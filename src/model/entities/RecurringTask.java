package model.entities;

import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;

import java.time.LocalDate;

public class RecurringTask extends Task {

    private final String type = "RECURRING";

    public RecurringTask(String title, String description, LocalDate dueDate, Priority priority, TaskStatus status, Category category) {
        super(title, description, dueDate, priority, status, category);
    }

    @Override
    public String getTypeDescription() {
        return "Recorrente";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + ";" + type;
    }
}
