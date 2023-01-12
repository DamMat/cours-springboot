package com.example.demo.repository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.demo.entities.Person;

@RunWith(SpringRunner.class) // Declare cette classe comme classe de test
@DataJpaTest // pour tester les JPA repositories
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void testFindAll() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		List<Person> personsFromDb = personRepository.findAll();

		// Teste
		assertEquals(persons, personsFromDb);
		assertThat(personsFromDb.equals(persons));
	}
	
	@Test
	public void testFindByFirstName() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		List<Person> personsFromDb = personRepository.findByFirstName("Wayne");

		// Teste
		assertThat(personsFromDb.contains(p1));
	}
	
	@Test
	public void testFindByFirstNameAndLastName() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		List<Person> personsFromDb = personRepository.findByFirstNameAndLastName("Wayne", "Bruce");

		// Teste
		assertThat(personsFromDb.contains(p1));
	}
	
	@Test
	public void testFindByFirstNameContaining() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		List<Person> personsFromDb = personRepository.findByFirstNameContaining("ay");

		// Teste
		assertThat(personsFromDb.contains(p1));
	}
	
	@Test
	public void testChercherSelonLeNom() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		List<Person> personsFromDb = personRepository.chercherSelonLeNom("Bruce");

		// Teste
		assertThat(personsFromDb.contains(p1));
	}
	
	@Test
	public void testchercherSelonLePrenom() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		List<Person> personsFromDb = personRepository.chercherSelonLeNom("Wayne");

		// Teste
		assertThat(personsFromDb.contains(p1));
	}

	@Test
	public void testFindById() {
		Person savedInDb = personRepository.save(new Person(1L, "Doe", "John", 40));
		Person getFromDb = personRepository.findById(savedInDb.getId()).get();

		assertEquals(savedInDb, getFromDb);
		assertThat(getFromDb).isEqualTo(savedInDb);
	}

	@Test
	public void testSave() {
		Person savedInDb = personRepository.save(new Person(1L, "Wick", "John", 40));
		Person getFromDb = personRepository.findById(savedInDb.getId()).get();

		assertEquals(savedInDb, getFromDb);
		assertThat(getFromDb).isEqualTo(savedInDb);
	}

	@Test
	public void testDelete() {
		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);

		List<Person> persons = Arrays.asList(p1, p2);

		personRepository.saveAll(persons);

		Person getFromDb = personRepository.findById(p1.getId()).get();
		personRepository.deleteById(getFromDb.getId());

		List<Person> personsFromDb = personRepository.findAll();

		assertNotEquals(persons, personsFromDb);
		assertTrue(!personsFromDb.contains(getFromDb));
		assertFalse(personsFromDb.contains(getFromDb));
		assertThat(personsFromDb.contains(getFromDb)).isFalse();

	}
	

	// Methode qui teste que si on appelle la methode findById() avec une valeur inexistante,4
	// alors, on attends une instance de NoSuchElementException
	@Test(expected = NoSuchElementException.class)
	public void testNoSuchElementException() {
		personRepository.findById(5L).get();
	}

}
