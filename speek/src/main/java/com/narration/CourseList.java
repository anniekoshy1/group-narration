/**
 * Manages the list of available courses in the language learning system.
 * This class follows the singleton pattern to ensure a single instance of the course list.
 */
package com.narration;

import java.util.ArrayList;

public class CourseList {

    private static CourseList courseList;  
    private final ArrayList<Course> courses;

    /**
     * initialize the list of courses using data from DataLoader
     */
    private CourseList() {
        courses = DataLoader.loadCourses();
    }

    /**
     * Returns the singleton instance of the CourseList
     *
     * @return the single instance of CourseList
     */
    public static CourseList getInstance() {
        if (courseList == null){
            courseList = new CourseList();
        }
        return courseList;
    }

    /**
     * Adds a course to the list and saves the updated list to storage.
     *
     * @param course the course to be added
     * @return the added course
     */
    public Course addCourse(Course course) {
        courses.add(course);
        saveCourses();
        return course;
    }

    /**
     * Removes a course from the list.
     *
     * @param course the course to be removed
     * @return true if the course was successfully removed, false otherwise
     */
    public boolean removeCourse(Course course) {
        if (course == null) {
            return false;
        }
        return courses.remove(course);
    }

    /**
     * Returns the list of all courses.
     *
     * @return the list of courses
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Finds and returns a course by its name, ignoring case.
     *
     * @param name the name of the course to search for
     * @return the course if found, otherwise null
     * @throws IllegalArgumentException if the name is null
     */
    public Course findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    
        for (Course course : courses) {
            if (course.getName().equalsIgnoreCase(name)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Retrieves a course by its unique identifier
     *
     * @param courseId the ID of the course as a string
     * @return the course if found, otherwise null
     */
    public Course getCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getId().toString().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Saves the current list of courses to storage.
     */
    public void saveCourses() {
        DataWriter.saveCourses(courses);
    }
}