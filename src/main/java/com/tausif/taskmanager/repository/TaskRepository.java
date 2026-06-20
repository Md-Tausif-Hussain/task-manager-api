package com.tausif.taskmanager.repository;

import com.tausif.taskmanager.model.Task;
import com.tausif.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Just by extending JpaRepository, you get save(), findById(), findAll(),
 * deleteById(), etc. for free — no SQL needed for basic operations.
 *
 * Spring also auto-generates queries from method names, like the one below:
 * "findByStatus" automatically becomes "SELECT * FROM tasks WHERE status = ?"
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);
}
