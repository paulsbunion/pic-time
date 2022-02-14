package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.UserEventMileage;

public interface EventMileageRepository extends JpaRepository<UserEventMileage, Long>{

}
