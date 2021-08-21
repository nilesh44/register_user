package com.ace.registeruser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@Entity
@Table(name="phone_details")
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "phone_id")
	private Integer phoneId;
	
	@Column(name = "int_user_id")
	private Integer internalUserId;
	
	@Column(name = "chng_tkt_id")
	private Integer changeTicketId;
	
	@Column(name = "phone_num")
	private String phoneNumber;
	
	@Column(name = "ext_num")
	private String extentionNumber;
	
	@Column(name="is_active")
	private String isPhoneActive;

	@Column(name="user_prefer_code")
	private String userPreferenceCode;
	
	public static PhoneDetails createPhone(Integer changeTicketId, Integer internalUserId,
			String phoneNumber,String userPreferenceCode) {
		return PhoneDetails
				.builder()
				.changeTicketId(changeTicketId)
				.internalUserId(internalUserId)
				.phoneNumber(phoneNumber)
				.isPhoneActive("Y")
				.userPreferenceCode(userPreferenceCode)
				.build();
	}

}
