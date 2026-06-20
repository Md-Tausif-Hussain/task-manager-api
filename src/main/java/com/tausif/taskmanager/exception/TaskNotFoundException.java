package com.tausif.taskmanager.exception;

/**
 * Thrown when a requested task doesn't exist in the database.
 * Caught by GlobalExceptionHandler and turned into a 404 response.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
