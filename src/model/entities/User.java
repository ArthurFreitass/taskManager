package model.entities;

import model.exceptions.DomainException;

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

    public void addTask(Task task) {
        tasksList.add(task);
    }

    public void removeTask(Task task) {
        tasksList.remove(task);
    }
}
