package com.defrainPhoto.pictime.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.dto.UserDTO;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.service.EventService;
import com.defrainPhoto.pictime.service.UserService;

@RestController
public class PhotographerController {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EventService eventService;
	
	public List<UserDTO> getAllPhotographers() {
		List<UserDTO> users =  Arrays.asList(modelMapper.map(userService.findAll(), UserDTO[].class));
		return users;
	}

	public List<Event> getAllEventsForPhotographer(Long id) {
		return eventService.getAllEventsForPhotographer(id);
	}

	public UserDTO getPhotographer(Long id) {
		// TODO Auto-generated method stub
		return modelMapper.map(userService.findById(id), UserDTO.class);
	}
}
