package com.defrainPhoto.pictime.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {
	@Autowired
	ClientRepository clientRepository;

	@Override
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}
	

}
