package model.entities;

import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;
import model.exceptions.DateException;
import model.exceptions.DomainException;

import java.time.LocalDate;

public abstract class Task {

    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private TaskStatus status;
    private Category category;

    public Task(Integer id, String title ,String description, LocalDate dueDate, Priority priority, TaskStatus status, Category category) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        this.priority = priority;
        this.status = status;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id <= 0) {
            throw new DomainException("ID cannot be less or than zero!");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new DomainException("Title cannot be null!");
        }
        if (title.isEmpty()) {
            throw new DomainException("Title cannot be empty!");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new DomainException("Description cannot be null!");
        }
        if (description.isEmpty()) {
            throw new DomainException("Description cannot be empty!");
        }
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate.isBefore(LocalDate.now())) {
            throw new DateException("Due date cannot be before Today");
        }
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public abstract String getTypeDescription();
}
