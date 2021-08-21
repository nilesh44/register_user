package com.ace.registeruser.service;

import java.util.List;

import com.ace.registeruser.entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.registeruser.entity.EmailDetails;
import com.ace.registeruser.entity.PhoneDetails;
import com.ace.registeruser.repository.EmailRepositiry;
import com.ace.registeruser.repository.PhoneRepository;
import com.ace.registeruser.vo.create.UserEmail;
import com.ace.registeruser.vo.create.UserPhone;
import com.ace.registeruser.vo.get.GetEmailResponse;
import com.ace.registeruser.vo.get.GetUserCompletInfoResponse;
import com.ace.registeruser.vo.get.GetUserPhoneResponse;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class FetchUserDetailsServiceImpl implements FetchUserDetailsService {

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private EmailRepositiry emailRepositiry;


	@Override
	public GetUserCompletInfoResponse getUserCompleteInfo(String userName,UserSession userSession) {


		Integer internalUserId = userSession.getInternalUserId();

		List<EmailDetails> emailDetails = emailRepositiry.findByInternalUserId(internalUserId);

		List<UserEmail> userEmails = UserEmail.from(emailDetails);

		List<PhoneDetails> phoneDetails = phoneRepository.findByInternalUserId(internalUserId);

		List<UserPhone> userPhones = UserPhone.from(phoneDetails);

		return GetUserCompletInfoResponse.builder().emails(userEmails).phones(userPhones).build();
	}
}
