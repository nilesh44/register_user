package com.ace.registeruser.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="int_user")
@Data
public class InternalUser {
	
	@EmbeddedId
	private InternalUserPk internalUserPk;

	@Column(name="is_block")
	private String isBlock;
	
	@Column(name="exp_tms")
	private Timestamp expireTms;
	
	@Column(name="by_user")
	private String byUser;
	
	
	public static InternalUser createInternalUser(Integer changeTicketId) {
		return InternalUser.builder().build();
	}



}
