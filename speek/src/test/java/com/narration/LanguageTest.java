package com.narration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LanguageTest {

    private Language language;
    private User user;
    private Course course1;
    private Course course2;
    private Assessment assessment1;
    private Assessment assessment2;

    @Before
    public void setUp() {
        // Create a mock user
        user = new User(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "testuser",
            "testuser@example.com",
            "password",
            new ArrayList<>(), // courses
            new HashMap<>(),   // progress
            new ArrayList<>(), // completedCourses
            null,              // currentCourseID
            new ArrayList<>(), // languages
            null,              // currentLanguageID
            "Spanish"          // currentLanguageName
        );

        // Initialize the Language object
        language = new Language(user, "Spanish");

        // Create mock courses
        course1 = new Course(
            UUID.fromString("10000000-0000-0000-0000-000000000001"),
            "Course 1",
            "Description 1",
            true,
            0.0,
            false,
            new ArrayList<>(), // lessons
            new ArrayList<>(), // assessments
            new ArrayList<>(), // completedAssessments
            new FlashcardQuestion("Question?", "Answer")
        );

        course2 = new Course(
            UUID.fromString("10000000-0000-0000-0000-000000000002"),
            "Course 2",
            "Description 2",
            true,
            0.0,
            false,
            new ArrayList<>(), // lessons
            new ArrayList<>(), // assessments
            new ArrayList<>(), // completedAssessments
            new FlashcardQuestion("Question?", "Answer")
        );

        // Create mock assessments using your Assessment class
        assessment1 = new Assessment(
            UUID.fromString("20000000-0000-0000-0000-000000000001"),
            Assessment.AssessmentType.MULTIPLE_CHOICE,
            new ArrayList<>()
        );

        assessment2 = new Assessment(
            UUID.fromString("20000000-0000-0000-0000-000000000002"),
            Assessment.AssessmentType.TRUE_FALSE,
            new ArrayList<>()
        );
    }

    @Test
    public void testGetName() {
        assertEquals("Spanish", language.getName());
    }

    @Test
    public void testGetAndSetUser() {
        assertEquals(user, language.getUser());

        // Create a new user
        User newUser = new User(
            UUID.fromString("00000000-0000-0000-0000-000000000002"),
            "newuser",
            "newuser@example.com",
            "newpassword",
            new ArrayList<>(),
            new HashMap<>(),
            new ArrayList<>(),
            null,
            new ArrayList<>(),
            null,
            "French"
        );

        language.setUser(newUser);
        assertEquals(newUser, language.getUser());
    }

    @Test
    public void testSetAndGetLanguageProgress() {
        language.setLanguageProgress(75.0);
        assertEquals(75.0, language.getLanguageProgress(), 0.0);
        assertEquals(37.5, language.getTotalPercentage(), 0.0); // Since coursePercentage is 0.0
    }

    @Test
    public void testSetAndGetCompletedCourses() {
        ArrayList<Course> completedCourses = new ArrayList<>();
        completedCourses.add(course1);
        completedCourses.add(course2);

        language.setCompletedCourses(completedCourses);
        assertEquals(2, language.getCompletedCourses().size());
        assertTrue(language.getCompletedCourses().contains(course1));
        assertTrue(language.getCompletedCourses().contains(course2));
        assertEquals(100.0, language.getCoursePercentage(), 0.0);
    }

    @Test
    public void testAddAndGetKeyWords() {
        language.addKeyWord("hola");
        language.addKeyWord("adiós");

        ArrayList<String> keyWords = language.getKeyWords();
        assertEquals(2, keyWords.size());
        assertTrue(keyWords.contains("hola"));
        assertTrue(keyWords.contains("adiós"));
    }

    @Test
    public void testAddAndGetCompletedAssessments() {
        language.addCompletedAssessment(assessment1);
        language.addCompletedAssessment(assessment2);

        ArrayList<Assessment> completedAssessments = language.getCompletedAssessments();
        assertEquals(2, completedAssessments.size());
        assertTrue(completedAssessments.contains(assessment1));
        assertTrue(completedAssessments.contains(assessment2));
    }

    @Test
    public void testGetId() {
        assertNotNull(language.getId());
    }

    @Test
    public void testUpdateTotalPercentage() {
        language.setLanguageProgress(80.0);
        ArrayList<Course> completedCourses = new ArrayList<>();
        completedCourses.add(course1);
        language.setCompletedCourses(completedCourses);

        // After setting completed courses, coursePercentage should be 100.0
        // Total percentage should be (100.0 + 80.0) / 2 = 90.0
        assertEquals(90.0, language.getTotalPercentage(), 0.0);
    }

    // Note: Tests that require access to private fields without getters are limited.
    // For example, we cannot test setCourseAccess() or takenStarterTest() fully
    // without appropriate getter methods.
}
