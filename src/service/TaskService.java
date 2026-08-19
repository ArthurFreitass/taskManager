package service;

import model.entities.Task;
import model.entities.User;
import model.entities.enums.TaskStatus;
import model.exceptions.DomainException;
import model.exceptions.TaskNotFoundException;
import model.exceptions.UserNotFoundException;
import repository.Repository;

public class TaskService {

    private Repository<Task> taskRepository;

    public TaskService(Repository<Task> taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Repository<User> userRepository, User u, Task task) {
        if (userRepository.findById(u.getName()) == null) {
            throw new UserNotFoundException();
        }
        taskRepository.addItem(task);
    }

    public void removeTask(Repository<User> userRepository, String name, Task task) {

        User userExists = userRepository.findById(name);

        if (userExists != null) {

            if (!userExists.getTasksList().contains(task)) {
                throw new DomainException("User cannot be remove a task that isn't your");
            }

            if (taskRepository.findById(String.valueOf(task.getId())) == null) {
                throw new TaskNotFoundException();
            }

            userExists.remove(task);
            taskRepository.delete(String.valueOf(task.getId()));
        } else {
            throw new UserNotFoundException();
        }
    }

    public void completeTask(Repository<User> userRepository, String name, Integer id) {

        User userExists = userRepository.findById(name);

        if (userExists == null) throw new UserNotFoundException();

        Task task = taskRepository.findById(String.valueOf(id));
        if (task == null) throw new TaskNotFoundException();

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new DomainException("Task it's complete");
        }
        task.setStatus(TaskStatus.COMPLETED);
    }

    public Repository<Task> getTaskRepository() {
        return taskRepository;
    }
}
