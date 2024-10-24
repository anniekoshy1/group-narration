import java.util.UUID;

public class Questions {

    private UUID questionID;
    private String questionText;  // The text of the question
    private boolean correctAnswer;  // The correct answer for the question
    private String userAnswer;  // The answer provided by the user
    private Difficulty difficulty;  // The difficulty level of the question

    public Questions(String questionText, boolean correctAnswer, Difficulty difficulty) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.userAnswer = "";
        this.questionID = UUID.randomUUID();

    }

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

    // Check if the user's answer is correct
    public boolean checkAnswers() {
        return userAnswer.equalsIgnoreCase(Boolean.toString(correctAnswer));
    }

    // Get the difficulty level of the question
    public Difficulty getDifficulty() {
        return difficulty;
    }

    // Set the difficulty level of the question
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void resetAnswer() {
        this.userAnswer = "";
    }

    @Override
    public String toString() {
        return "Question: " + questionText + "\nDifficulty: " + difficulty + "\nCorrect Answer: " + correctAnswer;
    }
}