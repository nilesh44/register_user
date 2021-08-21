package com.ace.registeruser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ace.registeruser.vo.create.RegisterUserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name="user_cred")
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_cred_id")
	private Integer userCredentialId;
	
    @Column(name="int_user_id")
	private Integer internalUserId;
    
    @Column(name="chng_tkt_id")
	private Integer changeTicketId;
	
    @Column(name="user_name")
	private String username;
	
    @Column(name="user_password")
	private String password;
	
    @Column(name="is_active")
	private String isUserCredentialActive;
	
    public static UserCredentials createNewUser(Integer changeTicketId, Integer internalUserId, RegisterUserRequest registerUserRequest) {
    	return UserCredentials.builder()
    	.changeTicketId(changeTicketId)
    	.internalUserId(internalUserId)
    	.username(registerUserRequest.getUserName())
    	.password(registerUserRequest.getPassword())
    	.isUserCredentialActive("Y")
    	.build();
    }
    
}
