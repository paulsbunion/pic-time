package com.defrainPhoto.pictime.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
	
	@Query(name = "location.getLocation", nativeQuery = true)
	List<LocationDTO> findAllByOrderByDescriptionAsc();

	List<LocationDTO> findByDescriptionContainingIgnoreCase(String input);
	
	@Query(value = "SELECT loc FROM Location as loc WHERE (:inputString is null or loc.description like %:inputString%) OR " +
			"(loc.city like %:inputString%) OR " + 
			"(loc.state like %:inputString%) OR " +
			"(loc.zipcode like %:inputString%) OR " +
			"(loc.street like %:inputString%)")
	List<LocationDTO> findAllByInputString(String inputString);
//	Page<LocationDTO> findAllByInputString(String inputString, Pageable pageable);

	@Query("SELECT loc from Location loc where loc.street = :#{#test_loc.street}")
	List<Location> existsByLocation(@Param("test_loc") Location location);
}


