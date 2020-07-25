package com.defrainPhoto.pictime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.defrainPhoto.pictime.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Optional<Client> findByEmail(String email);
	Optional<Client> findByFirstNameAndLastName(String firstName, String lastName);

}
