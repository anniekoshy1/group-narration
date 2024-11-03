package com.narration;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LessonTest {

    private Lesson lesson;

    @Before
    public void setUp() {
        // Initialize a Lesson object before each test
        lesson = new Lesson(
            "Introduction to Spanish",
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "An introductory lesson",
            0.0,
            "Hello, welcome to the Spanish course.",
            "Hola, bienvenido al curso de español."
        );
    }

    @Test
    public void testConstructorWithAllParameters() {
        // Verify that all fields are correctly initialized
        assertEquals("Introduction to Spanish", lesson.getLessonName());
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), lesson.getId());
        assertEquals("An introductory lesson", lesson.getDescription());
        assertEquals(0.0, lesson.getLessonProgress(), 0.0);
        assertEquals("Hello, welcome to the Spanish course.", lesson.getEnglishContent());
        assertEquals("Hola, bienvenido al curso de español.", lesson.getSpanishContent());
        assertFalse(lesson.isCompleted());
    }

    @Test
    public void testConstructorWithDefaultProgress() {
        // Create a Lesson using the constructor that initializes progress at 0
        Lesson lesson2 = new Lesson(
            "Basic Phrases",
            "Learn basic phrases",
            "Good morning, good night.",
            "Buenos días, buenas noches."
        );

        // Verify that fields are correctly initialized
        assertEquals("Basic Phrases", lesson2.getLessonName());
        assertNotNull(lesson2.getId());
        assertEquals("Learn basic phrases", lesson2.getDescription());
        assertEquals(0.0, lesson2.getLessonProgress(), 0.0);
        assertEquals("Good morning, good night.", lesson2.getEnglishContent());
        assertEquals("Buenos días, buenas noches.", lesson2.getSpanishContent());
        assertFalse(lesson2.isCompleted());
    }

    @Test
    public void testSetDescription() {
        // Update the description and verify the change
        lesson.setDescription("Updated description");
        assertEquals("Updated description", lesson.getDescription());
    }

    @Test
    public void testSetLessonProgress() {
        // Set progress to 50% and verify
        lesson.setLessonProgress(50.0);
        assertEquals(50.0, lesson.getLessonProgress(), 0.0);
        assertFalse(lesson.isCompleted());

        // Set progress to 100% and verify that the lesson is marked as completed
        lesson.setLessonProgress(100.0);
        assertEquals(100.0, lesson.getLessonProgress(), 0.0);
        assertTrue(lesson.isCompleted());
    }

    @Test
    public void testSetEnglishContent() {
        // Update the English content and verify the change
        lesson.setEnglishContent("New English content");
        assertEquals("New English content", lesson.getEnglishContent());
    }

    @Test
    public void testSetSpanishContent() {
        // Update the Spanish content and verify the change
        lesson.setSpanishContent("Nuevo contenido en español");
        assertEquals("Nuevo contenido en español", lesson.getSpanishContent());
    }

    @Test
    public void testMarkAsCompleted() {
        // Mark the lesson as completed and verify
        lesson.markAsCompleted();
        assertTrue(lesson.isCompleted());
        assertEquals(100.0, lesson.getLessonProgress(), 0.0);
    }
}
