package com.defrainPhoto.pictime.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.repository.ClientRepository;

@Service
@Validated
public class ClientServiceImpl implements ClientService {
	@Autowired
	ClientRepository clientRepository;
//	@Autowired
//	private Validator validator;

	@Override
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	@Override
	@Validated
	public Client addClient(@Valid Client client) {
		validate(client);
		Client saved = clientRepository.save(client);
		return saved;
	}

	@Override
	public Optional<Client> getClient(Long id) {
		return clientRepository.findById(id);
	}

	@Override
	public Optional<Client> getClient(String firstName, String lastName) {
		return clientRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public Optional<Client> getClient(String email) {
		return clientRepository.findByEmail(email);
	}

	@Override
	public Optional<Client> updateClientById(@Valid Client newClient) {
		validate(newClient);
		return Optional.of(clientRepository.save(newClient));
	}

	@Override
	public void deleteClient(Long id) {
		clientRepository.deleteById(id);
	}

	private void validate(Client client) {
//		Set<ConstraintViolation<Client>> violations = validator.validate(client);
//		if (!violations.isEmpty()) {
//			StringBuilder sb = new StringBuilder();
//			violations.stream().forEach(v -> sb.append(v.getMessage()));
//			throw new ConstraintViolationException("Error occured: " + sb.toString(), violations);
//		}
	}
}
