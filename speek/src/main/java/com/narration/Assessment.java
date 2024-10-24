package com.narration;
import java.util.List;
import java.util.UUID;

public class Assessment {
    //done
    public enum AssessmentType {
        MULTIPLE_CHOICE, TRUE_FALSE, OPEN_ENDED, MATCHING
    }
    //done
    private AssessmentType type;  // Type of the assessment
    private int userScore;  // User's score on the assessment
    private List<Questions> questions;  // List of questions in the assessment
    private int attempts;  // Number of attempts user has made
    private UUID id;

    //done
    public Assessment(UUID id, AssessmentType type, List<Questions> questions) {
        this.id = id;
        this.type = type;
        this.userScore = 0;
        this.attempts = 0;
        this.questions = questions;
    }
    //done
    public int getResults() {
        return userScore;
    }

    //done
    // Calculate the score for the assessment based on correct answers
    public int calculateScore() {
        int correctAnswers = 0;
        for (Questions question : questions) {
            if (question.checkAnswers()) {
                correctAnswers+=5;
            }
        }
        this.userScore = (int) ((double) correctAnswers / questions.size() * 100);  // Calculate score as a percentage
        return userScore;
    }

    //done
    // Calculate the user's rating based on the score (1 to 5 stars)
    public int calculateRating() {
        if (userScore >= 90) {
            return 5;  // 5-star rating
        } else if (userScore >= 80) {
            return 4;  // 4-star rating
        } else if (userScore >= 70) {
            return 3;  // 3-star rating
        } else if (userScore >= 60) {
            return 2;  // 2-star rating
        } else {
            return 1;  // 1-star rating
        }
    }
    
    //not sure
    public void retakeAssessment() {
        attempts++; 
        this.userScore = 0;  // Reset the score for the retake
    }

    //done
    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    //done
    public UUID getId() {
        return id;
    }
    
    //done
    public void setUUID(UUID id) {
        this.id = id;
    }

    //might have to die
    // Getter for the list of questions
    public List<Questions> getQuestions() {
        return questions;
    }

    //might have to get rid of
    // Setter for the list of questions
    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
    
    //done
    // Getter for the assessment type
    public AssessmentType getType() {
        return type;
    }

    //done
    // Setter for the assessment type
    public void setType(AssessmentType type) {
        this.type = type;
    }

    //need in UI
    // Getter for the number of attempts
    public int getAttempts() {
        return attempts;
    }

    //need in UI
    // Setter for the number of attempts
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String toString() {
    return "Assessment ID: " + ", Score: " + userScore;
}
}