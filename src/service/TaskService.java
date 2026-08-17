package service;

import model.entities.Task;
import model.entities.User;
import model.exceptions.DomainException;
import model.exceptions.UserNotFoundException;
import repository.Repository;

public class TaskService {

    private Repository<Task> taskRepository;

    public TaskService(Repository<Task> taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Repository<User> userRepository, User u ,Task task) {
        if (userRepository.findById(u.getName()) == null) {
            throw new UserNotFoundException();
        }
        taskRepository.addItem(task);
    }

    public void removeTask(Repository<User> userRepository, User u, Task task) {

        User userExists = userRepository.findById(u.getName());

        if (userExists != null) {

            if (!userExists.getTasksList().contains(task)) {
                throw new DomainException("User cannot be remove a task that isn't your");
            }
            userExists.remove(task);
            taskRepository.delete(String.valueOf(task.getId()));
        }
    }

    public Repository<Task> getTaskRepository() {
        return taskRepository;
    }
}
