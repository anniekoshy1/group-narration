package com.narration;
import java.util.List;

public class MultipleChoiceQuestion {

    private String question;  // The question being asked
    private List<String> choices;  // List of possible answer choices
    private String correctAnswer;  // The correct answer
    private String userAnswer;  // The answer provided by the user

    public MultipleChoiceQuestion(String question, List<String> choices, String correctAnswer) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.userAnswer = "";
    }

    // Get the question text
    public String getQuestion() {
        return question;
    }

    // Get the list of choices for the question
    public List<String> getChoices() {
        return choices;
    }

    // Submit the user's answer
    public void submitAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    // Check if the user's answer is correct
    public boolean checkAnswer() {
        return userAnswer.equalsIgnoreCase(correctAnswer);
    }

    // Get the correct answer for the question
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void reset() {
        this.userAnswer = "";
    }

    // Get the user's answer
    public String getUserAnswer() {
        return userAnswer;
    }
}