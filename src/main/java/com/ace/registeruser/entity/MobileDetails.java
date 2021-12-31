package com.ace.registeruser.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@Entity
@Table(name="mobile_details")
@NoArgsConstructor
@AllArgsConstructor
public class MobileDetails {
	
	@EmbeddedId
	private MobileDetailsPk mobileDetailsPk;
	
	@Column(name = "int_user_id")
	private Integer internalUserId;
	
	@Column(name = "mob_num")
	private String mobileNumber;
	
	@Column(name = "cntry_code")
	private String countryCode;
	
	@Column(name="is_block")
	private String isBlock;

	@Column(name="user_prefer_code")
	private String userPreferenceCode;
	
	@Column(name="exp_tms")
	private Timestamp expireTms;
	
	@Column(name="by_user")
	private String byUser;
	
	public static MobileDetails createPhone(Integer changeTicketId, Integer internalUserId,
			String phoneNumber,String userPreferenceCode) {
		return MobileDetails
				.builder()
				.internalUserId(internalUserId)
				.mobileNumber(phoneNumber)
				.userPreferenceCode(userPreferenceCode)
				.build();
	}

}
