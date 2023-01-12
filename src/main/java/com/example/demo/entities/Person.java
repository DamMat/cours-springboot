package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Data // Getter, Setter, ToString(), RequiredArgsContructor
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private Long id;
	@Column(name = "first_name")
	@NonNull // Necessaire pr ajouter des valeurs ds RequiredArgsContructor
	private String firstName;
	@Column(name = "last_name")
	@NonNull
	private String lastName;
	@Column(name = "age")
	@NonNull
	private Integer age;
	
	// CascadeType.PERSIST : lors de la persistance d'une personne, conserve
	// également ses projets.
	// CascadeType.REMOVE : lors de la suppression d'une personne, il supprime
	// également ses projets
	// les entités voitures.
	// CascadeType.REFRESH : lors de l'actualisation d'une personne, actualise
	// également ses projets
	// CascadeType.MERGE : lors de la fusion de l'état de l'entité personne,
	// fusionne également les entités contenues dans projets.	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "persons_projects", joinColumns = { @JoinColumn(name = "id_person") }, inverseJoinColumns = {
			@JoinColumn(name = "id_project") })
	private List<Project> projects = new ArrayList<>();

}
