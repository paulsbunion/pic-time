package com.defrainPhoto.pictime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientServiceImpl;

@RestController
public class ClientController {

	@Autowired
	ClientServiceImpl clientService;	
	
	@RequestMapping("/client")
	public Client addClient(@RequestBody Client client) {
		return clientService.addClient(client);
	}
}
