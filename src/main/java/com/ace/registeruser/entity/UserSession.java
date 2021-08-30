package com.ace.registeruser.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name="user_session")
@NoArgsConstructor
@AllArgsConstructor
@Component

public class UserSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_session_id")
	private Integer userSessionId;
	
	@Column(name="int_user_id")
	private Integer internalUserId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="crt_tms")
	private Timestamp createTimeStamp;
	
	@Column(name="last_login_tms")
	private Timestamp lastLoginTime;
	
	@Column(name="is_expired")
	private String isExpired;
}
