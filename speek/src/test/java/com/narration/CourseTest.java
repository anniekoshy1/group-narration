package com.narration;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Course class.
 */
public class CourseTest {

    private Course course;
    private Lesson lesson;
    private FlashcardQuestion flashcard;
    private Assessment assessment;

    @Before
    public void setUp() {
        // Initialize a sample lesson
        lesson = new Lesson(
            "Sample Lesson",
            UUID.randomUUID(),
            "This is a sample lesson.",
            0.0,
            "English content",
            "Spanish content"
        );

        // Initialize a sample flashcard
        flashcard = new FlashcardQuestion(
            "What is the capital of France?",
            "Paris"
        );

        // Initialize sample questions for the assessment
        ArrayList<Questions> questions = new ArrayList<>();
        // Assuming Difficulty enum has EASY, MEDIUM, HARD
        questions.add(new Questions("Is the earth round?", true, Difficulty.RUDIMENTARY));
        questions.add(new Questions("What color is the sky?", "Blue", Difficulty.RUDIMENTARY));
        questions.add(new Questions("Solve for x: 2x + 3 = 7", "2", Difficulty.INTERMEDIATE));

        // Set user's answers to be correct
        questions.get(0).submitAnswer("true");
        questions.get(1).submitAnswer("Blue");
        questions.get(2).submitAnswer("2");

        // Initialize an assessment with these questions
        assessment = new Assessment(
            UUID.randomUUID(),
            Assessment.AssessmentType.MULTIPLE_CHOICE,
            questions
        );

        // Calculate the score to reflect the user's answers
        assessment.calculateScore();

        // Initialize the course with the sample data
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);

        ArrayList<Assessment> assessments = new ArrayList<>();
        assessments.add(assessment);

        ArrayList<String> completedAssessments = new ArrayList<>();

        course = new Course(
            UUID.randomUUID(),
            "Sample Course",
            "A course for testing",
            true,
            0.0,
            false,
            lessons,
            assessments,
            completedAssessments,
            flashcard
        );

