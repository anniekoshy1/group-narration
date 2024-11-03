package com.narration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class FlashcardQuestionTest {

    private FlashcardQuestion flashcard;

    @Before
    public void setUp() {
        flashcard = new FlashcardQuestion("¿Cuál es la capital de España?", "Madrid");
    }

    @Test
    public void showDefinition_ReturnsBackAnswer() {
        assertEquals("Madrid", flashcard.showDefinition());
    }

    @Test
    public void submitAnswer_SetsUserAnswerWhenValidAnswerProvided() {
        flashcard.submitAnswer("Madrid");
        assertEquals("Madrid", flashcard.getUserAnswer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitAnswer_ThrowsExceptionWhenAnswerIsNull() {
        flashcard.submitAnswer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitAnswer_ThrowsExceptionWhenAnswerIsEmpty() {
        flashcard.submitAnswer(" ");
    }

    @Test
    public void checkAnswer_ReturnsTrueWhenAnswerIsCorrect() {
        flashcard.submitAnswer("Madrid");
        assertTrue(flashcard.checkAnswer());
    }

    @Test
    public void checkAnswer_ReturnsFalseWhenAnswerIsIncorrect() {
        flashcard.submitAnswer("Barcelona");
        assertFalse(flashcard.checkAnswer());
    }

    @Test
    public void markAsCompleted_SetsCompletedAndProgressTo100WhenInputIsDone() {
        flashcard.markAsCompleted("done");
        assertTrue(flashcard.isCompleted());
        assertEquals(100.0, flashcard.getFlashcardProgress(), 0.01);
    }

    @Test
    public void markAsCompleted_DoesNotSetCompletedWhenInputIsNotDone() {
        flashcard.markAsCompleted("not done");
        assertFalse(flashcard.isCompleted());
        assertEquals(0.0, flashcard.getFlashcardProgress(), 0.01);
    }

    @Test
    public void showCorrectAnswer_ReturnsBackAnswer() {
        assertEquals("Madrid", flashcard.showCorrectAnswer());
    }
}