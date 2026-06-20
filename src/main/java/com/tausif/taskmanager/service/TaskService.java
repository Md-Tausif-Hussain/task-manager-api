package com.tausif.taskmanager.service;

import com.tausif.taskmanager.exception.TaskNotFoundException;
import com.tausif.taskmanager.model.Task;
import com.tausif.taskmanager.model.TaskStatus;
import com.tausif.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service layer holds business logic and keeps the controller "thin".
 * The controller shouldn't talk to the repository directly — it goes
 * through this service instead. This separation makes the app easier
 * to test and maintain as it grows.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        return taskRepository.save(existingTask);
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id); // ensures it exists before deleting
        taskRepository.delete(task);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> searchTasksByTitle(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
