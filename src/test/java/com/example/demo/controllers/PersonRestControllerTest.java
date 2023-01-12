package com.example.demo.controllers;

import java.util.ArrayList;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.entities.Person;
import com.example.demo.entities.Project;
import com.example.demo.services.IPersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IPersonService personService;
	
	@Test
	public void testGetAll() throws Exception {

		Person p1 = new Person(1L, "Wayne", "Bruce", 45);
		Person p2 = new Person(2L, "Kent", "Clark", 45);
		List<Person> personList = new ArrayList<>();
		personList.add(p1);
		personList.add(p2);

		Mockito.when(personService.findAll()).thenReturn(personList);

		String URI = "/api/persons";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(personList);
		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);

	}
	
	@Test
	public void testGetById() throws Exception {

		Person mockPerson = new Person(1L, "admin", "admin", 10);

		Mockito.when(personService.findById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));
		
		String URI = "/api/persons/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockPerson);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testSave() throws Exception {

		Person p1 = new Person(1L, "Wayne", "Bruce", 45);

		String inputInJson = this.mapToJson(p1);

		String URI = "/api/persons";

		Mockito.when(personService.saveOrUpdate(Mockito.any(Person.class))).thenReturn(p1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);

	}
	
	@Test
	public void testEditById() throws Exception {

		Person mockPerson = new Person(1L, "admin", "admin", 10);

		Mockito.when(personService.findById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));

		Person personneFromDB = personService.findById(mockPerson.getId()).get();

		personneFromDB.setFirstName("admino");

		String inputInJson = this.mapToJson(personneFromDB);

		String URI = "/api/persons/1";

		Mockito.when(personService.saveOrUpdate(Mockito.any(Person.class))).thenReturn(mockPerson);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
	}
	
	@Test
	public void testDeletePersonne() throws Exception {
		Person mockPerson = new Person(1L, "admin", "admin", 10);
		Mockito.when(personService.findById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));
		
		Mockito.when(personService.delete(mockPerson.getId())).thenReturn(true);

		String URI = "/api/persons/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andReturn();

		verify(personService).findById(mockPerson.getId());
		verify(personService).delete(mockPerson.getId());

	}
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
