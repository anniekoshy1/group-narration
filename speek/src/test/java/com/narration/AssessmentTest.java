package com.narration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Assessment class.
 */
public class AssessmentTest {

    private Assessment assessment;
    private List<Questions> sampleQuestions;

    @Before
    public void setUp() {
        sampleQuestions = new ArrayList<>();

        // Creating sample True/False question
        Questions tfQuestion = new Questions(
            "Is the earth round?",
            true, // Correct answer is true
            Difficulty.RUDIMENTARY
        );
        tfQuestion.submitAnswer("true"); // User's answer

        // Creating sample Multiple Choice question
        List<String> options = new ArrayList<>();
        options.add("Red");
        options.add("Blue");
        options.add("Green");
        options.add("Yellow");
        Questions mcQuestion = new Questions(
            "What color is the sky?",
            options,
            "Blue", // Correct option
            Difficulty.RUDIMENTARY
        );
        mcQuestion.submitAnswer("Blue"); // User's answer

        // Creating sample Open-Ended question
        Questions oeQuestion = new Questions(
            "What is the capital of France?",
            "Paris", // Correct answer
            Difficulty.INTERMEDIATE
        );
        oeQuestion.submitAnswer("Paris"); // User's answer

        // Adding questions to the list
        sampleQuestions.add(tfQuestion);
        sampleQuestions.add(mcQuestion);
        sampleQuestions.add(oeQuestion);

        assessment = new Assessment(
            UUID.randomUUID(),
            Assessment.AssessmentType.MULTIPLE_CHOICE, // Assuming the type
            sampleQuestions
        );
    }

    @Test
    public void calculateScore_CorrectlyCalculatesUserScore() {
        int expectedScore = 100; // All answers are correct
        int actualScore = assessment.calculateScore();
        assertEquals("User score should be correctly calculated based on correct answers", expectedScore, actualScore);
    }

    @Test
    public void hasPassed_ReturnsTrueWhenScoreIs70OrHigher() {
        assessment.calculateScore();
        assertTrue("User should have passed the assessment with a score of 100%", assessment.hasPassed());
    }

    @Test
    public void hasPassed_ReturnsFalseWhenScoreIsBelow70() {
        // Change user's answers to be incorrect
        for (Questions question : sampleQuestions) {
            question.resetAnswer();
            question.submitAnswer("wrong answer");
        }
        assessment.calculateScore();
        assertFalse("User should not have passed the assessment with a score below 70%", assessment.hasPassed());
    }

    @Test
    public void calculateRating_ReturnsFiveStarsForPerfectScore() {
        assessment.calculateScore();
        int rating = assessment.calculateRating();
        assertEquals("Rating should be 5 stars for a 100% score", 5, rating);
    }

    @Test
    public void calculateRating_ReturnsOneStarForLowScore() {
        // Set user's answers to be incorrect
        for (Questions question : sampleQuestions) {
            question.resetAnswer();
            question.submitAnswer("wrong answer");
        }
        assessment.calculateScore();
        int rating = assessment.calculateRating();
        assertEquals("Rating should be 1 star for a low score", 1, rating);
    }

    @Test
    public void calculateRating_ReturnsCorrectRatingForVariousScores() {
        // 80% score scenario
        sampleQuestions.clear();
        for (int i = 0; i < 5; i++) {
            Questions question = new Questions(
                "Sample question " + i,
                "Correct Answer",
                Difficulty.INTERMEDIATE
            );
            if (i < 4) {
                question.submitAnswer("Correct Answer"); // Correct answers
            } else {
                question.submitAnswer("Wrong Answer"); // Incorrect answer
            }
            sampleQuestions.add(question);
        }
        assessment.setQuestions(sampleQuestions);
        assessment.calculateScore();
        int rating = assessment.calculateRating();
        assertEquals("Rating should be 4 stars for an 80% score", 4, rating);

        // 60% score scenario
        sampleQuestions.clear();
        for (int i = 0; i < 5; i++) {
            Questions question = new Questions(
                "Sample question " + i,
                "Correct Answer",
                Difficulty.INTERMEDIATE
            );
            if (i < 3) {
                question.submitAnswer("Correct Answer"); // Correct answers
            } else {
                question.submitAnswer("Wrong Answer"); // Incorrect answers
            }
            sampleQuestions.add(question);
        }
        assessment.setQuestions(sampleQuestions);
        assessment.calculateScore();
        rating = assessment.calculateRating();
        assertEquals("Rating should be 3 stars for a 60% score", 3, rating);
    }

    @Test
    public void retakeAssessment_ResetsScoreAndIncrementsAttempts() {
        assessment.calculateScore();
        int initialAttempts = assessment.getAttempts();

        assessment.retakeAssessment();

        assertEquals("User score should be reset to 0 after retake", 0, assessment.getResults());
        assertEquals("Attempt count should increment by 1 after retake", initialAttempts + 1, assessment.getAttempts());
        assertFalse("passedAssessment should be reset to false after retake", assessment.hasPassed());
    }

    @Test
    public void retakeAssessment_CanBeCalledMultipleTimes() {
        assessment.calculateScore();
        int initialAttempts = assessment.getAttempts();

        assessment.retakeAssessment();
        assessment.retakeAssessment();
        assessment.retakeAssessment();

        assertEquals("Attempt count should increment correctly after multiple retakes", initialAttempts + 3, assessment.getAttempts());
    }

    @Test
    public void generateUUID_ReturnsValidUUID() {
        UUID newUUID = assessment.generateUUID();
        assertNotNull("Generated UUID should not be null", newUUID);
        assertNotEquals("Generated UUID should be unique", assessment.getId(), newUUID);
    }

    @Test
    public void calculateScore_HandlesEmptyQuestionList() {
        assessment.setQuestions(new ArrayList<>());
        int score = assessment.calculateScore();
        assertEquals("Score should be 0 when there are no questions", 0, score);
        assertFalse("User should not have passed when there are no questions", assessment.hasPassed());
    }

    @Test
    public void calculateScore_HandlesNullQuestionList() {
        assessment.setQuestions(null);
        int score = assessment.calculateScore();
        assertEquals("Score should be 0 when question list is null", 0, score);
        assertFalse("User should not have passed when question list is null", assessment.hasPassed());
    }

    @Test
    public void toString_ReturnsCorrectStringRepresentation() {
        assessment.calculateScore();
        String expectedString = "Assessment ID: " + assessment.getId() + ", Score: " + assessment.getResults();
        assertEquals("toString should return the correct string representation", expectedString, assessment.toString());
    }

    @Test
    public void constructor_InitializesFieldsCorrectly() {
        UUID testId = UUID.randomUUID();
        List<Questions> testQuestions = new ArrayList<>();
        Questions question = new Questions(
            "Test question?",
            "Test answer",
            Difficulty.RUDIMENTARY
        );
        testQuestions.add(question);

        Assessment testAssessment = new Assessment(testId, Assessment.AssessmentType.TRUE_FALSE, testQuestions);

        assertEquals("Assessment ID should be initialized correctly", testId, testAssessment.getId());
        assertEquals("Assessment type should be initialized correctly", Assessment.AssessmentType.TRUE_FALSE, testAssessment.getType());
        assertEquals("User score should be initialized to 0", 0, testAssessment.getResults());
        assertEquals("Attempt count should be initialized to 0", 0, testAssessment.getAttempts());
        assertFalse("passedAssessment should be initialized to false", testAssessment.hasPassed());
    }
}
