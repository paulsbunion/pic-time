package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
