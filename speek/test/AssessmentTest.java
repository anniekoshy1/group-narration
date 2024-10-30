package com.narration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AssessmentTest {
    private Assessment assessment;
    private List<Questions> questions;

    @BeforeEach
    public void setUp() {
        questions = Arrays.asList(
                new Questions(true),
                new Questions(false), 
                new Questions(true)
        );
        assessment = new Assessment(UUID.randomUUID(), Assessment.AssessmentType.MULTIPLE_CHOICE, questions);
    }

    @Test
    public void testInitialization() {
        assertEquals(0, assessment.getResults());
        assertEquals(0, assessment.getAttempts());
        assertFalse(assessment.hasPassed());
    }

    @Test
    public void testCalculateScore_PassingScore() {
        int score = assessment.calculateScore();
        assertEquals(67, score); 
        assertFalse(assessment.hasPassed());
    }

    @Test
    public void testCalculateScore_FullScore() {
        questions.forEach(q -> q.setAnswerCorrect(true));
        int score = assessment.calculateScore();
        assertEquals(100, score);
        assertTrue(assessment.hasPassed());
    }

    @Test
    public void testCalculateRating() {
        assessment.calculateScore();
        assertEquals(4, assessment.calculateRating()); 
    }

    @Test
    public void testRetakeAssessment() {
        assessment.calculateScore();
        assessment.retakeAssessment();
        assertEquals(0, assessment.getResults());
        assertEquals(1, assessment.getAttempts());
        assertFalse(assessment.hasPassed());
    }

    @Test
    public void testUUIDMethods() {
        UUID newId = UUID.randomUUID();
        assessment.setUUID(newId);
        assertEquals(newId, assessment.getId());
    }
}