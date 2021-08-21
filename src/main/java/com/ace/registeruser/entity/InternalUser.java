package com.ace.registeruser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="int_user_id") 
	private Integer internalUsrId;
	
	@Column(name="chng_tkt_id") 
	private Integer changeTicketId;
	
	@Column(name="is_active")
	private String isInternalUserActive;
	
	/*
	 * @ToString.Exclude
	 * 
	 * @OneToOne(fetch = FetchType.EAGER, optional = false,cascade=CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "chng_tkt_id", nullable =
	 * false,insertable=false,updatable=false) private CreateUserEntity
	 * createUserEntity;
	 */
	
	
	public static InternalUser createInternalUser(Integer changeTicketId) {
		return InternalUser.builder().changeTicketId(changeTicketId).isInternalUserActive("Y").build();
	}



}