        // Set the current lesson
        course.setCurrentLesson(lesson);
    }

    @Test
    public void calculateProgress_UpdatesCourseProgressTo100WhenLessonAndFlashcardCompleted() {
        // Arrange
        lesson.markAsCompleted();
        flashcard.setFlashcardProgress(100.0);

        // Act
        course.calculateProgress();

        // Assert
        assertEquals("Course progress should be 100% when lesson and flashcard are completed", 100.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void calculateProgress_UpdatesCourseProgressTo50WhenOnlyLessonCompleted() {
        // Arrange
        lesson.markAsCompleted();
        flashcard.setFlashcardProgress(0.0);

        // Act
        course.calculateProgress();

        // Assert
        assertEquals("Course progress should be 50% when only the lesson is completed", 50.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void calculateProgress_UpdatesCourseProgressTo50WhenOnlyFlashcardCompleted() {
        // Arrange
        lesson.setLessonProgress(0.0);
        flashcard.setFlashcardProgress(100.0);

        // Act
        course.calculateProgress();

        // Assert
        assertEquals("Course progress should be 50% when only the flashcard is completed", 50.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void calculateProgress_UpdatesCourseProgressTo50WhenNeitherCompleted() {
        // Arrange
        lesson.setLessonProgress(0.0);
        flashcard.setFlashcardProgress(0.0);

        // Act
        course.calculateProgress();

        // Assert
        assertEquals("Course progress should be 50% when neither lesson nor flashcard is completed (as per method logic)", 50.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void setCourseProgress_SetsProgressWithinValidRange() {
        // Act
        course.setCourseProgress(85.0);

        // Assert
        assertEquals("Course progress should be set to 85.0%", 85.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void setCourseProgress_DoesNotSetProgressOutsideValidRange() {
        // Act
        course.setCourseProgress(-10.0);

        // Assert
        assertNotEquals("Course progress should not be set to a negative value", -10.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void addLesson_AddsLessonToCourse() {
        // Arrange
        Lesson newLesson = new Lesson(
            "New Lesson",
            "New lesson description",
            "English content",
            "Spanish content"
        );
        int initialSize = course.getAllLessons().size();

        // Act
        course.addLesson(newLesson);

        // Assert
        assertEquals("Course should have one more lesson after adding", initialSize + 1, course.getAllLessons().size());
        assertTrue("Course lessons should contain the new lesson", course.getAllLessons().contains(newLesson));
    }

    @Test
    public void addAssessment_AddsAssessmentToCourse() {
        // Arrange
        ArrayList<Questions> newQuestions = new ArrayList<>();
        newQuestions.add(new Questions("What is 2 + 2?", "4", Difficulty.RUDIMENTARY));
        Assessment newAssessment = new Assessment(
            UUID.randomUUID(),
            Assessment.AssessmentType.MULTIPLE_CHOICE,
            newQuestions
        );
        int initialSize = course.getAllAssessments().size();

        // Act
        course.addAssessment(newAssessment);

        // Assert
        assertEquals("Course should have one more assessment after adding", initialSize + 1, course.getAllAssessments().size());
        assertTrue("Course assessments should contain the new assessment", course.getAllAssessments().contains(newAssessment));
    }

    @Test
    public void getCompletedAssessments_ReturnsAssessmentsWithScore70OrAbove() {
        // Arrange
        // Existing assessment has a score calculated during setup (should be 100%)
        Assessment failingAssessment = new Assessment(
            UUID.randomUUID(),
            Assessment.AssessmentType.MULTIPLE_CHOICE,
            new ArrayList<>()
        );
        failingAssessment.calculateScore(); // Score will be 0% due to empty question list
        course.addAssessment(failingAssessment);

        // Act
        ArrayList<String> completedAssessments = course.getCompletedAssessments();

        // Assert
        assertEquals("There should be one completed assessment with a score >= 70%", 1, completedAssessments.size());
        assertTrue("Completed assessments should contain the assessment with a passing score", completedAssessments.contains(assessment.toString()));
    }

    @Test
    public void setCompletedCourse_SetsCourseToCompletedAndProgressTo100() {
        // Act
        course.setCompletedCourse();

        // Assert
        assertTrue("Course should be marked as completed", course.isCompletedCourse());
        assertEquals("Course progress should be set to 100% when completed", 100.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void completedCourse_ReturnsTrueWhenProgressIs100() {
        // Arrange
        course.setCourseProgress(100.0);

        // Act
        boolean isCompleted = course.completedCourse();

        // Assert
        assertTrue("completedCourse should return true when progress is 100%", isCompleted);
    }

    @Test
    public void completedCourse_ReturnsFalseWhenProgressIsNot100() {
        // Arrange
        course.setCourseProgress(75.0);

        // Act
        boolean isCompleted = course.completedCourse();

        // Assert
        assertFalse("completedCourse should return false when progress is not 100%", isCompleted);
    }

    @Test
    public void addKeyWord_AddsKeyWordToCourse() {
        // Arrange
        String keyWord = "Language";
        if (course.getKeyWords() == null) {
            course.setKeyWords(new ArrayList<>()); 
        }
        int initialSize = course.getKeyWords().size();

        // Act
        course.addKeyWord(keyWord);

        // Assert
        assertEquals("Course should have one more keyword after adding", initialSize + 1, course.getKeyWords().size());
        assertTrue("Course keywords should contain the new keyword", course.getKeyWords().contains(keyWord));
    }

    @Test
    public void generateUUID_ReturnsValidUUID() {
        // Act
        UUID newUUID = course.generateUUID();

        // Assert
        assertNotNull("Generated UUID should not be null", newUUID);
        assertNotEquals("Generated UUID should be unique", course.getId(), newUUID);
    }

    @Test
    public void setCompleted_SetsCompletedStatusAndUpdatesProgress() {
        // Act
        course.setCompleted(true);

        // Assert
        assertTrue("Course should be marked as completed", course.isCompletedCourse());
        assertEquals("Course progress should be set to 100% when completed", 100.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void setCompleted_DoesNotUpdateProgressWhenSetToIncomplete() {
        // Arrange
        course.setCourseProgress(100.0);

        // Act
        course.setCompleted(false);

        // Assert
        assertFalse("Course should be marked as not completed", course.isCompletedCourse());
        assertEquals("Course progress should remain unchanged when set to incomplete", 100.0, course.getCourseProgress(), 0.0);
    }
}
