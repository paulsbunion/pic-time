package com.defrainPhoto.pictime.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.controller.MileageController;

@Controller
@RequestMapping("/mvc/reports/")
public class ReportMVCController {

	private final String MVC_REPORTS_URL_BASE = "/mvc/reports/";
	private final String YEARLY_MILEAGE_URL = "reports/mileage/year";
	
	@Autowired
	MileageController mileageController;
	
	@GetMapping("mileage/{year}/{user}")
	public String getUserMileageFOrYear(@PathVariable("year") int year, @PathVariable("user") Long userId) {
		
		mileageController.getYearMileageForUser(year, userId);
		
		return YEARLY_MILEAGE_URL;
	}
}
