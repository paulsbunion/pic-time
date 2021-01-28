package com.defrainPhoto.pictime.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.dto.UserDTO;
import com.defrainPhoto.pictime.service.UserService;

@RestController
public class PhotographerController {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserService userService;
	
	public List<UserDTO> getAllPhotographers() {
		List<UserDTO> users =  Arrays.asList(modelMapper.map(userService.findAll(), UserDTO[].class));
		return users;
	}
}
