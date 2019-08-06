package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.Timeline;

public interface TimelineRepository extends JpaRepository<Timeline, Long>{

}
