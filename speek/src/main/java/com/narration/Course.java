package com.narration;
import java.util.ArrayList;
import java.util.UUID;

public class Course {

    private String name;  // Name of the course
    private String description;  // Description of the course
    private boolean userAccess;  // Access status of the course
    private double courseProgress;  // Progress of the course (0.0 to 100.0)
    private ArrayList<Lesson> lessons;  // List of lessons in the course
    private ArrayList<Assessment> assessments;  // List of assessments in the course
    private ArrayList<String> keyWords;  // Keywords related to the course
    private UUID id;  // Unique identifier for the course
    private boolean completed;  // Indicates whether the course is completed
    private ArrayList<String> completedAssessments;  // List of completed assessments
    private FlashcardQuestion flashcard;  // Track the progress of the flashcard
    private Lesson lesson;
    


    // Full constructor to set up a course with all details
    public Course(UUID id, String name, String description, boolean userAccess, double courseProgress, boolean completed, ArrayList<Lesson> lessons, ArrayList<Assessment> assessments, ArrayList<String> completedAssessments, Lesson lesson, FlashcardQuestion flashcard) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userAccess = userAccess;
        this.courseProgress = courseProgress;
        this.completed = completed;
        this.lessons = lessons;
        this.assessments = assessments;
        this.completedAssessments = new ArrayList<>(); 
        this.flashcard = flashcard; 
        this.lesson = lesson; // Default progress 
    }//done

    //done
    public Course(UUID id, double courseProgress) {
        this.id = id;
        this.courseProgress = courseProgress;
    }

	//done
    public double getCourseProgress() {
        return this.courseProgress;
    }

    //done
    // Get the name of the course
    public String getName() {
        return name;
    }

    //done
    // Set the name of the course
    public void setName(String name) {
        this.name = name;
    }

    //done
    // Get the description of the course
    public String getDescription() {
        return description;
    }

    //done
    // Set the description of the course
    public void setDescription(String description) {
        this.description = description;
    }

    //done
    // Get the progress of the course
    public void calculateProgress() {
        if (lesson.isCompleted() && flashcard.isCompleted()) {
            courseProgress = 100.0;
        } 
        else if(lesson.isCompleted() || flashcard.isCompleted()){
            courseProgress = 50.0;
        }
        else {
            courseProgress = 0.0;
        }
    }


    //done
    public void setCourseProgress(double courseProgress) {
        if (courseProgress >= 0.0 && courseProgress <= 100.0) {
            this.courseProgress = courseProgress;
        }
    }

    //done
    public boolean getUserAccess() {
        return userAccess;
    }
    
    //done
    public void setUserAccess(boolean userAccess) {
        this.userAccess = userAccess;
    }

    //done
    // Check if the course is completed
    public boolean isCompletedCourse() {
        return completed;
    }

    //done
    // Set the course as completed
    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            this.courseProgress = 100.0;  // If the course is marked completed, set progress to 100%
        }
    }

    //done
    // Get all the lessons in the course
    public ArrayList<Lesson> getAllLessons() {
        return lessons;
    }

    //done
    // Add a lesson to the course
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    //done
    // Get all the assessments in the course
    public ArrayList<Assessment> getAllAssessments() {
        return assessments;
    }

    //done
    // Add an assessment to the course
    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    //done
    // Get the completed assessments in the course
    public ArrayList<String> getCompletedAssessments() {
        ArrayList<String> completed = new ArrayList<>();
        for (Assessment assessment : assessments) {
            if (assessment.getResults() >= 70) {  
                completed.add(assessment.toString());  
            }
        }
        return this.completedAssessments;
    }

    //done
    public void setCompletedAssessments(ArrayList<String> completedAssessments) {
        this.completedAssessments = completedAssessments;
    }
    
    //done
    public void addKeyWord(String keyWord) {
        keyWords.add(keyWord);
    }

    //done
    public ArrayList<String> getKeyWords() {
        return keyWords;
    }

    //done
    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    //done
    public UUID getId() {
        return id;
    }

    //d
    public void setId(UUID id) {
        this.id = id;
    }

    //done
    //done
    // Mark the course as completed
    public void setCompletedCourse() {
        this.completed = true;
        this.courseProgress = 100.0;
    }

    //done
    //done
    // Check if the course is fully completed based on progress
    public boolean completedCourse() {
        return this.courseProgress == 100.0;
    }
}