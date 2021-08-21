package com.ace.registeruser.service;

import com.ace.registeruser.vo.create.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ace.registeruser.entity.ChangeTicket;
import com.ace.registeruser.entity.EmailDetails;
import com.ace.registeruser.entity.InternalUser;
import com.ace.registeruser.entity.PersonalInfo;
import com.ace.registeruser.entity.PhoneDetails;
import com.ace.registeruser.entity.UserCredentials;
import com.ace.registeruser.repository.EmailRepositiry;
import com.ace.registeruser.repository.InternalUserRepository;
import com.ace.registeruser.repository.PersonalInfoRepository;
import com.ace.registeruser.repository.PhoneRepository;
import com.ace.registeruser.repository.UserCredentialRepository;
import com.ace.registeruser.service.common.ChangeTicketService;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class RegisterUserServiceImpl implements RegisterUserService {

	@Autowired
	private ChangeTicketService changeTicketService;

	@Autowired
	private InternalUserRepository internalUserRepository;

	@Autowired
	private UserCredentialRepository userCredentialRepository;

	@Autowired
	private EmailRepositiry emailRepositiry;

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private PersonalInfoRepository personalInfoRepository;

	@Override
	public CreateUserResponse createUser(RegisterUserRequest registerUserRequest) {

		// create change ticket for transaction
		ChangeTicket changeTicket = changeTicketService.getChangeTicketForCreate(registerUserRequest.getUserName());

		Integer changeTicketId = changeTicket.getChangeTicketId();
		InternalUser internalUser = InternalUser.createInternalUser(changeTicketId);

		// create internal userId for user
		internalUser = internalUserRepository.save(internalUser);
		Integer internalUserId = internalUser.getChangeTicketId();
		String userName=registerUserRequest.getUserName();

		CompletableFuture<Void> createUserFuture=createUser( registerUserRequest, internalUserId,userName);

		CompletableFuture<Void> createEmailFuture=createEmail( registerUserRequest.getUserEmails(), internalUserId,userName);

		CompletableFuture<Void> createPhoneFuture=createPhone( registerUserRequest.getUserPhones(), internalUserId,userName);

		CompletableFuture<Void> createPersonalInfoFuture=createPersonalInfo( registerUserRequest.getUserPersonalInfo(), internalUserId,userName);

		CompletableFuture.allOf(createUserFuture,createEmailFuture,createPhoneFuture,createPersonalInfoFuture);

		return CreateUserResponse.builder().isUserCreated(true).build();
	}

	private CompletableFuture<Void> createUser(RegisterUserRequest registerUserRequest,Integer internalUserId,String userName){

		CompletableFuture<Void> registerUserFuture=CompletableFuture.runAsync(() -> {

			ChangeTicket changeTicketCreateUser = changeTicketService
					.getChangeTicketForCreate(userName);
			UserCredentials userCredentials = UserCredentials.createNewUser(changeTicketCreateUser.getChangeTicketId(),
					internalUserId, registerUserRequest);
			userCredentialRepository.saveAndFlush(userCredentials);
		});

		return registerUserFuture;
	}

	private CompletableFuture<Void> createPhone(UserPhone userPhone, Integer internalUserId, String userName){

		CompletableFuture<Void> createPhoneFuture=CompletableFuture.runAsync(() -> {
			ChangeTicket changeTicketforCreatePhone = changeTicketService
					.getChangeTicketForCreate(userName);
			PhoneDetails phoneDetails = PhoneDetails.createPhone(changeTicketforCreatePhone.getChangeTicketId(),
					internalUserId, userPhone.getPhoneNumber(),"01");
			phoneRepository.saveAndFlush(phoneDetails);
		});

		return createPhoneFuture;
	}
	private CompletableFuture<Void> createEmail(UserEmail userEmail, Integer internalUserId, String userName){

		CompletableFuture<Void> createEmailFuture=CompletableFuture.runAsync(() -> {
			ChangeTicket changeTicketforCreateEmail = changeTicketService
					.getChangeTicketForCreate(userName);
			EmailDetails emailDetails = EmailDetails.createEmail(changeTicketforCreateEmail.getChangeTicketId(),
					internalUserId, userEmail.getEmailAddress(),"01");
			emailRepositiry.saveAndFlush(emailDetails);
		});

		return createEmailFuture;
	}
	private CompletableFuture<Void> createPersonalInfo(UserPersonalInfo userPersonalInfo, Integer internalUserId, String userName){

		CompletableFuture<Void> createPersonalInfoFuture=CompletableFuture.runAsync(() -> {

			ChangeTicket changeTicketforCreatePersonalInfo = changeTicketService
					.getChangeTicketForCreate(userName);
			PersonalInfo personalInfo = PersonalInfo.createPersonalInfo(
					changeTicketforCreatePersonalInfo.getChangeTicketId(), internalUserId, userPersonalInfo);
			personalInfoRepository.saveAndFlush(personalInfo);

		});

		return createPersonalInfoFuture;
	}

}
