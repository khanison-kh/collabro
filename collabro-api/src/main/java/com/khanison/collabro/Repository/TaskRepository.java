package com.khanison.collabro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khanison.collabro.Entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
