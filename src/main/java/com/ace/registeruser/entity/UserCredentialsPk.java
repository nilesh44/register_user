package com.ace.registeruser.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsPk {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_cred_id")
	private Integer userCredentialId;
	
	@Column(name="crt_tms")
	private Timestamp createTms;
}
