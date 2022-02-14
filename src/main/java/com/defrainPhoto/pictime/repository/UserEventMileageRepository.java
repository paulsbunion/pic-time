package com.defrainPhoto.pictime.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.dto.UserEventMileageDTO;
import com.defrainPhoto.pictime.model.UserEventMileage;

public interface UserEventMileageRepository extends JpaRepository<UserEventMileage, Long>{

	//@Query(value = "SELECT e.name, uevm.* FROM UserEventMileage uevm inner join Event e WHERE e.id LIKE %?1%")
	//@Query(value = "SELECT e.id, e.event_name as eventName, e.date FROM Event e WHERE e.id= :userId", nativeQuery = true)
	//@Query("Select a from UserEventMileage a, Event b join Event b on a.eventUserId.eventId=b.id")
//	@Query("Select new com.defrainPhoto.pictime.dto.UserEventMileageDTO(a.eventUserId.eventId, a.eventUserId.userId, b.eventName, a.date, a.totalMiles) from UserEventMileage a, Event b join Event b on a.eventUserId.eventId=b.id")
	@Query("Select new com.defrainPhoto.pictime.dto.UserEventMileageDTO(a.eventUserId, b.eventName, a.date, a.totalMiles) from UserEventMileage a JOIN Event b on a.eventUserId.eventId=b.id")
	List<UserEventMileageDTO> findAllByEventUserIdUserId(Long userId);
	
	//@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name LIKE %?1%")
}


//private Long id;
//private String eventName;
//@DateTimeFormat(pattern = "yyyy-MM-dd")
//private LocalDate date;
//private  Long totalMiles;


//
//private long EventId;
//private long UserId;
//
//private String eventName;
//@DateTimeFormat(pattern = "yyyy-MM-dd")
//private LocalDate date;
//private  Long totalMiles;