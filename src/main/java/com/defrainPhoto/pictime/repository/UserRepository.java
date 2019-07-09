package com.defrainPhoto.pictime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defrainPhoto.pictime.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
