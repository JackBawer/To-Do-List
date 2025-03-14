package model;

import java.time.LocalDateTime;

public class Task {
    private final int id;
    private final String title;
    private String description;
    private final LocalDateTime deadline;
    private Priority priority;
    private Status status;

    public Task(String title, String description, LocalDateTime deadline, Priority priority, Status status) {
        this(0, title, description, deadline, priority, status);
    }
    public Task(int id, String title, String description, LocalDateTime deadline, Priority priority, Status status) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
        this.status = (status != null) ? status : Status.TODO;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (description != null) ? description : "";
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = (status != null) ? status : Status.TODO;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }
}
