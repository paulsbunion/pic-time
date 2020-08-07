package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientServiceSpringIntegrationTest {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Autowired
	ClientService clientService;

	@Before
	public void init() {

	}

//	@Test(expected = ValidationException.class)
	@Test(expected = TransactionSystemException.class)
	public void testAddInvalidClient() {
		Client client1 = new Client(1l, "Larry", "Evans", "123 South Street, Columbus, OH, 43062", "123-456-7890",
				"larryEmail", false);
		clientService.addClient(client1);
	}

	@Test
	public void testAddClient() {
		Client client1 = new Client(1l, "Larry", "Evans", "123 South Street, Columbus, OH, 43062", "123-456-7890",
				"larry@Email", false);
		clientService.addClient(client1);
		assertEquals(client1, clientService.getClient(1l));
	}
}
