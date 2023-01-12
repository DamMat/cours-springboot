package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.PersonDto;
import com.example.demo.entities.Person;
import com.example.demo.services.IPersonService;

// Fichier qui mappe sur des chemins et qui va nous renvoyer
// des reponses au format JSon, XML
@RestController
// permet la communication entre applications (Backend <=> Frontend)
@CrossOrigin("*")
@RequestMapping("/api/persons")
public class PersonRestController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired // utilisee pr l'injection de dependances
	private IPersonService personService;

	@GetMapping()
	public ResponseEntity<List<Person>> getAll() {
		return new ResponseEntity<List<Person>>(personService.findAll(), HttpStatus.OK);
	}
	
	// /api/persons/showSome?firstName=...&lastName=...
	@GetMapping("/showSome")
	public ResponseEntity<List<Person>> getAll(
			@RequestParam(value = "firstName") String firstName, 
			@RequestParam(value = "lastName") String lastName) {
		return new ResponseEntity<List<Person>>(personService.findByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
	}
	
	// /api/persons/showSome2/nom2/prenom2
	@GetMapping("/showSome2/{firstName}/{lastName}")
	public ResponseEntity<List<Person>> getAll2(
			@PathVariable(value = "firstName") String firstName, 
			@PathVariable(value = "lastName") String lastName) {
		return new ResponseEntity<List<Person>>(personService.findByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
	}

	// /api/persons
	@PostMapping()
	public ResponseEntity<Person> create(@RequestBody @Valid PersonDto personDto) {
//		Person person = new Person();
//		person.setFirstName(personDto.getFirstName());
//		person.setLastName(personDto.getLastName());
//		person.setAge(personDto.getAge());
		Person personToSave = modelMapper.map(personDto, Person.class);	
		return new ResponseEntity<Person>(personService.saveOrUpdate(personToSave), HttpStatus.CREATED);
	}

	// /api/persons/1
	@GetMapping("/{id}")
	public ResponseEntity<Person> getById(@PathVariable long id) {
		return personService.findById(id).map((p) -> {
			return new ResponseEntity<Person>(p, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + "n'existe pas"));
	}

	// /api/persons/1
	@PutMapping("/{id}")
	public ResponseEntity<Person> editById(@PathVariable long id, @RequestBody @Valid PersonDto personDto) {
		return personService.findById(id).map((p) -> {
//			p.setFirstName(person.getFirstName());
//			p.setLastName(person.getLastName());
//			p.setAge(person.getAge());
			modelMapper.map(personDto, p);
			personService.saveOrUpdate(p);
			return new ResponseEntity<Person>(p, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + "n'existe pas"));
	}

	// /api/persons/1
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable long id)  {
		return personService.findById(id).map((p) -> {
			personService.delete(p.getId());
			return new ResponseEntity<>(true, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + "n'existe pas"));
	}	
}
