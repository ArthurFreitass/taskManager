package application;

import model.entities.User;
import persistance.FilePersistance;
import repository.InMemoryTaskRepository;
import repository.InMemoryUserRepository;
import service.TaskService;
import service.UserService;
import ui.ConsoleUI;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();
        InMemoryUserRepository userRepository = new InMemoryUserRepository();

        FilePersistance filePersistance = new FilePersistance();

        // ADD your file path, taskRepository and user Repository in parameter filePersistance.load();

        TaskService taskService = new TaskService(taskRepository);
        UserService userService = new UserService(userRepository);

        ConsoleUI ui = new ConsoleUI(sc, taskService, userService);
        ui.showMenu();

        // / ADD your file path, taskRepository and user Repository in parameter filePersistance.save();

        sc.close();
    }
}