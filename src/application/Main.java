package application;

import model.entities.Category;
import model.entities.SimpleTask;
import model.entities.Task;
import model.entities.User;
import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;
import persistance.FilePersistance;
import repository.InMemoryTaskRepository;
import repository.InMemoryUserRepository;

import java.io.IOException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException {

        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryTaskRepository taskRepository = new InMemoryTaskRepository();

        Category programming = new Category("Programming");
        Category english = new Category("English");
        Category personal = new Category("Personal");

        User arthur = new User("Arthur");
        User lucas = new User("Lucas");
        User maria = new User("Maria");

        userRepository.addItem(arthur);
        userRepository.addItem(lucas);
        userRepository.addItem(maria);

        Task task1 = new SimpleTask(
                "Study Java",
                "Review Collections and Maps",
                LocalDate.now().plusDays(2),
                Priority.HIGH,
                TaskStatus.PENDING,
                programming
        );

        Task task2 = new SimpleTask(
                "Practice English",
                "Watch one hour of English content",
                LocalDate.now().plusDays(1),
                Priority.MEDIUM,
                TaskStatus.PENDING,
                english
        );

        Task task3 = new SimpleTask(
                "Finish Java project",
                "Implement persistence and repositories",
                LocalDate.now().plusDays(5),
                Priority.HIGH,
                TaskStatus.PENDING,
                programming
        );

        Task task4 = new SimpleTask(
                "Read book",
                "Read thirty pages",
                LocalDate.now().plusDays(3),
                Priority.LOW,
                TaskStatus.PENDING,
                personal
        );

        Task task5 = new SimpleTask(
                "Study SQL",
                "Practice SELECT, WHERE and JOIN",
                LocalDate.now().plusDays(4),
                Priority.MEDIUM,
                TaskStatus.PENDING,
                programming
        );

        taskRepository.addItem(task1);
        taskRepository.addItem(task2);
        taskRepository.addItem(task3);
        taskRepository.addItem(task4);
        taskRepository.addItem(task5);

        arthur.add(task1);
        arthur.add(task2);
        arthur.add(task3);

        lucas.add(task4);

        maria.add(task5);

        FilePersistance filePersistance = new FilePersistance();
        filePersistance.save("C:\\Users\\Arthur Freitas\\IdeaProjects\\taskManager\\src\\data\\file.txt", userRepository, taskRepository);
    }
}