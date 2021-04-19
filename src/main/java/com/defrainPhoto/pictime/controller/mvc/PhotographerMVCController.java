package com.defrainPhoto.pictime.controller.mvc;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.controller.PhotographerController;
import com.defrainPhoto.pictime.dto.MonthDTO;
import com.defrainPhoto.pictime.dto.UserDTO;
import com.defrainPhoto.pictime.model.Event;

@Controller
@RequestMapping("/mvc/photographers/")
public class PhotographerMVCController {
	
	private static final String LIST_PHOTOGRAPHERS_URL = "photographer/list-photographers";
	private static final String LIST_PHOTOGRAPHER_EVENTS_URL = "photographer/list-photographer-events";
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PhotographerController photographerController;
	
	@GetMapping("list")
	public String getAllPhotographers(Model model) {
		
		log.info("MVC user calling get all photographers");
		List<UserDTO> photographers = photographerController.getAllPhotographers();
		model.addAttribute("photographers", photographers);
		
		return LIST_PHOTOGRAPHERS_URL;
	}
	
	@GetMapping("{id}/events")
	public String getAllPhotographerEvents(@PathVariable("id")Long id, Model model) {
		
		LocalDate now = LocalDate.now();
		MonthDTO month = new MonthDTO(now);
		model.addAttribute("month", month);
		
		UserDTO photog = photographerController.getPhotographer(id);
		model.addAttribute("photog", photog);
		List<Event> events = photographerController.getAllEventsForPhotographer(id);
		model.addAttribute("events", events);
		
		return LIST_PHOTOGRAPHER_EVENTS_URL;
	}
}
