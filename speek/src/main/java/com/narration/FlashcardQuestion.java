package com.narration;

public class FlashcardQuestion {

    private final String frontInfo;  // The front of the flashcard, now final
    private final String backAnswer;  // The back of the flashcard, now final
    private String userAnswer;  // The user's input or guess
    private boolean completed;   // Track if the flashcard has been completed
    private double flashcardProgress;  // Track the progress of the flashcard

    // Constructor to initialize flashcard information
    public FlashcardQuestion(String frontInfo, String backAnswer) {
        this.frontInfo = frontInfo;
        this.backAnswer = backAnswer;
        this.userAnswer = "";
        this.completed = false;  // Default to not completed
        this.flashcardProgress = 0.0;  // Default progress
    }

    // Method to flip the card and show the answer
    public void flipCard() {
        System.out.println("Flipped! The answer is: " + backAnswer);
    }

    // Show the definition or answer on the back of the card
    public String showDefinition() {
        return backAnswer;
    }

    // Submit an answer for the flashcard question
    public void submitAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid answer.");
        }
        this.userAnswer = userAnswer;
    }

    // Method to mark the flashcard as completed if the user types "done"
    public void markAsCompleted(String userInput) {
        if ("done".equalsIgnoreCase(userInput.trim())) {
            this.flashcardProgress = 100.0;  // Set progress to 100%
            this.completed = true;  // Mark as completed
        }
    }

    // Check if the user's answer is correct
    public boolean checkAnswer() {
        return userAnswer.equalsIgnoreCase(backAnswer);
    }

    // Show the correct answer
    public String showCorrectAnswer() {
        return backAnswer;
    }

    // Getters for the flashcard information
    public String getFrontInfo() {
        return frontInfo;
    }

    public String getBackAnswer() {
        return backAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    // Check if the flashcard is completed
    public boolean isCompleted() {
        return completed;
    }

    // Get flashcard progress
    public double getFlashcardProgress() {
        return flashcardProgress;
    }
}