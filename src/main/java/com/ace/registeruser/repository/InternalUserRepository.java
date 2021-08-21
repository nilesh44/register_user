package com.ace.registeruser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.InternalUser;
@Repository
public interface InternalUserRepository extends JpaRepository<InternalUser, Integer>{

}
