public class FlashcardQuestion {

    private String frontInfo;  // The front of the flashcard 
    private String backAnswer;  // The back of the flashcard
    private String userAnswer;  // The user's input or guess

    //done
    public FlashcardQuestion(String frontInfo, String backAnswer) {
        this.frontInfo = frontInfo;
        this.backAnswer = backAnswer;
        this.userAnswer = "";
    }

    //done
    public void flipCard() {
        System.out.println("Flipped! The answer is: " + backAnswer);
    }

    //done
    // Show the definition or answer on the back of the card
    public String showDefinition() {
        return backAnswer;
    }

    //done
    // Submit an answer for the flashcard question
    public void submitAnswer(String userAnswer) {
    if (userAnswer == null || userAnswer.trim().isEmpty()) {
        throw new IllegalArgumentException("Please provide a valid answer.");
    }
    this.userAnswer = userAnswer;
}

    //done
    // Check if the user's answer is correct
    public boolean checkAnswer() {
        if (userAnswer == null) {
            return false;
        }
        return userAnswer.equalsIgnoreCase(backAnswer);
    }

    //done
    // Show the correct answer
    public String showCorrectAnswer() {
        return backAnswer;
    }

    //done
    // Getters for the flashcard information
    public String getFrontInfo() {
        return frontInfo;
    }

    //done
    public String getBackAnswer() {
        return backAnswer;
    }

    //done
    public String getUserAnswer() {
        return userAnswer;
    }
}