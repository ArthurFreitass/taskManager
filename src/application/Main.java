package application;

import model.entities.Category;
import model.entities.SimpleTask;
import model.entities.Task;
import model.entities.User;
import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User user = new User("Teste");

        List<Task> tasks = new ArrayList<>();

        SimpleTask task1 = new SimpleTask(
                "Go to the 1221" ,
                "Train chest and triceps",
                LocalDate.of(2026, 8, 12),
                Priority.MEDIUM,
                TaskStatus.PENDING,
                new Category()
        );

        SimpleTask task2 = new SimpleTask(
                "Go to the gym",
                "Train chest and triceps",
                LocalDate.of(2026, 8, 12),
                Priority.MEDIUM,
                TaskStatus.PENDING,
                new Category()
        );

        SimpleTask task3 = new SimpleTask(
                "Go to the gym 111",
                "Train chest and triceps",
                LocalDate.of(2026, 8, 12),
                Priority.MEDIUM,
                TaskStatus.PENDING,
                new Category()
        );

        user.add(task1);
        user.add(task2);
        user.add(task3);

        System.out.println(user.getTasksList());

        SimpleTask task4 = new SimpleTask(
                "Go to the gym 111",
                "Train chest and triceps",
                LocalDate.of(2026, 8, 12),
                Priority.MEDIUM,
                TaskStatus.PENDING,
                new Category()
        );

        user.remove(task4);
    }
}
