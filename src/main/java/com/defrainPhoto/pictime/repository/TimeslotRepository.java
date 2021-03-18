package com.defrainPhoto.pictime.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long>{

	public List<Timeslot> findAllByEvent(Long id);
	
	@Query
	List<TimeslotDTO> findAllBy();

	@Query
	public List<TimeslotDTO> findAllByPhotographersId(Long id);

	@Query
	public List<Timeslot> findAllByEventId(Long id);
	
	@Query
	public List<Timeslot> findAllByEventIdOrderByTimeStartTime(Long id);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM timeslot_user WHERE timeslot_user.timeslot_id = :timeslot_id AND timeslot_user.user_id = :user_id", nativeQuery = true)
	public void RemovePhotographerFromTimeslotsForEvent(@Param("user_id") Long photographerId, @Param("timeslot_id") Long timeslotId);
}
