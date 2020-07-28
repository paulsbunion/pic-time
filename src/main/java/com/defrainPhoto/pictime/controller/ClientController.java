package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientService;
import com.defrainPhoto.pictime.service.ClientServiceImpl;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;	
	
	@PostMapping("/client")
	public Client addClient(@Valid @RequestBody Client client) {
		return clientService.addClient(client);
	}
	
	@GetMapping("/client")
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}
}
