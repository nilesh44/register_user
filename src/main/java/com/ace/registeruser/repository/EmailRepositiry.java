package com.ace.registeruser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.EmailDetails;
import com.ace.registeruser.exception.RecordNotFoundException;

@Repository
public interface EmailRepositiry extends JpaRepository<EmailDetails, Integer> {

	@Query("select ed from EmailDetails ed where ed.internalUserId = :internalUserId and ed.emailAddress = :emailAddress and  ed.isEmailActive ='Y'")
	public List<EmailDetails> getUserEmail(@Param("internalUserId") Integer internalUserId,
			@Param("emailAddress") String emailAddress);

	@Query("select ed from EmailDetails ed where ed.emailAddress = :emailAddress and  ed.isEmailActive ='Y'")
	public List<EmailDetails> findByEmailAddress(@Param("emailAddress") String emailAddress);

	@Query("select ed from EmailDetails ed where ed.internalUserId = :internalUserId and  ed.isEmailActive ='Y'")
	public List<EmailDetails> findByInternalUserId(@Param("internalUserId") Integer internalUserId);

	public static void sendErrorForEmailNotFound(List<EmailDetails> emailDetails) {

		if (emailDetails.isEmpty()) {
			throw new RecordNotFoundException("email data not found for this user");
		}
	}

}
