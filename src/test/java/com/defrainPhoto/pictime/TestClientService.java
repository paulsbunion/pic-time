package com.defrainPhoto.pictime;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.repository.ClientRepository;
import com.defrainPhoto.pictime.service.ClientService;
import com.defrainPhoto.pictime.service.ClientServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestClientService {
	
	@InjectMocks
	ClientServiceImpl clientService;
	
	@Mock
	ClientRepository clientRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllClients() {
		List<Client> clientList = new ArrayList<Client>();
		Client client1 = new Client(1l, "Larry", "Evans", "123 South Street, Columbus, OH, 43062",
				"123-456-7890", "larry@E.com", false); 
		Client client2 = new Client(2l, "Tim", "Boyd", "488 Eas Main St, Gatlinburg, TN, 77035",
				"776-252-4545", "mondo@yahoo.com", true);
		Client client3 = new Client(3l, "Lisa", "Waters", "292 Cup Street, Detroit, MI, 22921",
				"993-222-2346", "stuff@daniel.com", false);
		
		clientList.add(client1);
		clientList.add(client2);
		clientList.add(client3);
		
		when(clientRepository.findAll()).thenReturn(clientList);
		
		assertEquals(3, clientService.getAllClients().size());
	}
}
