package com.example.demo.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Person;

public interface IPersonService extends IService<Person> {

	List<Person> findByFirstName(String firstName);
	
	List<Person> findByFirstNameAndLastName(String firstName, String lastName);

	List<Person> findByFirstNameContaining(String firstName);
	
	List<Person> chercherSelonLeNom(String lastName);
	
	List<Person> chercherSelonLePrenom(String firstName);

}
