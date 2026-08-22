package model.entities;

import model.exceptions.DomainException;

public class Category {

    private String name;
    private String description;

    public Category(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public Category(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new DomainException("Name cannot be null!");
        }
        if (name.isEmpty()) {
            throw new DomainException("Name cannot be empty!");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new DomainException("Name cannot be null!");
        }
        if (description.isEmpty()) {
            throw new DomainException("Name cannot be empty!");
        }
        this.description = description;
    }
}
