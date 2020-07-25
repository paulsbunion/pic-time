package com.defrainPhoto.pictime;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientServiceImpl;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = ClientServiceImpl.class)
//@DataJpaTest
@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
public class SpringIntegrationTestClientService {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Autowired
	ClientServiceImpl clientService;
	
	@Before
	public void init() {

	}

	@Test(expected = ValidationException.class)
	public void testAddInvalidClient() {
		Client client1 = new Client(1l, "Larry", "Evans", "123 South Street, Columbus, OH, 43062", "123-456-7890",
				"larryEmail", false);
//		when(clientRepository.save(client1)).thenReturn(client1);
		clientService.addClient(client1);

		Set<ConstraintViolation<Client>> violations = validator.validate(client1);

	}
}
