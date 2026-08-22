package model.entities;

import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;
import model.exceptions.DateException;
import model.exceptions.DomainException;
import persistance.Exportable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Task implements Exportable {

    private final Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private TaskStatus status;
    private Category category;

    private static int count = 0;

    public Task(String title ,String description, LocalDate dueDate, Priority priority, Category category) {
        this.id = ++count;
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.category = category;
    }

    public Integer getId() {
        return id;
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
        if (dueDate == null) {
            throw new DateException("Due date cannot be null");
        }
        if (dueDate.isBefore(LocalDate.now())) {
            throw new DateException("Due date cannot be before Today");
        }
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new DomainException("Priority cannot be null!");
        }
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (category == null) {
            throw new DomainException("Category cannot be null!");
        }
        this.category = category;
    }

    public void setStatus(TaskStatus status) {
        if (status == null) {
            throw new DomainException("Status cannot be null!");
        }
        this.status = status;
    }

    public abstract String getTypeDescription();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, dueDate);
    }

    @Override
    public String toString() {
        return "ID : " + id + " Title : " + title + " Description: " + description;
    }

    @Override
    public String toFileFormat() {
        return "TASK;" + id + ";"+ title + ";" + description + ";" + dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";" + priority + ";" + status + ";"+ category.getName();
    }
}
