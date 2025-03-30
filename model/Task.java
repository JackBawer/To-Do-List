package model;

import java.time.LocalDateTime;

public class Task {
    private final int id;
    private final String title;
    private String description;
    private final LocalDateTime deadline;
    private Priority priority;
    private Status status;
    private LocalDateTime dateIssued;
    private final int userId;
    private LocalDateTime completedAt;
    private boolean isDeleted;

    public Task(int id, String title, String description, LocalDateTime deadline, Priority priority, Status status, int userId, LocalDateTime dateIssued, LocalDateTime completedAt, boolean isDeleted) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (userId < 0) {
            throw new IllegalArgumentException("User ID cannot be negative");
        }
        this.id = id;
        this.title = title;
        this.description = (description != null) ? description : "";
        this.deadline = deadline;
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
        this.status = (status != null) ? status : Status.TODO;
        this.userId = userId;
        this.dateIssued = (dateIssued != null) ? dateIssued : LocalDateTime.now();
        this.completedAt = completedAt;
        this.isDeleted = isDeleted;
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

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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
                ", userId=" + userId +
                ", dateIssued=" + dateIssued +
                ", completedAt=" + completedAt +
                ", isDeleted=" + isDeleted +
                '}';
    }
}