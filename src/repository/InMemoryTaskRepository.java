package repository;

import model.entities.Task;
import model.exceptions.DomainException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskRepository implements Repository<Task> {

    private Map<String, Task> taskMap;

    public InMemoryTaskRepository() {
        taskMap = new HashMap<>();
    }

    @Override
    public void addItem(Task task) {
        if (task == null) {
            throw new DomainException("New Task in memory task repository cannot be null!");
        }
        taskMap.put(String.valueOf(task.getId()), task);
    }

    @Override
    public Task findById(String id) {
        if (taskMap.get(id) == null) {
            throw new DomainException("Task not found in memory task repository!");
        }
        return taskMap.get(id);
    }

    @Override
    public List<Task> findAll() {
        if (taskMap.size() == 0) return null;
        List<Task> taskList = new ArrayList<>(taskMap.values());
        return taskList;
    }

    @Override
    public void delete(String id) {
        if (taskMap.remove(id) == null) {
            throw new DomainException("Task not exists in memory task repository");
        }
    }
}
