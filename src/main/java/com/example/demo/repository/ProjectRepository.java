package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<Project> findProjectsByPersonsId(long id);

}
