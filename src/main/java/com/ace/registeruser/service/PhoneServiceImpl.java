package com.ace.registeruser.service;

import com.ace.registeruser.entity.ChangeTicket;
import com.ace.registeruser.entity.MobileDetails;
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

        MobileDetails existingSecondaryPhone= phoneRepository.findByInternalUserIdAndUserPreferenceCode(internalUserId,"02");

        if(existingSecondaryPhone!=null){
            expirePhone( existingSecondaryPhone, userName);
        }

            ChangeTicket changeTicket = changeTicketService.getChangeTicketForCreate(userSession.getUserName());

            String userPreferenceCode = "02";

            MobileDetails phoneDetailsUpdated = MobileDetails
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
        MobileDetails newPrimaryPhoneDetails = phoneRepository.findByPhoneNumber(newPrimaryPhoneNumber);

        if(isPhoneNumberAssociatedWithAnotherUSer(newPrimaryPhoneDetails,internalUserId)){
            log.error("phone number is associated to another user");
            throw new RecordAlradyPresent("invalid phone number");
        }

      List<MobileDetails> existingPhoneDetails= phoneRepository.findByInternalUserId(internalUserId);

        if(!CollectionUtils.isEmpty(existingPhoneDetails)){

            Optional<MobileDetails> isPrimaryPhoneNumber=  existingPhoneDetails.stream().filter(existingPhoneDetail->existingPhoneDetail.getUserPreferenceCode().equals("01")).findFirst();
            Optional<MobileDetails> isSecondaryPhoneNumber=  existingPhoneDetails.stream().filter(existingPhoneDetail->existingPhoneDetail.getUserPreferenceCode().equals("02")).findFirst();

            if(isPrimaryPhoneNumber.isPresent()){

                MobileDetails existingPrimaryPhone=isPrimaryPhoneNumber.get();

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


private boolean isUserExistingSecondaryPhoneNumber( MobileDetails newPrimaryPhoneDetails ,Integer internalUserId){
  return  newPrimaryPhoneDetails.getInternalUserId().equals(internalUserId)
            &&newPrimaryPhoneDetails.getUserPreferenceCode().equals("02");
}



    private void makePrimaryAsSecondary(MobileDetails phoneDetail,String userName,Integer internalUserId){
        expirePhone( phoneDetail, userName);

        ChangeTicket changeTicketforUpdate = changeTicketService.getChangeTicketForUpdate(userName);
        createUpdateNewPhone(changeTicketforUpdate.getChangeTicketId(), internalUserId, phoneDetail.getPhoneNumber(),"02");
    }

    private void createUpdateNewPhone(Integer changeTicketId, Integer internalUserId, String phoneNumber,String userPreferenceCode) {
        MobileDetails phoneDetailsUpdated = MobileDetails
                .createPhone(changeTicketId,
                        internalUserId, phoneNumber,userPreferenceCode);
        phoneRepository.save(phoneDetailsUpdated);
    }

    private void expirePhone(MobileDetails phoneDetail,String userName) {

        ChangeTicket changeTicketforExpire = changeTicketService.getChangeTicketForExpire(userName);
        phoneDetail.setIsPhoneActive("N");
        phoneDetail.setChangeTicketId(changeTicketforExpire.getChangeTicketId());
        phoneRepository.saveAndFlush(phoneDetail);
    }


    private boolean isPhoneNumberAssociatedWithAnotherUSer(MobileDetails phoneDetails, Integer internalUserId){
        return phoneDetails!=null && !phoneDetails.getInternalUserId().equals(internalUserId);
    }

    private boolean isPhoneNumberAlradyPrimary(MobileDetails primaryPhoneDetail,String newPrimaryPhoneNumber){
        return primaryPhoneDetail.getPhoneNumber().equals(newPrimaryPhoneNumber);
    }

    @Override
    public void expirePhone(PhoneExpireRequest expirePhone, UserSession userSession) {

        // get oldPhone details
        List<MobileDetails> phoneDetails = phoneRepository
                .getUserPhone(Integer.valueOf(userSession.getInternalUserId()), expirePhone.getPhoneNumber());
        PhoneRepository.sendErrorForPhoneNotFound(phoneDetails);

        MobileDetails phoneDetail = phoneDetails.get(0);
        // expire oldphone details
        // create change ticket for expire record
        ChangeTicket changeTicketforExpire = changeTicketService.getChangeTicketForExpire(userSession.getUserName());
        phoneDetail.setIsPhoneActive("N");

        phoneDetail.setChangeTicketId(changeTicketforExpire.getChangeTicketId());

        phoneRepository.save(phoneDetail);
    }

    @Override
    public GetUserPhoneResponse getPhone(String userName, UserSession userSession) {

        List<MobileDetails> phoneDetails = phoneRepository
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
