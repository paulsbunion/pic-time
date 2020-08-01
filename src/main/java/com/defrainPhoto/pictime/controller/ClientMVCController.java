package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.model.Client;

@Controller
@RequestMapping("/mvc/clients/")
public class ClientMVCController {

	private final String MVC_CLIENT_URL_BASE = "/mvc/clients/";
	private final String LIST_CLIENTS_URL = "client/list-clients";
	private final String EDIT_CLIENT_URL = "client/edit-client";
	private final String NEW_CLIENT_URL = "client/new-client";
	private final String SHOW_CLIENT_URL = "client/show-client";
	
	@Autowired
	private ClientController clientController;

	@GetMapping("list")
	public String listAllClients(Model model) {
		List<Client> allClients = clientController.getAllClients();
		while (allClients == null || allClients.isEmpty()) {
			addClients();
			allClients = clientController.getAllClients();
		}
		model.addAttribute("clients", clientController.getAllClients());
		return LIST_CLIENTS_URL;
	}
	
	@GetMapping("show/{id}")
	public String shoClientDetails(@PathVariable("id") long id, Model model) {
		model.addAttribute("client", clientController.getClientById(id));
		return SHOW_CLIENT_URL;
	}

	@GetMapping("new")
	public String newClientForm(Model model) {
		model.addAttribute("client", new Client());
		return NEW_CLIENT_URL;
	}
	
	@PostMapping("save")
	public String saveNewClient(@Valid Client client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return NEW_CLIENT_URL;
		}
		clientController.addClient(client);
		return "redirect:" + MVC_CLIENT_URL_BASE + "list";
	}
	
	@GetMapping("edit/{id}")
	public String showEditClientForm(@PathVariable("id") long id, Model model) {
		Client client = clientController.getClientById(id);
		model.addAttribute("client", client);
		return EDIT_CLIENT_URL;
	}
	
	@PostMapping("update/{id}")
	public String updateEditedClient(@Valid Client client, BindingResult result, @PathVariable("id") Long id, Model model) {
		if (result.hasErrors()) {
			client.setId(id);
			return EDIT_CLIENT_URL;
		}
		clientController.updateClient(id, client);
		return "redirect:" + MVC_CLIENT_URL_BASE + "list";
	}
	
	@GetMapping("delete/{id}")
	public String deleteClient(@PathVariable("id") long id) {
		clientController.delete(id);
		return "redirect:" + MVC_CLIENT_URL_BASE + "list";
	}
	/**
	 * Helper method for testing data
	 */
	private void addClients() {
		Client client1 = new Client(1l, "Larry", "Evans", "123 South Street, Columbus, OH, 43062", "123-456-7890",
				"larry@E.com", false);
		Client client2 = new Client(2l, "Tim", "Boyd", "488 Eas Main St, Gatlinburg, TN, 77035", "776-252-4545",
				"mondo@yahoo.com", true);
		Client client3 = new Client(3l, "Lisa", "Waters", "292 Cup Street, Detroit, MI, 22921", "993-222-2346",
				"stuff@daniel.com", false);

		clientController.addClient(client1);
		clientController.addClient(client2);
		clientController.addClient(client3);
	}
}
