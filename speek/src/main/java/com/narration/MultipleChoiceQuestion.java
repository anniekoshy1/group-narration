package com.narration;
import java.util.List;

public class MultipleChoiceQuestion {

    private final String question;  // The question being asked
    private final List<String> choices;  // List of possible answer choices
    private final String correctAnswer;  // The correct answer
    private String userAnswer;  // The answer provided by the user

    public MultipleChoiceQuestion(String question, List<String> choices, String correctAnswer) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.userAnswer = "";
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void submitAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean checkAnswer() {
        return userAnswer.equalsIgnoreCase(correctAnswer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void reset() {
        this.userAnswer = "";
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}