package com.narration;
import java.util.UUID;

public class Lesson {

    private final UUID id;
    private String description;  
    private double lessonProgress;
    private String content;
    private boolean completed; 

    public Lesson(UUID id, String description, double lessonProgress, String content ) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.lessonProgress = lessonProgress;
        this.content = content;
    }

     public Lesson(String description, String content) {
        this(UUID.randomUUID(), description, 0.0, content);
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLessonProgress() {
        return lessonProgress;
    }

    public void setLessonProgress(double lessonProgress) {
        this.lessonProgress = lessonProgress;
        this.completed = lessonProgress >= 100.0;  // Auto-set completed if progress is 100%
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
        this.lessonProgress = 100.0;  // Set progress to 100% if marked as complete
    }
}