package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.EventType;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

}
