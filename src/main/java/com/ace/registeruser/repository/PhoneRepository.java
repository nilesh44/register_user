package com.ace.registeruser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.PhoneDetails;
import com.ace.registeruser.exception.RecordNotFoundException;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneDetails, Integer> {
	
	@Query("select pd from PhoneDetails pd where pd.internalUserId = :internalUserId and pd.phoneNumber = :phoneNumber and  pd.isPhoneActive ='Y'")
	public List<PhoneDetails> getUserPhone(@Param("internalUserId") Integer internalUserId,
			@Param("phoneNumber") String phoneNumber);
	
	@Query("select pd from PhoneDetails pd where pd.phoneNumber = :phoneNumber and  pd.isPhoneActive ='Y'")
	public PhoneDetails findByPhoneNumber(
			@Param("phoneNumber") String phoneNumber);
	
	@Query("select pd from PhoneDetails pd where pd.internalUserId = :internalUserId and  pd.isPhoneActive ='Y'")
	public List<PhoneDetails> findByInternalUserId(@Param("internalUserId") Integer internalUserId);

	@Query("select pd from PhoneDetails pd  where pd.internalUserId = :internalUserId and  pd.isPhoneActive ='Y' and userPreferenceCode = :userPreferenceCode ")
	public PhoneDetails findByInternalUserIdAndUserPreferenceCode(
			@Param("internalUserId") Integer internalUserId,@Param("userPreferenceCode") String userPreferenceCode);


	static void sendErrorForPhoneNotFound(List<PhoneDetails> phoneDetails) {

		if (phoneDetails.isEmpty()) {
			throw new RecordNotFoundException("phone data not found for this user");
		}
	}

}
