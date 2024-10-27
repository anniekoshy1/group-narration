package com.narration;

import java.util.List;

public class Questions {

    private String questionText;  // The text of the question
    private boolean correctAnswer;  // The correct answer for the question (for true/false)
    private String userAnswer;  // The answer provided by the user
    private Difficulty difficulty;  // The difficulty level of the question
    private List<String> options;  // The options for multiple-choice questions
    private String correctOption;
    private String correctOpenEndedAnswer;

    // Constructor for True/False questions
    public Questions(String questionText, boolean correctAnswer, Difficulty difficulty) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.userAnswer = "";
        this.options = null; // No options for true/false questions
        this.correctOpenEndedAnswer = null;
    }

    // Constructor for Multiple Choice questions
    public Questions(String questionText, List<String> options, String correctOption, Difficulty difficulty) {
        this.questionText = questionText;
        this.difficulty = difficulty;
        this.userAnswer = "";
        this.options = null;
        this.correctOption = correctOption;
        this.correctOpenEndedAnswer = null;
    }

    // Constructor for Open-Ended questions
    public Questions(String questionText, String userAnswer, Difficulty difficulty) {
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.difficulty = difficulty;
        this.options = null; // No options for open-ended questions
        this.correctOption = null;
    }

    // Getters and setters

    // Get the text of the question
    public String getQuestionText() {
        return questionText;
    }

    // Set the text of the question
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    // Get the correct answer for the question
    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    // Set the correct answer for the question
    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Get the user's answer for the question
    public String getUserAnswer() {
        return userAnswer;
    }

    // Submit the user's answer for the question
    public void submitAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean checkAnswers() {
        if (options != null) {
            // Multiple-choice question
            return userAnswer.equals(correctOption);
        } else if (correctOpenEndedAnswer != null) {
            // Open-ended question, exact match (case-insensitive)
            return userAnswer.equalsIgnoreCase(correctOpenEndedAnswer);
        } else {
            // True/False question
            return Boolean.parseBoolean(userAnswer) == correctAnswer;
        }
    }


    // Get the difficulty level of the question
    public Difficulty getDifficulty() {
        return difficulty;
    }

    // Set the difficulty level of the question
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // Getter for options
    public List<String> getOptions() {
        return options;
    }

    // Setter for options
    public void setOptions(List<String> options) {
        this.options = options;
    }

    // Reset the user's answer for the question
    public void resetAnswer() {
        this.userAnswer = "";
    }

    // Returns a string representation of the question
    @Override
    public String toString() {
        return "Question: " + questionText + 
            "\nOptions: " + options + 
            "\nDifficulty: " + difficulty + 
            "\nCorrect Answer: " + correctAnswer;
    }
}
