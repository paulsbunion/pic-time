package com.defrainPhoto.pictime.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc/")
public class HomeContoller {
	
	@GetMapping("home")
	public String getHome() {
		return "index";
	}

}
