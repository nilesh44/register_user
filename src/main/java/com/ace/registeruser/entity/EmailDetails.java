package com.ace.registeruser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ace.registeruser.vo.create.UserEmail;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_id")
	private Integer emailId;

	@Column(name = "int_user_id")
	private Integer internalUserId;

	@Column(name = "chng_tkt_id")
	private Integer changeTicketId;

	@Column(name = "email_addr")
	private String emailAddress;
	
	@Column(name="is_active")
	private String isEmailActive;

	@Column(name="user_prefer_code")
	private String userPreferenceCode;

	public static EmailDetails createEmail(Integer changeTicketId, Integer internalUserId,
			String emailAddress,String userPreferenceCode) {
		return EmailDetails
				.builder()
				.changeTicketId(changeTicketId)
				.internalUserId(internalUserId)
				.emailAddress(emailAddress)
				.isEmailActive("Y")
				.userPreferenceCode(userPreferenceCode)
				.build();
	}
}
