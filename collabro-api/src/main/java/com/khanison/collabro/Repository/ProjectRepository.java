package com.khanison.collabro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khanison.collabro.Entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
