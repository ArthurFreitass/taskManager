package model.entities;

import model.entities.enums.Priority;
import model.entities.enums.TaskStatus;

import java.time.LocalDate;

public class RecurringTask extends Task {

    private String type;

    public RecurringTask(String title, String description, LocalDate dueDate, Priority priority, TaskStatus status, Category category) {
        super(title, description, dueDate, priority, status, category);
        this.type = "RECURRING";
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
