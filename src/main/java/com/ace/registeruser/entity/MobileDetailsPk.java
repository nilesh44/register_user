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
public class MobileDetailsPk {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mob_id")
	private Integer mobId;
	
	@Column(name="crt_tms")
	private Timestamp createTms;
}
