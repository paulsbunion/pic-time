package com.defrainPhoto.pictime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.Timeslot;

public interface TimeSlotRepository extends JpaRepository<Timeslot, Long>{

	public List<Timeslot> findAllByTimeline(Long id);
	
}
