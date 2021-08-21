package com.ace.registeruser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ace.registeruser.entity.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {

	@Query("SELECT us from UserSession us where userName = :userName and isExpired = 'N'")
	public Optional<UserSession> findByUsername(@Param("userName") String userName);
}
