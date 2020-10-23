package com.defrainPhoto.pictime.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

	List<Event> findAllByPhotographersId(long id);

	List<Event> findAlByDateGreaterThanEqualAndDateLessThan(LocalDate fromDate, LocalDate toDate);

	List<Event> findAlByDate(LocalDate date);

}
