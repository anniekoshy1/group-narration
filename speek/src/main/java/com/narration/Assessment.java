package com.narration;

import java.util.List;
import java.util.UUID;

public class Assessment {

    public enum AssessmentType {
        MULTIPLE_CHOICE, TRUE_FALSE, OPEN_ENDED, MATCHING
    }

    private AssessmentType type;  
    private int userScore;  
    private List<Questions> questions;  
    private int attempts;  
    private UUID id;

    public Assessment(UUID id, AssessmentType type, List<Questions> questions) {
        this.id = id;
        this.type = type;
        this.userScore = 0;
        this.attempts = 0;
        this.questions = questions;
    }

    public int getResults() {
        return userScore;
    }

    public int calculateScore() {
        int correctAnswers = 0;
        for (Questions question : questions) {
            if (question.checkAnswers()) {
                correctAnswers++;
            }
        }
        this.userScore = (int) ((double) correctAnswers / questions.size() * 100); 
        return userScore;
    }

    public int calculateRating() {
        if (userScore >= 90) {
            return 5;
        } else if (userScore >= 80) {
            return 4;
        } else if (userScore >= 70) {
            return 3;
        } else if (userScore >= 60) {
            return 2;
        } else {
            return 1;
        }
    }

    public void retakeAssessment() {
        attempts++; 
        this.userScore = 0;
    }

    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setUUID(UUID id) {
        this.id = id;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public AssessmentType getType() {
        return type;
    }

    public void setType(AssessmentType type) {
        this.type = type;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "Assessment ID: " + id + ", Score: " + userScore;
    }
}