package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.services.impl.PersonServiceImpl;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

	@TestConfiguration // création des beans nécessaires pour les tests
	static class PersonneServiceTestContextConfiguration {

		@Bean // bean de service
		PersonServiceImpl personneService() {
			return new PersonServiceImpl();
		}

	}
	
	@Autowired
	private PersonServiceImpl personService;
	
	@MockBean // création d'un mockBean pour PersonneRepository
	private PersonRepository personRepository;
	
	@Test
	public void testFindAll() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);
		
		List<Person> persons = Arrays.asList(p1, p2);
		
		// Creation du mock -> implemenetaiton factice ici de la methode findAll() de PersonneRepository
		Mockito.when(personRepository.findAll()).thenReturn(persons);
		
		List<Person> personsFromDb = personService.findAll();
		
		// Teste
		assertNotNull(personsFromDb);
		assertEquals(persons, personsFromDb);
		assertEquals(persons.size(), personsFromDb.size());
		
		verify(personRepository).findAll();
	}
	
	@Test
	public void testSaveOrUpdate() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		
		// Creation du mock -> implementaiton factice ici de la methode save() de PersonneRepository
		Mockito.when(personRepository.save(p1)).thenReturn(p1);
		
		Person person = personService.saveOrUpdate(p1);
		
		// Teste
		assertNotNull(person);
		assertEquals(p1, person);
		assertEquals(p1.getFirstName(), person.getFirstName());

		verify(personRepository).save(p1);
	}
	
	@Test
	public void testFindById() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		
		Mockito.when(personRepository.findById(p1.getId())).thenReturn(Optional.of(p1));

		Person personFromDB = personService.findById(p1.getId()).get();
		assertNotNull(personFromDB);
		
		assertEquals(personFromDB.getId(), p1.getId());
		verify(personRepository).findById(p1.getId());
	}
	
	@Test
	public void testDelete() throws Exception {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);

		doNothing().when(personRepository).deleteById(p1.getId());		
		
		boolean test = personService.delete(p1.getId());
		
		assertEquals(true, test);
		verify(personRepository).deleteById(p1.getId());
	}
}
