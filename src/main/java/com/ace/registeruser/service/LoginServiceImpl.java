package com.ace.registeruser.service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.registeruser.entity.UserCredentials;
import com.ace.registeruser.entity.UserSession;
import com.ace.registeruser.exception.InvalidDataException;
import com.ace.registeruser.repository.UserCredentialRepository;
import com.ace.registeruser.repository.UserSessionRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class LoginServiceImpl implements LoginService{

	@Autowired
	private UserSessionRepository sessionRepository;
	
	@Autowired
	private UserCredentialRepository userCredentialRepository;
	

	@Override
	public void logIn(String userName, String password) {
		
		Optional<UserCredentials> userCredential=userCredentialRepository.findByUsernameAndPassword(userName, password);
		
		if(!userCredential.isPresent()) {
			throw new InvalidDataException("invalid cradentials");
		}
		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		Optional<UserSession> userSessionexistInDB=	sessionRepository.findByUsername(userName);
		
		if(userSessionexistInDB.isPresent() && new Timestamp(System.currentTimeMillis()).after(new Timestamp(userSessionexistInDB.get().getLastLoginTime().getTime() + TimeUnit.MINUTES.toMillis(1))) ) {
		
			userSessionexistInDB.get().setLastLoginTime(timestamp);
			userSessionexistInDB.get().setIsExpired("Y");
			sessionRepository.save(userSessionexistInDB.get());
			
			UserSession userSession=UserSession
					.builder()
					.createTimeStamp(timestamp)
					.lastLoginTime(timestamp)
				.internalUserId(userCredential.get().getInternalUserId())
				.userName(userName)
				.isExpired("N")
				.build();
			sessionRepository.save(userSession);
			
		}else if(userSessionexistInDB.isPresent() && new Timestamp(System.currentTimeMillis()).before(new Timestamp(userSessionexistInDB.get().getLastLoginTime().getTime() + TimeUnit.MINUTES.toMillis(1))) ){
			userSessionexistInDB.get().setLastLoginTime(timestamp);
			sessionRepository.save(userSessionexistInDB.get());
		}
		
		else {
	
	UserSession userSession=UserSession
			.builder()
			.createTimeStamp(timestamp)
			.lastLoginTime(timestamp)
		.internalUserId(userCredential.get().getInternalUserId())
		.userName(userName)
		.isExpired("N")
		.build();
	sessionRepository.save(userSession);
		}
	}
	
    @Override
	public void logOut(String userName) {
    	
	    Optional<UserCredentials> userCredential=userCredentialRepository.findByUsername(userName);
		
		if(!userCredential.isPresent()) {
			throw new InvalidDataException("invalid cradentials");
		}
		Optional<UserSession> userSession=	sessionRepository.findByUsername(userName);
		if(userSession.isPresent()) {
			 userSession.get().setIsExpired("N");
			 sessionRepository.save(userSession.get());
		}else {
			throw new InvalidDataException("please login again your session has been expired");
		}
	
	
	}
}
