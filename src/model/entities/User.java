package model.entities;

import model.exceptions.DomainException;
import persistance.Exportable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Exportable {

    private String name;
    private List<Task> tasksList;

    public User(String name) {
        setName(name);
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

    @Override
    public String toString() {
        return name;
    }

    private String allTasks() {
        String tasks = "";
        for (Task task : getTasksList()) {
            tasks += task + "\n";
        }
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toFileFormat() {
        return "USER;" + name;
    }
}
