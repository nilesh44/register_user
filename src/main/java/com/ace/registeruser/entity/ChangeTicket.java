package com.ace.registeruser.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name="change_ticket")
public class ChangeTicket {
	
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
	
	
	public static ChangeTicket forCreate(String user) {
		return ChangeTicket.builder()
				.createTms(Timestamp.from(Instant.now()))
				.actionCode("A")
				.byUser(user)
				.build();
	}
	
	public static ChangeTicket forUpdate(String user) {
		return ChangeTicket.builder()
				.updateTms(Timestamp.from(Instant.now()))
				.actionCode("U")
				.byUser(user)
				.build();
	}
	
	public static ChangeTicket forExpire(String user) {
		return ChangeTicket.builder()
				.expireTms(Timestamp.from(Instant.now()))
				.actionCode("D")
				.byUser(user)
				.build();
	}
	

}
