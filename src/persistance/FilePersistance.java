package persistance;

import model.entities.*;
import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;
import model.exceptions.DomainException;
import repository.InMemoryTaskRepository;
import repository.InMemoryUserRepository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilePersistance {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FilePersistance() {
    }

    public void save(String path, InMemoryUserRepository userRepo, InMemoryTaskRepository taskRepo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (User user : userRepo.findAll()) {
                bw.write(user.toFileFormat());
                bw.newLine();
                for (Task task : user.getTasksList()) {
                    bw.write(task.toFileFormat());
                    bw.newLine();
                }
            }
        }
    }

    public void load(String path, InMemoryUserRepository userRepo, InMemoryTaskRepository taskRepo) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            User currentUser = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("USER;")) {
                    currentUser = parseUser(line);
                    userRepo.addItem(currentUser);
                } else if (line.startsWith("TASK;")) {
                    if (currentUser == null) {
                        throw new DomainException("Task without user!");
                    }
                    Task task = parseTask(line);
                    taskRepo.addItem(task);
                    currentUser.add(task);
                }
            }
        } catch (IOException e) {
            throw new DomainException("Error reading data file!");
        }
    }

    private User parseUser(String line) {
        String[] parts = line.split(";");
        return new User(parts[1]);
    }

    private Task parseTask(String line) {
        String[] parts = line.split(";");
        String title = parts[2];
        String description = parts[3];
        LocalDate dueDate = LocalDate.parse(parts[4], formatter);
        Priority priority = Priority.valueOf(parts[5]);
        TaskStatus status = TaskStatus.valueOf(parts[6]);
        Category category = new Category(parts[7]);
        String type = parts[8];

        Task task;
        if (type.equals("SIMPLE")) {
            task = new SimpleTask(title, description, dueDate, priority, category);
        } else if (type.equals("RECURRING")) {
            task = new RecurringTask(title, description, dueDate, priority, category);
        } else {
            throw new DomainException("Invalid task type!");
        }

        task.setStatus(status);
        return task;
    }
}