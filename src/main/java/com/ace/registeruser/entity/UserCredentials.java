package com.ace.registeruser.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ace.registeruser.vo.create.RegisterUserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "user_cred")
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

	@EmbeddedId
	private UserCredentialsPk userCredentialsPk;

	@Column(name = "int_user_id")
	private Integer internalUserId;

	@Column(name = "user_name")
	private String username;

	@Column(name = "user_password")
	private String password;

	@Column(name = "is_block")
	private String isBlock;

	@Column(name = "exp_tms")
	private Timestamp expireTms;

	@Column(name = "by_user")
	private String byUser;

	public static UserCredentials createNewUser(Integer changeTicketId, Integer internalUserId,
			RegisterUserRequest registerUserRequest) {
		return UserCredentials.builder().internalUserId(internalUserId).username(registerUserRequest.getUserName())
				.password(registerUserRequest.getPassword()).build();
	}

}
