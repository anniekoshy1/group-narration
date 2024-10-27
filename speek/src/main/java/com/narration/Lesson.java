/**
 * Represents a lesson within a course, including details like description, progress, content, and completion status.
 */
package com.narration;

import java.util.UUID;

public class Lesson {

    private final UUID id;
    private String description;
    private double lessonProgress;
    private String content;
    private boolean completed;
    private String lessonName;

    /**
     * Constructs a Lesson with specified ID, description, progress, and content.
     *
     * @param id             the unique identifier for the lesson
     * @param description    a brief description of the lesson
     * @param lessonProgress the progress percentage of the lesson
     * @param content        the main content of the lesson
     */
    public Lesson(String lessonName, UUID id, String description, double lessonProgress, String content) {
        this.lessonName = lessonName;
        this.id = UUID.randomUUID();
        this.description = description;
        this.lessonProgress = lessonProgress;
        this.content = content;
    }

    /**
     * Constructs a Lesson with description and content, initializing progress at 0.
     *
     * @param description a brief description of the lesson
     * @param content     the main content of the lesson
     */
    public Lesson(String lessonName, String description, String content) {
        this(lessonName, UUID.randomUUID(), description, 0.0, content);
    }

    public String getLessonName(){
        return lessonName;
    }

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

    /**
     * Sets the lesson progress and automatically marks it as completed if progress reaches 100%.
     *
     * @param lessonProgress the progress percentage of the lesson
     */
    public void setLessonProgress(double lessonProgress) {
        this.lessonProgress = lessonProgress;
        this.completed = lessonProgress >= 100.0;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Checks if the lesson is completed.
     *
     * @return true if the lesson is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Marks the lesson as completed, setting the progress to 100%.
     */
    public void markAsCompleted() {
        this.completed = true;
        this.lessonProgress = 100.0;
    }
}