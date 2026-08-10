package model.entities;

import model.exceptions.DomainException;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private List<Task> tasksList;

    public User(String name) {
        this.name = name;
        this.tasksList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new DomainException("Name cannot be null!");
        }
        if (name.isEmpty()) {
            throw new DomainException("Description cannot be empty!");
        }
        this.name = name;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void add(Task task) {
        if (task == null) {
            throw new DomainException("New task cannot be null!");
        }
        tasksList.add(task);
    }

    public void remove(Task task) {
        if (tasksList.size() == 0) {
            throw new DomainException("Add new task first!");
        }
        if (!tasksList.contains(task)) {
            throw new DomainException("Task not found!");
        }
        tasksList.remove(task);
    }
}
