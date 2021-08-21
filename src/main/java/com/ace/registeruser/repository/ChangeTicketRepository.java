package com.ace.registeruser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.ChangeTicket;
@Repository
public interface ChangeTicketRepository extends JpaRepository<ChangeTicket, Integer>{

}
