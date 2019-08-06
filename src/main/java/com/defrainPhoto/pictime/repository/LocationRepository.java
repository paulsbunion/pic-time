package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
