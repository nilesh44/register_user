package com.ace.registeruser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.UserCredentials;
import com.ace.registeruser.exception.RecordNotFoundException;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials, Integer> {
	
	
	@Query("SELECT uc from UserCredentials uc where uc.username = :username and uc.isUserCredentialActive = 'Y'")
	public Optional<UserCredentials> findByUsername(@Param("username") String userName);
	
	@Query("SELECT uc from UserCredentials uc where uc.username = :username and uc.password = :password and uc.isUserCredentialActive = 'Y'")
	public Optional<UserCredentials> findByUsernameAndPassword(@Param("username") String userName,@Param("password") String password);

	public static void sendErrorForUserNotFound(Optional<UserCredentials> userCredentials) {

		if (!userCredentials.isPresent()) {
			throw new RecordNotFoundException("user name not found");
		}
	}

}
