package com.defrainPhoto.pictime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
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
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import com.defrainPhoto.pictime.controller.ClientController;
import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.service.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest(ClientController.class)
//@WebMvcTest
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
//	@Autowired
//	w

	@Autowired
	private ObjectMapper objectMapper;

	private MockRestServiceServer mockServer;

	@InjectMocks
	private ClientController clientController;
	
	@MockBean
	private ClientServiceImpl clientService;

	@Before
	public void setup() {
//			mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@WithMockUser
	@Test
	public void testGetRequestClient() throws Exception {
		Client client = new Client(1l, "Jenny", "Doodle", "123 E Main", "1-221-228-3392", "jenny@email.com", false);

		when(clientService.addClient(client)).thenReturn(client);

		mvc.perform(post("/client/")
				.with(csrf())
				.content(asJsonString(client))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
//				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is(client.getFirstName())));

//		given(clientService.addClient(client)).
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
