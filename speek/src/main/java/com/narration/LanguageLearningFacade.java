/**
 * Acts as the main interface for the language learning system, providing functionality for user management, course handling, language selection, and progress tracking.
 * @author Four Musketeers
 */
package com.narration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LanguageLearningFacade {

    private final UserList userList;
    private final CourseList courseList;
    private final LanguageList languageList;
    private User user;
    private Language currentLanguage;
    DataLoader dataLoader = new DataLoader();
    DataWriter dataWriter = new DataWriter();
    private List<User> users;
    private final ArrayList<Language> languages;
    private final WordsList wordsList;

    /**
     * Initializes the facade, setting up user, course, and language lists,
     * and loads data such as words from storage.
     */
    public LanguageLearningFacade() {
        languages = new ArrayList<>();
        userList = UserList.getInstance();
        courseList = CourseList.getInstance();
        languages.add(new Language("Spanish"));
        languageList = LanguageList.getInstance();
        this.dataWriter = new DataWriter();
        this.wordsList = new DataLoader().loadWords();
        this.users = DataLoader.getUsers();

        if (this.users == null) {
            this.users = new ArrayList<>();
        }
    }

    /**
     * Logs in a user based on provided credentials.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if login is successful, false otherwise
     */
    public boolean login(String username, String password) {
        User foundUser = UserList.getInstance().getUser(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            this.user = foundUser;
            dataLoader.saveUserProgress(this.user);
            return true;
        }
        return false;
    }

    /**
     * Logs out the currently logged-in user and clears the current language.
     */
    public void logout() {
        this.user = null;
        this.currentLanguage = null;
    }

    /**
     * Starts a specified course for the current user.
     *
     * @param course the course to start
     */
    public void startCourse(Course course) {
        if (user != null) {
            user.getCourses().add(course);
            course.setUserAccess(true);
        }
    }

    /**
     * Tracks the progress of a specific course for the current user.
     *
     * @param course the course to track progress for
     * @return the course progress percentage, or 0 if not accessible
     */
    public double trackCourseProgress(Course course) {
        if (user != null && course.getUserAccess()) {
            return course.getCourseProgress();
        }
        return 0.0;
    }

    /**
     * Starts an assessment by resetting attempts for the current user.
     *
     * @param assessment the assessment to start
     */
    public void startAssessment(Assessment assessment) {
        if (user != null) {
            assessment.retakeAssessment();
        }
    }

    /**
     * Retrieves all available languages in the system.
     *
     * @return a list of available languages
     */
    public List<Language> getAllLanguages() {
        return languages;
    }

    /**
     * Selects a language for the current user based on language name.
     *
     * @param languageName the name of the language to select
     */
    public void selectLanguage(String languageName) {
        Language language = languageList.findLanguageByName(languageName);
        if (language != null) {
            this.currentLanguage = language;
        }
    }

    /**
     * Tracks the learning progress in the currently selected language.
     *
     * @return the language progress percentage, or 0 if no language is selected
     */
    public double trackLanguageProgress() {
        if (currentLanguage != null) {
            return currentLanguage.getLanguageProgress();
        }
        return 0.0;
    }

    /**
     * Retrieves all available courses in the system.
     *
     * @return a list of all available courses
     */
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = DataLoader.loadCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found in DataLoader.");
        } else {
            for (Course course : courses) {
                System.out.println("Available course: " + course.getName());
            }
        }
        return courses;
    }

    /**
     * Gets the list of words available in the system.
     *
     * @return the WordsList containing all words
     */
    public WordsList getWordsList() {
        return this.wordsList;
    }

    /**
     * Tracks the overall progress across all courses for the current user.
     *
     * @return the overall progress percentage
     */
    public double trackOverallProgress() {
        if (user != null) {
            double totalProgress = 0.0;
            for (Course course : user.getCourses()) {
                totalProgress += course.getCourseProgress();
            }
            return totalProgress / user.getCourses().size();
        }
        return 0.0;
    }

    /**
     * Gets the currently logged-in user.
     *
     * @return the current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return user;
    }

    /**
     * Saves the current user's progress and logs them out.
     */
    public void saveAndLogout() {
        if (user != null) {
            new DataWriter().saveUserProgress(user);
            logout();
        }
    }

    /**
     * Registers a new user with the provided credentials.
     *
     * @param username the username of the new user
     * @param email    the email address of the new user
     * @param password the password for the new user
     */
    public void registerUser(String username, String email, String password) {
        UUID userId = UUID.randomUUID();
        User newUser = new User(userId, username, email, password, new ArrayList<>(), new HashMap<>(), new ArrayList<>(), null, new ArrayList<>(), null, "English");
        userList.addUser(newUser);
        dataWriter.saveUsers(new ArrayList<>(users));
    }

    /**
     * Checks if the current user has access to a specified course.
     *
     * @param course the course to check access for
     * @return true if the user has access, false otherwise
     */
    public boolean hasCourseAccess(Course course) {
        if (user != null) {
            return course.getUserAccess();
        }
        return false;
    }

    /**
     * Loads assessment questions for a given assessment ID.
     *
     * @param assessmentId the UUID of the assessment
     */
    public void loadAssessmentQuestions(UUID assessmentId) {
        dataLoader.loadAssessmentById(assessmentId.toString());
    }
}