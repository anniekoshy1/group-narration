package com.narration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the CourseList class.
 */
public class CourseListTest {

    private CourseList courseList;
    private Course sampleCourse;

    @Before
    public void setUp() throws Exception {
        // Reset the singleton instance using reflection
        resetSingletonInstance();

        // Initialize the course list
        courseList = CourseList.getInstance();

        // Clear the courses list using reflection to avoid file I/O
        clearCoursesList();

        // Create a sample course
        sampleCourse = new Course(
                UUID.randomUUID(),
                "Sample Course",
                "A sample course for testing",
                true,
                0.0,
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new FlashcardQuestion("Question?", "Answer")
        );
    }

    @Test
    public void getInstance_ReturnsSingletonInstance() {
        // Act
        CourseList firstInstance = CourseList.getInstance();
        CourseList secondInstance = CourseList.getInstance();

        // Assert
        assertNotNull("getInstance should return a non-null instance", firstInstance);
        assertSame("getInstance should return the same instance on subsequent calls", firstInstance, secondInstance);
    }

    @Test
    public void addCourse_AddsCourseToList() {
        // Arrange
        int initialSize = courseList.getCourses().size();

        // Act
        Course addedCourse = courseList.addCourse(sampleCourse);

        // Assert
        assertEquals("Course list size should increase by 1 after adding a course", initialSize + 1, courseList.getCourses().size());
        assertTrue("Course list should contain the added course", courseList.getCourses().contains(sampleCourse));
        assertEquals("addCourse should return the added course", sampleCourse, addedCourse);
    }

    @Test
    public void removeCourse_RemovesCourseFromList() {
        // Arrange
        courseList.addCourse(sampleCourse);
        int initialSize = courseList.getCourses().size();

        // Act
        boolean result = courseList.removeCourse(sampleCourse);

        // Assert
        assertTrue("removeCourse should return true when course is successfully removed", result);
        assertEquals("Course list size should decrease by 1 after removing a course", initialSize - 1, courseList.getCourses().size());
        assertFalse("Course list should not contain the removed course", courseList.getCourses().contains(sampleCourse));
    }

    @Test
    public void removeCourse_ReturnsFalseWhenCourseIsNull() {
        // Act
        boolean result = courseList.removeCourse(null);

        // Assert
        assertFalse("removeCourse should return false when course is null", result);
    }

    @Test
    public void findByName_ReturnsCourseWhenExists() {
        // Arrange
        courseList.addCourse(sampleCourse);

        // Act
        Course foundCourse = courseList.findByName("Sample Course");

        // Assert
        assertNotNull("findByName should return the course when it exists", foundCourse);
        assertEquals("findByName should return the correct course", sampleCourse, foundCourse);
    }

    @Test
    public void findByName_ReturnsNullWhenCourseDoesNotExist() {
        // Act
        Course foundCourse = courseList.findByName("Nonexistent Course");

        // Assert
        assertNull("findByName should return null when course does not exist", foundCourse);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByName_ThrowsExceptionWhenNameIsNull() {
        // Act
        courseList.findByName(null);

        // Assert is handled by the expected exception
    }

    @Test
    public void getCourseById_ReturnsCourseWhenExists() {
        // Arrange
        courseList.addCourse(sampleCourse);
        String courseId = sampleCourse.getId().toString();

        // Act
        Course foundCourse = courseList.getCourseById(courseId);

        // Assert
        assertNotNull("getCourseById should return the course when it exists", foundCourse);
        assertEquals("getCourseById should return the correct course", sampleCourse, foundCourse);
    }

    @Test
    public void getCourseById_ReturnsNullWhenCourseDoesNotExist() {
        // Act
        Course foundCourse = courseList.getCourseById(UUID.randomUUID().toString());

        // Assert
        assertNull("getCourseById should return null when course does not exist", foundCourse);
    }

    // Helper method to reset the singleton instance using reflection
    private void resetSingletonInstance() throws Exception {
        Field instanceField = CourseList.class.getDeclaredField("courseList");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    // Helper method to clear the courses list using reflection
    private void clearCoursesList() throws Exception {
        Field coursesField = CourseList.class.getDeclaredField("courses");
        coursesField.setAccessible(true);
        ArrayList<Course> courses = (ArrayList<Course>) coursesField.get(courseList);
        courses.clear();
    }
}
