package com.defrainPhoto.pictime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.repository.ClientRepository;
import com.defrainPhoto.pictime.service.ClientServiceImpl;

@ComponentScan
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceMockUnitTest {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@InjectMocks
	ClientService clientService = new ClientServiceImpl();

	@Mock
	ClientRepository clientRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void getAllClients() {
		List<Client> clientList = new ArrayList<Client>();
		Client client1 = new Client(1l, "Larry", "Evans", "123 South Street, Columbus, OH, 43062", "123-456-7890",
				"larry@E.com", false);
		Client client2 = new Client(2l, "Tim", "Boyd", "488 Eas Main St, Gatlinburg, TN, 77035", "776-252-4545",
				"mondo@yahoo.com", true);
		Client client3 = new Client(3l, "Lisa", "Waters", "292 Cup Street, Detroit, MI, 22921", "993-222-2346",
				"stuff@daniel.com", false);

		clientList.add(client1);
		clientList.add(client2);
		clientList.add(client3);

		when(clientRepository.findByOrderByLastNameAscFirstNameAsc()).thenReturn(clientList);

		assertEquals(3, clientService.getAllClients().size());
	}

	@Test
	public void testInvalidEmail() {
		Client client = new Client(1l, "Bob", "Barker", "address", "227-334-5566", "email", false);
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
		String message = violations.stream().findFirst().get().getMessage();
		assertTrue(message.contains("must be a well-formed email address"));
	}

	@Test
	public void testValidEmail() {
		Client client = new Client(1l, "Bob", "Barker", "address", "227-334-5566", "email@valid", false);
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
		assertTrue(violations.size() == 0);
		
		when(clientRepository.save(client)).thenReturn(client);
		Client savedClient = clientService.addClient(client);
		assertTrue(savedClient == client);
	}

	@Test
	public void testInvalidPhoneNumber() {
		Client client = new Client(1l, "Bob", "Barker", "address", "234-5566", "email@valid", false);
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
		assertEquals("Invalid phone number", violations.stream().findFirst().get().getMessage());
	}

	@Test
	public void testValidPhoneNumber() {
		Client client = new Client(1l, "Bob", "Barker", "address", "228-234-5566", "email@valid", false);
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
		assertEquals(0, violations.size());
		client = new Client(1l, "Bob", "Barker", "address", "2282345566", "email@valid", false);
		violations = validator.validate(client);
		assertEquals(0, violations.size());
		client = new Client(1l, "Bob", "Barker", "address", "1228.234.5566", "email@valid", false);
		violations = validator.validate(client);
		assertEquals(0, violations.size());
		client = new Client(1l, "Bob", "Barker", "address", "+123 (228).234.5566", "email@valid", false);
		System.out.println("+123 (228).234.5566");
		violations = validator.validate(client);
		assertEquals(0, violations.size());
	}

}
