package service;

import model.entities.Task;
import model.entities.User;
import model.exceptions.DomainException;
import model.exceptions.DuplicateUserException;
import model.exceptions.UserNotFoundException;
import repository.InMemoryTaskRepository;
import repository.Repository;

import java.util.List;

public class UserService {

    private Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    private void checkName(String name) {
        if (name == null) {
            throw new DomainException("Username cannot be null!");
        }

        if (name.isEmpty()) {
            throw new DomainException("Username cannot be empty!");
        }
    }

    public void addUser(String name) {
        checkName(name);
        if (userRepository.findById(name) != null) {
            throw new DuplicateUserException();
        }

        userRepository.addItem(new User(name));
    }

    public User findUser(String name) {
        checkName(name);
        if (userRepository.findById(name) == null) {
            throw new UserNotFoundException();
        }
        return userRepository.findById(name);
    }

    public void removeUser(String name, Repository<Task> taskRepository) {
        checkName(name);
        User user = userRepository.findById(name);

        if (user == null) {
            throw new UserNotFoundException();
        }

        for (Task task : user.getTasksList()) {
            taskRepository.delete(String.valueOf(task.getId()));
        }

        user.getTasksList().clear();

        userRepository.delete(name);
    }

    public Repository<User> getUserRepository() {
        return userRepository;
    }
}
