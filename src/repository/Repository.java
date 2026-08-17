package repository;

import java.util.List;

public interface Repository<T> {
    void addItem(T item);
    T findById(String id);
    List<T> findAll();
    void delete(String id);
}
