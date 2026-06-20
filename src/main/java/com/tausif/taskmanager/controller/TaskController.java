package com.tausif.taskmanager.controller;

import com.tausif.taskmanager.model.Task;
import com.tausif.taskmanager.model.TaskStatus;
import com.tausif.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Defines the REST API endpoints. Each method here maps to one
 * HTTP route. This is the only layer that should know about HTTP
 * concepts like status codes and request/response bodies.
 *
 * Base URL: http://localhost:8080/api/tasks
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks  -> list all tasks
    // GET /api/tasks?status=PENDING -> filter by status
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status) {

        List<Task> tasks = (status != null)
                ? taskService.getTasksByStatus(status)
                : taskService.getAllTasks();

        return ResponseEntity.ok(tasks);
    }

    // GET /api/tasks/5  -> get one task by id
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // GET /api/tasks/search?keyword=report -> search by title
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String keyword) {
        return ResponseEntity.ok(taskService.searchTasksByTitle(keyword));
    }

    // POST /api/tasks  -> create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/tasks/5  -> fully update a task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                            @Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    // PATCH /api/tasks/5/status?status=COMPLETED -> update just the status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id,
                                              @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    // DELETE /api/tasks/5  -> delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
