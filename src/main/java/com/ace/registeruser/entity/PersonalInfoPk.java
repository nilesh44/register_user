package com.ace.registeruser.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoPk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_info_id")
	private Integer personalInfoId;
	
	@Column(name="crt_tms")
	private Timestamp createTms;
}
