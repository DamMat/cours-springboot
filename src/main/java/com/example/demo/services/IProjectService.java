package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Project;

public interface IProjectService extends IService<Project> {

	List<Project> getProjectsByPersonId(long idPerson);
	
	Optional<Project> saveOneProjectByPerson(long idPerson, Project project);
	
	Optional<Project> getOneProjectByPerson(long idPerson, long idProject);
	
	Optional<Project> assignOneProjectByPerson(long idPerson, long idProject);
	
	Optional<Project> deleteOneProjectByPerson(long idPerson, long idProject);
	
	Optional<Project> editOneProjectByPerson(long idPerson, long idProject, Project project);

	
}
