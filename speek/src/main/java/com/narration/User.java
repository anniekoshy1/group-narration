package com.narration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private UUID id;
    private String username;  // Username of the user
    private String email;  // Email address of the user
    private String password;  // User's password
    private ArrayList<Course> courses;  // List of courses the user is enrolled in
    private HashMap<UUID, Double> progress;  // Map to track the user's progress for each course
    private ArrayList<UUID> completedCourses;  // List of completed courses
    private UUID currentCourseID;  // The current course the user is working on
    private ArrayList<Language> languages;  // List of languages the user is learning
    private UUID currentLanguageID;  // The ID of the current language the user is learning
    private String currentLanguageName;  // The name of the current language the user is learning

    public User(UUID id, String username, String email, String password, ArrayList<Course> courses,
                HashMap<UUID, Double> progress, ArrayList<UUID> completedCourses, UUID currentCourseID,
                ArrayList<Language> languages, UUID currentLanguageID, String currentLanguageName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.courses = courses;
        this.progress = progress;
        this.completedCourses = completedCourses;
        this.currentCourseID = currentCourseID;
        this.languages = languages;
        this.currentLanguageID = currentLanguageID;
        this.currentLanguageName = currentLanguageName;
    }

    public UUID getId() {
        return id;
    }

    // Get the user's username
    public String getUsername() {
        return username;
    }

    // Set the user's username
    public void setUsername(String username) {
        this.username = username;
    }

    // Get the user's email
    public String getEmail() {
        return email;
    }

    // Set the user's email
    public void setEmail(String email) {
        this.email = email;
    }

    // Get the user's password
    public String getPassword() {
        return password;
    }

    // Set the user's password
    public void setPassword(String password) {
        this.password = password;
    }

    // Get the list of courses the user is enrolled in
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // Add a course to the user's list of courses
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Get the user's progress in a specific course
    public double getCourseProgress(UUID courseId) {
        return progress.getOrDefault(courseId, 0.0);
    }

    // Update the user's progress for a specific course
    public void updateCourseProgress(UUID courseId, double newProgress) {
        progress.put(courseId, newProgress);
    }

    public ArrayList<UUID> getCompletedCourses() {
        return completedCourses;
    }

    public void completeCourse(UUID courseId) {
        if (!completedCourses.contains(courseId)) {
            completedCourses.add(courseId);
        }
    }

    // Get the current course the user is working on
    public UUID getCurrentCourse() {
        return currentCourseID;
    }

    // Set the current course the user is working on
    public void setCurrentCourse(UUID courseId) {
        this.currentCourseID = courseId;
    }

    // Get the list of languages the user is learning
    public ArrayList<Language> getLanguages() {
        return languages;
    }

    // Add a new language to the user's list of languages
    public void addLanguage(Language language) {
        languages.add(language);
    }

    // Get the current language ID the user is learning
    public UUID getCurrentLanguage() {
        return currentLanguageID;
    }

    // Set the current language ID the user is learning
    public void setCurrentLanguage(UUID languageId) {
        this.currentLanguageID = languageId;
    }

    // Get the current language name the user is learning
    public String getCurrentLanguageName() {
        return currentLanguageName;
    }

    // Set the current language name the user is learning
    public void setCurrentLanguageName(String languageName) {
        this.currentLanguageName = languageName;
    }

    // Get the user's progress in all courses
    public HashMap<UUID, Double> getProgress() {
        return progress;
    }

    // Set the user's progress
    public void setProgress(HashMap<UUID, Double> progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "User: " + username + "\nEmail: " + email + "\nCurrent Language: " + currentLanguageName;
    }
}