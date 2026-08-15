package persistance;

import model.entities.*;
import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;
import model.exceptions.DomainException;
import repository.InMemoryTaskRepository;
import repository.InMemoryUserRepository;

import java.io.*;
import java.time.LocalDate;

public class FilePersistance {

    public FilePersistance() {
    }

    public void save(String path, InMemoryUserRepository userRepo, InMemoryTaskRepository taskRepo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            for (User u : userRepo.findAll()) {
                bw.write(u.toFileFormat());
                bw.newLine();
            }
            for (Task task : taskRepo.findAll()) {
                bw.write(task.toFileFormat());
                bw.newLine();
            }
        }
    }

    public void load(String path, InMemoryUserRepository userRepo, InMemoryTaskRepository taskRepo) throws IOException {

        File file = new File(path);

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("USER;")) {
                    userRepo.addItem(parseUser(line));
                } else if (line.startsWith("TASK;")) {
                    taskRepo.addItem(parseTask(line));
                }
            }

        } catch (IOException e) {
            throw new DomainException("Error no data in File");
        }
    }

    private User parseUser(String line) {
        String [] arr = line.split(";");
        return new User(arr[1]);
    }

    private Task parseTask(String line) {
        String[] parts = line.split(";");
        String id = parts[1];
        String title = parts[2];
        String description = parts[3];
        LocalDate dueDate = parts[4].isEmpty() ? null : LocalDate.parse(parts[4]);
        Priority priority = Priority.valueOf(parts[5]);
        TaskStatus status = TaskStatus.valueOf(parts[6]);
        Category category = new Category(parts[7]);
        String type = parts[8];

        if (type.equals("SIMPLE")) {
            SimpleTask t = new SimpleTask(title, description, dueDate, priority, status, category);
            return t;
        } else if (type.equals("RECURRING")) {
            return new RecurringTask(title, description, dueDate, priority, status, category);
        }
        return null;
    }
}
