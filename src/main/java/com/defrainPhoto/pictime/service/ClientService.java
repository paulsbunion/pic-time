package com.defrainPhoto.pictime.service;

import java.util.List;
import java.util.Optional;

import com.defrainPhoto.pictime.model.Client;

public interface ClientService {

	List<Client> getAllClients();

	Client addClient(Client client);

	Optional<Client> updateClientById(Client newClient);

	Optional<Client> getClient(String email);

	Optional<Client> getClient(String firstName, String lastName);

	Optional<Client> getClient(Long id);
	
	void deleteClient(Long id);
	
}
