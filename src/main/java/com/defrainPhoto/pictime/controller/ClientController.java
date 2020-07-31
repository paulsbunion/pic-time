package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;

	@PostMapping("/clients")
	public Client addClient(@Valid @RequestBody Client client) {
		return clientService.addClient(client);
	}

	@PutMapping("/clients/{id}")
	public Client updateClient(@PathVariable("id") long id, @Valid @RequestBody Client clientDetails) {
		Client oldClient = clientService.getClient(id).get();
		if (oldClient == null) {
			return null;
		}

		return clientService.updateClientById(clientDetails).get();
	}

	@GetMapping("/clients")
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}
	
	@GetMapping("/clients/{id}")
	public Client getClientById(@PathVariable("id") Long id) {
		return clientService.getClient(id).get();
	}
	
	@DeleteMapping("/clients/{id}")
	public void delete(@PathVariable("id") Long id) {
		clientService.deleteClient(id);
	}
}
