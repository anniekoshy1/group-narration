package com.narration;
import java.util.ArrayList;

public class CourseList {

    private static CourseList instance;  
    private ArrayList<Course> courses; 

    //done
    private CourseList() {
        courses = new ArrayList<>();

        Course startingOutCourse = new Course("Starting Out");
        courses.add(startingOutCourse);
    }

    //done
    public static CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    //done
    public Course addCourse(Course course) {
        courses.add(course);
        return course;
    }

    //done
    public boolean removeCourse(Course course) {
        if (course == null) {
            return false;
        }
        return courses.remove(course);
    }

    //done
    public ArrayList<Course> getCourses() {
        return courses;
    }

    //done
    public Course findByName(String name) {
    if (name == null) {
        throw new IllegalArgumentException("Name cannot be null");
    }
    
    for (Course course : courses) {
        if (course.getName().equalsIgnoreCase(name)) {
            return course;
        }
    }
    return null;  // Return null if the course is not found
}

    //done
    public Course getCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getId().toString().equals(courseId)) {
                return course;
            }
        }
        return null;  // Return null if the course is not found

    }
        //possibly done, question for portia
    public void save() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.saveCourses(courses);
    }
}