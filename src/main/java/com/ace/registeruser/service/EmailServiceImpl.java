package com.ace.registeruser.service;

import com.ace.registeruser.entity.ChangeTicket;
import com.ace.registeruser.entity.EmailDetails;
import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.exception.RecordAlradyPresent;
import com.ace.registeruser.repository.EmailRepositiry;
import com.ace.registeruser.service.common.ChangeTicketService;
import com.ace.registeruser.vo.create.UserEmail;
import com.ace.registeruser.vo.expire.EmailExpireRequest;
import com.ace.registeruser.vo.get.GetEmailResponse;
import com.ace.registeruser.vo.update.EmailUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class EmailServiceImpl implements EmailService{

    @Autowired
    private ChangeTicketService changeTicketService;

    @Autowired
    private EmailRepositiry emailRepositiry;

    @Override
    public GetEmailResponse getEmail(String userName, UserSession userSession) {

        List<EmailDetails> emailDetails = emailRepositiry
                .findByInternalUserId(userSession.getInternalUserId());

        List<UserEmail> userEmails = UserEmail.from(emailDetails);

        GetEmailResponse getEmailResponse = new GetEmailResponse();
        getEmailResponse.setUserEmails(userEmails);

        return getEmailResponse;
    }

    @Override
    public void expireEmail(EmailExpireRequest expireEmail, UserSession userSession) {

        List<EmailDetails> emailDetails = emailRepositiry.getUserEmail(userSession.getInternalUserId(),
                expireEmail.getEmailAddress());
        EmailRepositiry.sendErrorForEmailNotFound(emailDetails);

        EmailDetails emailDetail = emailDetails.get(0);

        ChangeTicket changeTicketforExpire = changeTicketService.getChangeTicketForExpire(userSession.getUserName());
        emailDetail.setIsEmailActive("N");
        emailDetail.setChangeTicketId(changeTicketforExpire.getChangeTicketId());
        emailRepositiry.save(emailDetail);

    }

    @Override
    public void updateEmail(EmailUpdateRequest emailUpdateRequest, UserSession userSession) {

        validateNewEmail(emailUpdateRequest.getNewEmailAddress());

        Integer internalUserId = userSession.getInternalUserId();

        ChangeTicket changeTicket;
        String userPreferenceCode;
        if (StringUtils.isNoneBlank(emailUpdateRequest.getOldEmailAddress())) {

            expireOldEmail(internalUserId, emailUpdateRequest.getOldEmailAddress(), userSession);
            changeTicket = changeTicketService.getChangeTicketForUpdate(userSession.getUserName());
            userPreferenceCode="01";

        } else {

            changeTicket = changeTicketService.getChangeTicketForCreate(userSession.getUserName());
            userPreferenceCode="02";
        }

        createNewEmail(changeTicket.getChangeTicketId(), internalUserId, emailUpdateRequest.getNewEmailAddress(),userPreferenceCode);
    }

    private void createNewEmail(Integer changeTicketId, Integer internalUserId, String emailAddress,String userPreferenceCode) {

        EmailDetails emailDetailsUpdated = EmailDetails.createEmail(changeTicketId, internalUserId, emailAddress,userPreferenceCode);
        emailRepositiry.save(emailDetailsUpdated);
    }

    private void validateNewEmail(String emailAddress) {

        List<EmailDetails> emailDetails = emailRepositiry.findByEmailAddress(emailAddress);
        if (!CollectionUtils.isEmpty(emailDetails)) {
            new RecordAlradyPresent("emailAddress Alrady Present");
        }
    }

    private void expireOldEmail(Integer internalUserId, String oldEmailAddress, UserSession userSession) {

        // get oldEmailAddress details
        List<EmailDetails> emailDetails = emailRepositiry.getUserEmail(internalUserId, oldEmailAddress);

        EmailRepositiry.sendErrorForEmailNotFound(emailDetails);

        // expire oldEmailAddress details
        EmailDetails emailDetail = emailDetails.get(0);
        // create change ticket for expire record

        ChangeTicket changeTicketforExpire = changeTicketService.getChangeTicketForExpire(
                userSession.getUserName());
        emailDetail.setIsEmailActive("N");

        emailDetail.setChangeTicketId(changeTicketforExpire.getChangeTicketId());
        emailRepositiry.save(emailDetail);
    }
}
