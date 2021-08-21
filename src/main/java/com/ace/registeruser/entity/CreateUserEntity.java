package com.ace.registeruser.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
//@Table(name="change_ticket")
public class CreateUserEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="chng_tkt_id")
	private Integer changeTicketId;
	
	@Column(name="crt_tms")
	private Timestamp createTms;
	
	@Column(name="exp_tms")
	private Timestamp expireTms;
	
	@Column(name="upd_tms")
	private Timestamp updateTms;
	
	@Column(name="action_code")
	private String actionCode;
	
	@Column(name="by_user")
	private String byUser;
	
	
	    @OneToOne(fetch = FetchType.LAZY,
        cascade =  CascadeType.ALL)
	    @MapsId
	 private InternalUser internalUser;
	
	
	public static CreateUserEntity forCreate(String user) {
		return CreateUserEntity.builder()
				.createTms(Timestamp.from(Instant.now()))
				.actionCode("A")
				.byUser(user)
				.build();
	}
	
	public static CreateUserEntity forUpdate(String user) {
		return CreateUserEntity.builder()
				.updateTms(Timestamp.from(Instant.now()))
				.actionCode("U")
				.byUser(user)
				.build();
	}
	
	public static CreateUserEntity forExpire(String user) {
		return CreateUserEntity.builder()
				.expireTms(Timestamp.from(Instant.now()))
				.actionCode("D")
				.byUser(user)
				.build();
	}
}
