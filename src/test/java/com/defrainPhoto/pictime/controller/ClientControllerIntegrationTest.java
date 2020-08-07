package com.defrainPhoto.pictime.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import com.defrainPhoto.pictime.controller.ClientController;
import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientService;
import com.defrainPhoto.pictime.service.ClientServiceImpl;
import com.defrainPhoto.pictime.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
//@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@InjectMocks
	private ClientController clientController;

	@MockBean
	private ClientService clientService;

	@MockBean
	UserService userService;

	@WithMockUser
	@Test
	public void testAddClient() throws Exception {
		Client client = new Client(1l, "Jenny", "Doodle", "123 E Main", "1-221-228-3392", "jenny@email.com", false);

		when(clientService.addClient(client)).thenReturn(client);

		mvc.perform(post("/clients/").with(csrf()).content(asJsonString(client)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is(client.getFirstName())));

	}

	@WithMockUser
	@Test
	public void testAddBadClient() throws Exception {
		Client client = new Client(1l, "Jenny", "Doodle", "123 E Main", "1-221-228-3392", "email", false);
		when(clientService.addClient(client)).thenReturn(client);
		mvc.perform(post("/clients").with(csrf()).content(asJsonString(client)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
//		.andExpect(jsonPath("$.status", is(400)))
//		.andExpect(jsonPath("$.error", is("Invalid email")));

	}

	@WithMockUser
	@Test
	public void testGetAllClients() throws Exception {
		Client client1 = new Client(1l, "Jenny", "Doodle", "123 E Main", "1-221-228-3392", "jenny@email.com", false);
		Client client2 = new Client(1l, "Kyle", "Doodle", "123 E Main", "1-221-228-3392", "kyle@email.com", false);

		when(clientService.getAllClients()).thenReturn(Lists.newArrayList(client1, client2));

		mvc.perform(get("/clients/").with(csrf()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[*]", hasSize(2))).andExpect(jsonPath("$[0].firstName", is("Jenny")))
				.andExpect(jsonPath("$[0].lastName", is("Doodle")))
				.andExpect(jsonPath("$[0].phoneNumber", is("1-221-228-3392")))
				.andExpect(jsonPath("$[0].email", is("jenny@email.com")))
				.andExpect(jsonPath("$[1].firstName", is("Kyle"))).andExpect(jsonPath("$[1].lastName", is("Doodle")))
				.andExpect(jsonPath("$[1].phoneNumber", is("1-221-228-3392")))
				.andExpect(jsonPath("$[1].email", is("kyle@email.com")));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
