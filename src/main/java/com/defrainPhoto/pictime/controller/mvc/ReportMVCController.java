package com.defrainPhoto.pictime.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.controller.MileageController;
import com.defrainPhoto.pictime.dto.UserEventMileageDTO;
import com.defrainPhoto.pictime.model.UserEventMileage;

@Controller
@RequestMapping("/mvc/reports/")
public class ReportMVCController {

	private final String MVC_REPORTS_URL_BASE = "/mvc/reports/";
	private final String YEARLY_MILEAGE_URL = "reports/mileage/year";
	private final String ALL_MILEAGE_URL = "reports/mileage/list-user-event-mileage";
	
	@Autowired
	MileageController mileageController;
	
	@GetMapping("mileage/{user}/{year}")
	public String getUserMileageFOrYear(@PathVariable("user") Long userId, @PathVariable("year") int year) {
		
		//mileageController.getYearMileageForUser(year, userId);
		mileageController.getYearMileageForUser(userId, year);
		
		return YEARLY_MILEAGE_URL;
	}
	
	@GetMapping("mileage/{user}")
	public String getAllUserMileage(@PathVariable("user") Long userId, Model model) {
		
		//mileageController.getYearMileageForUser(year, userId);
		List<UserEventMileageDTO> userEventMileage = mileageController.getAllMileageForUser(userId);
		model.addAttribute("events", userEventMileage);
		
		System.out.println("EVENTS FOUND: " + userEventMileage.size());
		
		System.out.println("redirecting to url: " + ALL_MILEAGE_URL);
		return ALL_MILEAGE_URL;
	}
}
