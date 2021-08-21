package com.ace.registeruser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.registeruser.entity.PersonalInfo;
@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Integer>{

}
