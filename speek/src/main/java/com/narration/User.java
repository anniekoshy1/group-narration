package com.narration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private final UUID id;
    private String username;  // Username of the user
    private String email;  // Email address of the user
    private String password;  // User's password
    private final ArrayList<Course> courses;  // List of courses the user is enrolled in
    private HashMap<UUID, Double> progress;  // Map to track the user's progress for each course
    private final ArrayList<UUID> completedCourses;  // List of completed courses
    private UUID currentCourseID;  // The current course the user is working on
    private final ArrayList<Language> languages;  // List of languages the user is learning
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

    public User(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.courses = new ArrayList<>();
        this.progress = new HashMap<>();
        this.completedCourses = new ArrayList<>();
        this.currentCourseID = null;
        this.languages = new ArrayList<>();
        this.currentLanguageID = null;
        this.currentLanguageName = "English";
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public double getCourseProgress(UUID courseId) {
        return progress.getOrDefault(courseId, 0.0);
    }

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

    public UUID getCurrentCourse() {
        return currentCourseID;
    }

    public void setCurrentCourse(UUID courseId) {
        this.currentCourseID = courseId;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void addLanguage(Language language) {
        languages.add(language);
    }

    public UUID getCurrentLanguage() {
        return currentLanguageID;
    }

    public void setCurrentLanguage(UUID languageId) {
        this.currentLanguageID = languageId;
    }

    public String getCurrentLanguageName() {
        return currentLanguageName;
    }

    public void setCurrentLanguageName(String languageName) {
        this.currentLanguageName = languageName;
    }

    public HashMap<UUID, Double> getProgress() {
        return progress;
    }

    public void setProgress(HashMap<UUID, Double> progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "User: " + username + "\nEmail: " + email + "\nCurrent Language: " + currentLanguageName;
    }
}