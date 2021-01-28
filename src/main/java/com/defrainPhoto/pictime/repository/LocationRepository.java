package com.defrainPhoto.pictime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
	
	@Query(name = "location.getLocation", nativeQuery = true)
	List<LocationDTO> findAllByOrderByDescriptionAsc();
}
