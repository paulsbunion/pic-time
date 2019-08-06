package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Client findByEmail(String email);

}
