package com.narration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion question;
    private List<String> choices;

    @Before
    public void setUp() {
        // Initialize the list of choices and the MultipleChoiceQuestion object
        choices = Arrays.asList("Option A", "Option B", "Option C", "Option D");
        question = new MultipleChoiceQuestion(
            "What is the capital of France?",
            choices,
            "Option B"
        );
    }

    @Test
    public void testConstructor() {
        // Verify that all fields are correctly initialized
        assertEquals("What is the capital of France?", question.getQuestion());
        assertEquals(choices, question.getChoices());
        assertEquals("Option B", question.getCorrectAnswer());
        assertEquals("", question.getUserAnswer());
    }

    @Test
    public void testSubmitAnswer() {
        // Submit an answer and verify that it's recorded
        question.submitAnswer("Option B");
        assertEquals("Option B", question.getUserAnswer());
    }

    @Test
    public void testCheckAnswer_Correct() {
        // Submit the correct answer and check if it's correct
        question.submitAnswer("Option B");
        assertTrue(question.checkAnswer());
    }

    @Test
    public void testCheckAnswer_Incorrect() {
        // Submit an incorrect answer and check if it's detected
        question.submitAnswer("Option A");
        assertFalse(question.checkAnswer());
    }

    @Test
    public void testCheckAnswer_CaseInsensitive() {
        // Submit the correct answer in different case and verify it's still correct
        question.submitAnswer("option b");
        assertTrue(question.checkAnswer());
    }

    @Test
    public void testReset() {
        // Submit an answer, reset it, and verify that the userAnswer is empty
        question.submitAnswer("Option C");
        question.reset();
        assertEquals("", question.getUserAnswer());
    }

    @Test
    public void testGetChoices_Unmodifiable() {
        // Verify that the choices list cannot be modified externally
        List<String> retrievedChoices = question.getChoices();
        try {
            retrievedChoices.add("Option E");
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Test passes
        }
    }
}
