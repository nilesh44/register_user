package com.ace.registeruser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ace.registeruser.vo.create.RegisterUserRequest;

import com.ace.registeruser.vo.create.UserPersonalInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name="personal_info")
public class PersonalInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_info_id")
	private Integer personalInfoId;
	
	@Column(name = "int_user_id")
	private Integer internalUserId;
	
	@Column(name = "chng_tkt_id")
	private Integer changeTicketId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name="is_active")
	private String isPersonalInfoActive;
	
	public static PersonalInfo createPersonalInfo(Integer changeTicketId, Integer internalUserId,
			UserPersonalInfo userPersonalInfo) {
		return PersonalInfo
				.builder()
				.changeTicketId(changeTicketId)
				.internalUserId(internalUserId)
				.firstName(userPersonalInfo.getFirstName())
				.lastName(userPersonalInfo.getLastName())
				.isPersonalInfoActive("Y")
				.build();
	}
}
