package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entities.Project;
import com.example.demo.services.IPersonService;
import com.example.demo.services.IProjectService;

@RestController
public class ProjectRestController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IPersonService personService;

//	@GetMapping("/persons/{personId}/projects")
//	public ResponseEntity<List<Project>> getAllProjetsByPersonneId(@PathVariable(value = "personId") long personId) {
//		personService.findById(personId).orElseThrow(
//				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + personId));
//		List<Project> projets = projectService.getProjectsByPersonId(personId);
//		return new ResponseEntity<>(projets, HttpStatus.OK);
//	}

	@GetMapping("/persons/{personId}/projects")
	public ResponseEntity<List<Project>> getById(@PathVariable(value = "personId") long id) {
		return personService.findById(id).map((p) -> {
			List<Project> projets = projectService.getProjectsByPersonId(p.getId());
			return new ResponseEntity<>(projets, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + " n'existe pas"));
	}

	@PostMapping("/persons/{personId}/projects")
	public ResponseEntity<Project> addProjectByPersonneNum(@PathVariable(value = "personId") long id,
			@RequestBody Project project) {
		Project projectToSave = projectService.saveOneProjectByPerson(id, project).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));
		return new ResponseEntity<>(projectToSave, HttpStatus.CREATED);
	}
	
	@PostMapping("/persons/{personId}/assignProjets")
    public ResponseEntity<Project> assignProjectByPersonneNum(
    		@PathVariable(value = "personId") long id, 
    		@RequestBody Project project){
       Project projectToSave = projectService.assignOneProjectByPerson(id, project.getId()).orElseThrow(
               () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + id));     
       return new ResponseEntity<>(projectToSave, HttpStatus.CREATED);
    }
	
	@DeleteMapping("/persons/{personId}/projects/{projetId}")
    public ResponseEntity<?> deleteProjectByPersonId(@PathVariable(value = "personId") long personId,
                                                     @PathVariable(value = "projetId") long projetId){
        personService.findById(personId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + personId));
        projectService.findById(projetId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id : " + projetId));
        projectService.deleteOneProjectByPerson(personId, projetId);
        return new ResponseEntity<>("DELETED !!!", HttpStatus.CREATED);
    }
	
	@PutMapping("/persons/{personId}/projects/{projectId}")
    public ResponseEntity<Project> editProjectByPersonneNum(@PathVariable(value = "personId") long personId,
                                                            @PathVariable(value = "projectId") long projectId,
                                                            @RequestBody Project projet){
        personService.findById(personId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + personId));
        projectService.findById(projectId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id : " + projectId));
        projectService.editOneProjectByPerson(personId, projectId, projet);
        return new ResponseEntity<>(projet, HttpStatus.CREATED);
    }
	
	@GetMapping("/persons/{personId}/projects/{projetId}")
    public ResponseEntity<Project> getProjectByPersonId(@PathVariable(value = "personId") long personId,
                                                  @PathVariable(value = "projetId") long projetId){
        personService.findById(personId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id : " + personId));
        projectService.findById(projetId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id : " + projetId));
        Project project = projectService.getOneProjectByPerson(personId, projetId).get();
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }
	
	

}
