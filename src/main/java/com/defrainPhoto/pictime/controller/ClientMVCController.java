package com.defrainPhoto.pictime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.model.Client;

@Controller
public class ClientMVCController {

	@Autowired
	private ClientController clientController;

	@RequestMapping(path = "/listclients")
	public String listAllClients(Model model) {
		List<Client> allClients = clientController.getAllClients();
		while (allClients == null || allClients.isEmpty()) {
			addClients();
			allClients = clientController.getAllClients();
		}
		model.addAttribute("clients", clientController.getAllClients());
		return "client/list-clients";
	}

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
