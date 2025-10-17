package com.khanison.collabro.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khanison.collabro.Entity.Project;
import com.khanison.collabro.Exception.ResourceNotFoundException;
import com.khanison.collabro.Repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
    }

    @Transactional
    public Project create(Project project) {
        project.setId(null);
        return projectRepository.save(project);
    }

    @Transactional
    public Project update(Long id, Project updatedProject) {
        Project existingProject = findById(id);
        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setProjectStatus(updatedProject.getProjectStatus());
        existingProject.setStartDate(updatedProject.getStartDate());
        existingProject.setEndDate(updatedProject.getEndDate());
        existingProject.setCreatedBy(updatedProject.getCreatedBy());
        return projectRepository.save(existingProject);
    }

    @Transactional
    public void delete(Long id) {
        Project project = findById(id);
        projectRepository.delete(project);
    }
}
