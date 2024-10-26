package com.narration;

public class TrueFalseQuestion extends Questions {

    private final boolean correctAnswer;  // The correct answer
    private boolean userAnswer;  // The user's submitted answer 

    public TrueFalseQuestion(String questionText, boolean correctAnswer, Difficulty difficulty) {
        super(questionText, correctAnswer, difficulty);  
        this.correctAnswer = correctAnswer;
        this.userAnswer = false;
    }

    public void submitAnswer(boolean userAnswer) {
        this.userAnswer = userAnswer;
        super.submitAnswer(userAnswer ? "True" : "False");  
    }

    @Override
    public boolean checkAnswers() {
        return this.userAnswer == this.correctAnswer;  
    }

    @Override
    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String getUserAnswer() {
        return userAnswer ? "True" : "False";  
    }

    @Override
    public void resetAnswer() {
        this.userAnswer = false;
        super.resetAnswer();  
    }

    @Override
    public String toString() {
        return "True/False Question: " + getQuestionText() + "\nCorrect Answer: " + (correctAnswer ? "True" : "False");
    }
}