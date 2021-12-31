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
@Table(name = "email_details")
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

	
	@EmbeddedId
	private EmailDetailsPk emailDetailsPk;

	@Column(name = "int_user_id")
	private Integer internalUserId;

	@Column(name = "email_addr")
	private String emailAddress;
	
	@Column(name="is_block")
	private String isBlock;

	@Column(name="user_prefer_code")
	private String userPreferenceCode;
	
	@Column(name="exp_tms")
	private Timestamp expireTms;
	
	@Column(name="by_user")
	private String byUser;

	public static EmailDetails createEmail(Integer internalUserId,
			String emailAddress,String userPreferenceCode) {
		return EmailDetails
				.builder()
				.internalUserId(internalUserId)
				.emailAddress(emailAddress)
				.userPreferenceCode(userPreferenceCode)
				.build();
	}
}
