package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ClientService clientService;

	@PostMapping("/clients")
	public Client addClient(@Valid @RequestBody Client client) {
		log.info("Client REST controller adding new client");
		return clientService.addClient(client);
	}

	@PutMapping("/clients/{id}")
	public Client updateClient(@PathVariable("id") long id, @Valid @RequestBody Client clientDetails) {
		log.info("Client REST controller updating client with ID: " + id);
		Client oldClient = clientService.getClient(id).get();
		if (oldClient == null) {
			log.error("No client found to update");
			return null;
		}

		return clientService.updateClientById(clientDetails).get();
	}

	@GetMapping("/clients")
	public List<Client> getAllClients() {
		log.info("Client REST controller getting all clients");
		return clientService.getAllClients();
	}
	
	@GetMapping("/clients/{id}")
	public Client getClientById(@PathVariable("id") Long id) {
		log.info("Client REST controller getting client with ID: " + id);
		return clientService.getClient(id).get();
	}
	
	@DeleteMapping("/clients/{id}")
	public void delete(@PathVariable("id") Long id) {
		log.info("Client REST controller deleting client with ID: " + id);
		try {
			clientService.deleteClient(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete Client by ID, Empty Result for ID: " + id, e);
		}
	}
}
