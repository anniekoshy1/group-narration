package com.narration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Language {
 
    private UUID id; 
    private User user;  // The user who is learning the language
    private StarterTest starterTest;  // The initial test for placement
    private String name; 
    private double coursePercentage;  // Progress percentage for courses
    private double totalPercentage;  // Overall progress percentage in the language
    private double languageProgress;  // Progress in learning the language
    private ArrayList<String> keyWords;  // Keywords related to the language
    private ArrayList<Course> completedCourses;  // List of completed courses
    private ArrayList<Assessment> completedAssessments;  // List of completed assessments
    private HashMap<Course, Boolean> courseAccess;
    private Dictionary dictionary;

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
        this.dictionary = new Dictionary(new WordsList());
    }
    public Language(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Get the name of the language
    public String getName() {
        return name;
    }

    public User getUser(){
        return user;
    }

    public void setUser (User user){
        this.user = user;
    }

    // Set access to a specific course
    public void setCourseAccess(Course course, boolean access) {
        courseAccess.put(course, access);
    }

    // Get the total percentage of progress for the language
    public double getTotalPercentage() {
        return totalPercentage;
    }

    // Get the course percentage for the language
    public double getCoursePercentage() {
        return coursePercentage;
    }

    // Get the overall progress in the language
    public double getLanguageProgress() {
        return languageProgress;
    }

    // Set the progress for the language
    public void setLanguageProgress(double languageProgress) {
        this.languageProgress = languageProgress;
        updateTotalPercentage();
    }

    // Get the completed courses in the language
    public ArrayList<Course> getCompletedCourses() {
        return completedCourses;
    }

    // Set the completed courses for the language
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
        if (completedCourses.size() > 0) {
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