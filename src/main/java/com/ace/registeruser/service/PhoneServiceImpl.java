package com.ace.registeruser.service;

import com.ace.registeruser.entity.ChangeTicket;
import com.ace.registeruser.entity.PhoneDetails;
import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.exception.RecordAlradyPresent;
import com.ace.registeruser.repository.PhoneRepository;
import com.ace.registeruser.service.common.ChangeTicketService;
import com.ace.registeruser.vo.create.UserPhone;
import com.ace.registeruser.vo.expire.PhoneExpireRequest;
import com.ace.registeruser.vo.get.GetUserPhoneResponse;
import com.ace.registeruser.vo.phone.AddSecondaryPhoneNumber;
import com.ace.registeruser.vo.update.ChangePrimaryPhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService{

    @Autowired
    private ChangeTicketService changeTicketService;

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public void addAddSecondaryPhoneNumber(AddSecondaryPhoneNumber addSecondaryPhoneNumber,
                                           UserSession userSession) {

        Integer internalUserId = userSession.getInternalUserId();
        String userName=userSession.getUserName();

        PhoneDetails existingSecondaryPhone= phoneRepository.findByInternalUserIdAndUserPreferenceCode(internalUserId,"02");

        if(existingSecondaryPhone!=null){
            expirePhone( existingSecondaryPhone, userName);
        }

            ChangeTicket changeTicket = changeTicketService.getChangeTicketForCreate(userSession.getUserName());

            String userPreferenceCode = "02";

            PhoneDetails phoneDetailsUpdated = PhoneDetails
                    .createPhone(
                            changeTicket.getChangeTicketId(),
                            internalUserId,
                            addSecondaryPhoneNumber.getSecondaryPhoneNumber(),
                            userPreferenceCode);

            phoneRepository.save(phoneDetailsUpdated);

    }

    @Override
    public void addNewPrimaryPhoneNumber(ChangePrimaryPhoneNumber changePrimaryPhoneNumber, UserSession userSession) {

        Integer internalUserId = userSession.getInternalUserId();
        String newPrimaryPhoneNumber=changePrimaryPhoneNumber.getNewPrimaryPhoneNumber();
        String userName=userSession.getUserName();
        PhoneDetails newPrimaryPhoneDetails = phoneRepository.findByPhoneNumber(newPrimaryPhoneNumber);

        if(isPhoneNumberAssociatedWithAnotherUSer(newPrimaryPhoneDetails,internalUserId)){
            log.error("phone number is associated to another user");
            throw new RecordAlradyPresent("invalid phone number");
        }

      List<PhoneDetails> existingPhoneDetails= phoneRepository.findByInternalUserId(internalUserId);

        if(!CollectionUtils.isEmpty(existingPhoneDetails)){

            Optional<PhoneDetails> isPrimaryPhoneNumber=  existingPhoneDetails.stream().filter(existingPhoneDetail->existingPhoneDetail.getUserPreferenceCode().equals("01")).findFirst();
            Optional<PhoneDetails> isSecondaryPhoneNumber=  existingPhoneDetails.stream().filter(existingPhoneDetail->existingPhoneDetail.getUserPreferenceCode().equals("02")).findFirst();

            if(isPrimaryPhoneNumber.isPresent()){

                PhoneDetails existingPrimaryPhone=isPrimaryPhoneNumber.get();

                if(isPhoneNumberAlradyPrimary(existingPrimaryPhone,newPrimaryPhoneNumber)){
                    throw new RecordAlradyPresent("this phone number is already existing primary phone number");
                }
                if(newPrimaryPhoneDetails!=null
                        && isUserExistingSecondaryPhoneNumber(newPrimaryPhoneDetails,internalUserId)){
                        expirePhone( newPrimaryPhoneDetails, userName);
                        makePrimaryAsSecondary(existingPrimaryPhone, userName, internalUserId);

                }
                else if(!isSecondaryPhoneNumber.isPresent()){

                        makePrimaryAsSecondary(existingPrimaryPhone, userName, internalUserId);
                    }else{
                        expirePhone( existingPrimaryPhone, userName);
                    }
            }

        }
            ChangeTicket changeTicketForCreate = changeTicketService.getChangeTicketForCreate(userName);
            createUpdateNewPhone(changeTicketForCreate.getChangeTicketId(),
                    internalUserId,
                    newPrimaryPhoneNumber,
                    "01");

        }


private boolean isUserExistingSecondaryPhoneNumber( PhoneDetails newPrimaryPhoneDetails ,Integer internalUserId){
  return  newPrimaryPhoneDetails.getInternalUserId().equals(internalUserId)
            &&newPrimaryPhoneDetails.getUserPreferenceCode().equals("02");
}



    private void makePrimaryAsSecondary(PhoneDetails phoneDetail,String userName,Integer internalUserId){
        expirePhone( phoneDetail, userName);

        ChangeTicket changeTicketforUpdate = changeTicketService.getChangeTicketForUpdate(userName);
        createUpdateNewPhone(changeTicketforUpdate.getChangeTicketId(), internalUserId, phoneDetail.getPhoneNumber(),"02");
    }

    private void createUpdateNewPhone(Integer changeTicketId, Integer internalUserId, String phoneNumber,String userPreferenceCode) {
        PhoneDetails phoneDetailsUpdated = PhoneDetails
                .createPhone(changeTicketId,
                        internalUserId, phoneNumber,userPreferenceCode);
        phoneRepository.save(phoneDetailsUpdated);
    }

    private void expirePhone(PhoneDetails phoneDetail,String userName) {

        ChangeTicket changeTicketforExpire = changeTicketService.getChangeTicketForExpire(userName);
        phoneDetail.setIsPhoneActive("N");
        phoneDetail.setChangeTicketId(changeTicketforExpire.getChangeTicketId());
        phoneRepository.saveAndFlush(phoneDetail);
    }


    private boolean isPhoneNumberAssociatedWithAnotherUSer(PhoneDetails phoneDetails, Integer internalUserId){
        return phoneDetails!=null && !phoneDetails.getInternalUserId().equals(internalUserId);
    }

    private boolean isPhoneNumberAlradyPrimary(PhoneDetails primaryPhoneDetail,String newPrimaryPhoneNumber){
        return primaryPhoneDetail.getPhoneNumber().equals(newPrimaryPhoneNumber);
    }

    @Override
    public void expirePhone(PhoneExpireRequest expirePhone, UserSession userSession) {

        // get oldPhone details
        List<PhoneDetails> phoneDetails = phoneRepository
                .getUserPhone(Integer.valueOf(userSession.getInternalUserId()), expirePhone.getPhoneNumber());
        PhoneRepository.sendErrorForPhoneNotFound(phoneDetails);

        PhoneDetails phoneDetail = phoneDetails.get(0);
        // expire oldphone details
        // create change ticket for expire record
        ChangeTicket changeTicketforExpire = changeTicketService.getChangeTicketForExpire(userSession.getUserName());
        phoneDetail.setIsPhoneActive("N");

        phoneDetail.setChangeTicketId(changeTicketforExpire.getChangeTicketId());

        phoneRepository.save(phoneDetail);
    }

    @Override
    public GetUserPhoneResponse getPhone(String userName, UserSession userSession) {

        List<PhoneDetails> phoneDetails = phoneRepository
                .findByInternalUserId(userSession.getInternalUserId());
        GetUserPhoneResponse getUserPhoneResponse = new GetUserPhoneResponse();

     if(!CollectionUtils.isEmpty(phoneDetails)){
    List<UserPhone> userPhones = UserPhone.from(phoneDetails);
    getUserPhoneResponse.setUserPhones(userPhones);
      }else{
         getUserPhoneResponse.setUserPhones(Collections.emptyList());
     }


        return getUserPhoneResponse;
    }
}
