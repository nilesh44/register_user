package com.ace.registeruser.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ace.registeruser.vo.create.UserPersonalInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name="personal_info")
public class PersonalInfo {
	
	@EmbeddedId
	private PersonalInfoPk personalInfoPk;
	
	@Column(name = "int_user_id")
	private Integer internalUserId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "dob")
	private Date dateOfBirth;
	
	@Column(name="is_block")
	private String isBlock;

	@Column(name="user_prefer_code")
	private String userPreferenceCode;
	
	@Column(name="exp_tms")
	private Timestamp expireTms;
	
	@Column(name="by_user")
	private String byUser;
	
	public static PersonalInfo createPersonalInfo(Integer changeTicketId, Integer internalUserId,
			UserPersonalInfo userPersonalInfo) {
		return PersonalInfo
				.builder()
				.internalUserId(internalUserId)
				.firstName(userPersonalInfo.getFirstName())
				.lastName(userPersonalInfo.getLastName())
				.build();
	}
}
