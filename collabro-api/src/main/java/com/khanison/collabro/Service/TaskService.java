package com.khanison.collabro.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khanison.collabro.Entity.Task;
import com.khanison.collabro.Exception.ResourceNotFoundException;
import com.khanison.collabro.Repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
    }

    @Transactional
    public Task create(Task task) {
        task.setId(null);
        return taskRepository.save(task);
    }

    @Transactional
    public Task update(Long id, Task updatedTask) {
        Task existingTask = findById(id);
        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setTaskStatus(updatedTask.getTaskStatus());
        existingTask.setProject(updatedTask.getProject());
        existingTask.setAssignedUser(updatedTask.getAssignedUser());
        return taskRepository.save(existingTask);
    }

    @Transactional
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
}
