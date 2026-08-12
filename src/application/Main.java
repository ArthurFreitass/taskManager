package application;

import model.entities.Category;
import model.entities.SimpleTask;
import model.entities.Task;
import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;
import repository.InMemoryTaskRepository;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Task task1 = new SimpleTask(
                "a", "b", LocalDate.now(), Priority.HIGH, TaskStatus.COMPLETED, (new Category())
        );
        Task task2 = new SimpleTask(
                "b", "b", LocalDate.now(), Priority.HIGH, TaskStatus.COMPLETED, (new Category())
        );
        Task task3 = new SimpleTask(
                "c", "b", LocalDate.now(), Priority.HIGH, TaskStatus.COMPLETED, (new Category())
        );

        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();

        taskRepository.addItem(task1);
        taskRepository.addItem(task2);
        taskRepository.addItem(task3);

        taskRepository.delete("3");

        System.out.println(taskRepository.findAll());
    }
}
