package com.narration;

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

public class CourseListTest {

    private CourseList courseList;
    private Course sampleCourse;

    @Before
    public void setUp() {
        courseList = CourseList.getInstance();
        sampleCourse = new Course(UUID.randomUUID(), "Sample Course", "Sample Description", true, 0.0, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new FlashcardQuestion("Q", "A"));
    }

    @Test
    public void getInstance_ReturnsSameInstance() {
        CourseList anotherInstance = CourseList.getInstance();
        assertSame("getInstance should return the same instance of CourseList", courseList, anotherInstance);
    }

    @Test
    public void addCourse_AddsCourseToList() {
        int initialSize = courseList.getCourses().size();
        courseList.addCourse(sampleCourse);
        assertEquals("Course list size should increase by 1 after adding a course", initialSize + 1, courseList.getCourses().size());
        assertTrue("Course list should contain the added course", courseList.getCourses().contains(sampleCourse));
    }

    @Test
    public void removeCourse_RemovesCourseFromList() {
        courseList.addCourse(sampleCourse);
        int initialSize = courseList.getCourses().size();
        courseList.removeCourse(sampleCourse);
        assertEquals("Course list size should decrease by 1 after removing a course", initialSize - 1, courseList.getCourses().size());
        assertFalse("Course list should not contain the removed course", courseList.getCourses().contains(sampleCourse));
    }

    @Test
    public void removeCourse_ReturnsFalseForNonExistentCourse() {
        boolean result = courseList.removeCourse(new Course(UUID.randomUUID(), "Nonexistent Course", "Description", true, 0.0, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new FlashcardQuestion("Q", "A")));
        assertFalse("removeCourse should return false for a course not in the list", result);
    }

    @Test
    public void getCourses_ReturnsListOfCourses() {
        ArrayList<Course> courses = courseList.getCourses();
        assertNotNull("getCourses should not return null", courses);
    }

    @Test
    public void findByName_ReturnsCourseWhenNameMatches() {
        courseList.addCourse(sampleCourse);
        Course foundCourse = courseList.findByName("Sample Course");
        assertEquals("findByName should return the course with the matching name", sampleCourse, foundCourse);
    }

    @Test
    public void findByName_ReturnsNullWhenNameDoesNotMatch() {
        Course foundCourse = courseList.findByName("Nonexistent Course");
        assertNull("findByName should return null if no course matches the name", foundCourse);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByName_ThrowsExceptionWhenNameIsNull() {
        courseList.findByName(null);
    }

    @Test
    public void getCourseById_ReturnsCourseWhenIdMatches() {
        courseList.addCourse(sampleCourse);
        Course foundCourse = courseList.getCourseById(sampleCourse.getId().toString());
        assertEquals("getCourseById should return the course with the matching ID", sampleCourse, foundCourse);
    }

    @Test
    public void getCourseById_ReturnsNullWhenIdDoesNotMatch() {
        Course foundCourse = courseList.getCourseById(UUID.randomUUID().toString());
        assertNull("getCourseById should return null if no course matches the ID", foundCourse);
    }
}
