package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.EventUser;
import com.defrainPhoto.pictime.model.EventUserPK;

public interface EventUserRepository extends JpaRepository<EventUser, EventUserPK>{

}
