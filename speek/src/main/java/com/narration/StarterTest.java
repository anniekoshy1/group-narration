package com.narration;
import java.util.List;

public class StarterTest {

    private List<Questions> questions;  // List of questions in the starter test
    private int userScore;  // The user's score on the test
    private int totalQuestions;  // Total number of questions in the test

    public StarterTest(List<Questions> questions) {
        this.questions = questions;
        this.totalQuestions = questions.size();
        this.userScore = 0;
    }

    // Get the list of questions in the test
    public List<Questions> getQuestions() {
        return questions;
    }

    // Set the list of questions in the test
    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
        this.totalQuestions = questions.size();
    }

    // Submit the user's answers and calculate the score
    public void submitTest() {
        int correctAnswers = 0;
        for (Questions question : questions) {
            if (question.checkAnswers()) {
                correctAnswers++;
            }
        }
        userScore = correctAnswers;
    }

    // Get the user's score on the test
    public int getUserScore() {
        return userScore;
    }

    // Get the user's score as a percentage
    public double getScorePercentage() {
        if (totalQuestions == 0) {
            return 0.0;
        }
        return (double) userScore / totalQuestions * 100.0;
    }

    public void resetTest() {
        userScore = 0;
        for (Questions question : questions) {
            question.resetAnswer();
        }
    }

    public Difficulty determineLevel() {
        double percentage = getScorePercentage();
        if (percentage >= 90) {
            return Difficulty.ADVANCED;
        } else if (percentage >= 60) {
            return Difficulty.INTERMEDIATE;
        } else {
            return Difficulty.RUDIMENTARY;
        }
    }

    // Get the total number of questions in the test
    public int getTotalQuestions() {
        return totalQuestions;
    }

    // Add a question to the test
    public void addQuestion(Questions question) {
        questions.add(question);
        totalQuestions++;
    }

    // Remove a question from the test
    public void removeQuestion(Questions question) {
        questions.remove(question);
        totalQuestions--;
    }
}