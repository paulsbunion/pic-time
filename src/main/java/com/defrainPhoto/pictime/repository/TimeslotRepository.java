package com.defrainPhoto.pictime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.model.Timeslot;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long>{

	public List<Timeslot> findAllByEvent(Long id);
	
	@Query
	List<TimeslotDTO> findAllBy();

	@Query
	public List<TimeslotDTO> findAllByPhotographersId(Long id);

	@Query
	public List<Timeslot> findAllByEventId(Long id);
}
