package repository;

import model.entities.Task;

import java.util.List;

public interface Repository<T> {
    void add(Task task);
    void remove(int id);
    List<T> findAll();
    T findById(int id);
}
