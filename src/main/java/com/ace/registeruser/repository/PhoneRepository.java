package com.ace.registeruser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.MobileDetails;
import com.ace.registeruser.exception.RecordNotFoundException;

@Repository
public interface PhoneRepository extends JpaRepository<MobileDetails, Integer> {
	
	@Query("select pd from PhoneDetails pd where pd.internalUserId = :internalUserId and pd.phoneNumber = :phoneNumber and  pd.isPhoneActive ='Y'")
	public List<MobileDetails> getUserPhone(@Param("internalUserId") Integer internalUserId,
			@Param("phoneNumber") String phoneNumber);
	
	@Query("select pd from PhoneDetails pd where pd.phoneNumber = :phoneNumber and  pd.isPhoneActive ='Y'")
	public MobileDetails findByPhoneNumber(
			@Param("phoneNumber") String phoneNumber);
	
	@Query("select pd from PhoneDetails pd where pd.internalUserId = :internalUserId and  pd.isPhoneActive ='Y'")
	public List<MobileDetails> findByInternalUserId(@Param("internalUserId") Integer internalUserId);

	@Query("select pd from PhoneDetails pd  where pd.internalUserId = :internalUserId and  pd.isPhoneActive ='Y' and userPreferenceCode = :userPreferenceCode ")
	public MobileDetails findByInternalUserIdAndUserPreferenceCode(
			@Param("internalUserId") Integer internalUserId,@Param("userPreferenceCode") String userPreferenceCode);


	static void sendErrorForPhoneNotFound(List<MobileDetails> phoneDetails) {

		if (phoneDetails.isEmpty()) {
			throw new RecordNotFoundException("phone data not found for this user");
		}
	}

}
