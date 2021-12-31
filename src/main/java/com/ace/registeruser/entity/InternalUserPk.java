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


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InternalUserPk {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="int_user_id") 
	private Integer internalUsrId;
	
	@Column(name="crt_tms")
	private Timestamp createTms;
}
