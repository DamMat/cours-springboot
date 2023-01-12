package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	// Methodes personnalises
	List<Person> findByFirstName(String firstName);
	
	List<Person> findByFirstNameAndLastName(String firstName, String lastName);

	List<Person> findByFirstNameContaining(String firstName);
	
	@Query("select p from Person p where p.lastName =:lastName")
	List<Person> chercherSelonLeNom(@Param("lastName") String lastName);
	
	@Query("select p from Person p where p.firstName =?1")
	List<Person> chercherSelonLePrenom(String firstName);

}
