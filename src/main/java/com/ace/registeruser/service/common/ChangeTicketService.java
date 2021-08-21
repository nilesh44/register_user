package com.ace.registeruser.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.registeruser.entity.ChangeTicket;
import com.ace.registeruser.repository.ChangeTicketRepository;
@Service
public class ChangeTicketService {

	@Autowired
	private ChangeTicketRepository changeTicketRepository;
	
	public ChangeTicket getChangeTicketForCreate(String username){
		return changeTicketRepository.save(ChangeTicket.forCreate(username));
	}
	
	public ChangeTicket getChangeTicketForUpdate(String username){
		return changeTicketRepository.save(ChangeTicket.forUpdate(username));
	}
	
	public ChangeTicket getChangeTicketForExpire(String username){
		return changeTicketRepository.save(ChangeTicket.forExpire(username));
	}
}
