package com.narration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Language {

    private final UUID id;  // Marked as final
    private User user;  // The user who is learning the language
    private StarterTest starterTest;  // The initial test for placement
    private final String name;  // Marked as final
    private double coursePercentage;  // Progress percentage for courses
    private double totalPercentage;  // Overall progress percentage in the language
    private double languageProgress;  // Progress in learning the language
    private ArrayList<String> keyWords;  // Keywords related to the language
    private ArrayList<Course> completedCourses;  // List of completed courses
    private ArrayList<Assessment> completedAssessments;  // List of completed assessments
    private HashMap<Course, Boolean> courseAccess;

    public Language(User user, String name) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.name = name;
        this.coursePercentage = 0.0;
        this.totalPercentage = 0.0;
        this.languageProgress = 0.0;
        this.keyWords = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
        this.completedAssessments = new ArrayList<>();
        this.courseAccess = new HashMap<>();
    }

    public Language(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.coursePercentage = 0.0;
        this.totalPercentage = 0.0;
        this.languageProgress = 0.0;
        this.keyWords = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
        this.completedAssessments = new ArrayList<>();
        this.courseAccess = new HashMap<>();
    }

    public Language(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCourseAccess(Course course, boolean access) {
        courseAccess.put(course, access);
    }

    public double getTotalPercentage() {
        return totalPercentage;
    }

    public double getCoursePercentage() {
        return coursePercentage;
    }

    public double getLanguageProgress() {
        return languageProgress;
    }

    public void setLanguageProgress(double languageProgress) {
        this.languageProgress = languageProgress;
        updateTotalPercentage();
    }

    public ArrayList<Course> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(ArrayList<Course> completedCourses) {
        this.completedCourses = completedCourses;
        updateCoursePercentage();
    }

    public boolean takenStarterTest() {
        return starterTest != null;
    }

    public void addKeyWord(String keyWord) {
        keyWords.add(keyWord);
    }

    private void updateTotalPercentage() {
        this.totalPercentage = (this.coursePercentage + this.languageProgress) / 2.0;
    }

    private void updateCoursePercentage() {
        if (!completedCourses.isEmpty()) {
            this.coursePercentage = 100.0;
        }
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<String> getKeyWords() {
        return keyWords;
    }

    public void addCompletedAssessment(Assessment assessment) {
        completedAssessments.add(assessment);
    }

    public ArrayList<Assessment> getCompletedAssessments() {
        return completedAssessments;
    }
}