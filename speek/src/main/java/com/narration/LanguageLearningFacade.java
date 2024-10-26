package com.narration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LanguageLearningFacade {

    private final UserList userList;  // List of all users
    private final CourseList courseList;  // List of all available courses
    private final LanguageList languageList;  // List of all languages in the system
    private User user;  // The currently logged-in user
    private Language currentLanguage;  // The language the user is currently learning
    DataLoader dataLoader = new DataLoader();
    DataWriter dataWriter = new DataWriter();
    private List<User> users;
    private final ArrayList<Language> languages;  // Marked as final
    private final WordsList wordsList;  // Marked as final

    public LanguageLearningFacade() {
        languages = new ArrayList<>();
        userList = UserList.getInstance();
        courseList = CourseList.getInstance();
        languages.add(new Language("Spanish"));
        languageList = LanguageList.getInstance();
        this.dataWriter = new DataWriter();
        this.wordsList = dataLoader.loadWords();
        this.users = new DataLoader().getUsers();  // Assuming this returns an ArrayList<User>

        if (this.users == null) {
            this.users = new ArrayList<>();  // If no users exist, initialize an empty list
        }
    }

    public boolean login(String username, String password) {
        User foundUser = userList.getUser(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            this.user = foundUser;
            dataLoader.saveUserProgress(this.user);    
            return true;
        }
        return false;
    }

    public void logout() {
        this.user = null;
        this.currentLanguage = null;
    }

    public void startCourse(Course course) {
        if (user != null) {
            user.getCourses().add(course);
            course.setUserAccess(true);  // Grant access to the course
        }
    }

    public double trackCourseProgress(Course course) {
        if (user != null && course.getUserAccess()) {
            return course.getCourseProgress();
        }
        return 0.0;
    }

    public void startAssessment(Assessment assessment) {
        if (user != null) {
            assessment.retakeAssessment();  
        }
    }

    public List<Language> getAllLanguages() {
        return languages;
    }

    public void selectLanguage(String languageName) {
        Language language = languageList.findLanguageByName(languageName);
        if (language != null) {
            this.currentLanguage = language;
        }
    }

    public double trackLanguageProgress() {
        if (currentLanguage != null) {
            return currentLanguage.getLanguageProgress();
        }
        return 0.0;
    }

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

    public WordsList getWordsList() {
        return this.wordsList;
    }

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

    public ArrayList<Language> getAllLanguagesByKeyWord(String keyWord) {
        ArrayList<Language> matchingLanguages = new ArrayList<>();
        for (Language language : languageList.getLanguages()) {
            if (language.getKeyWords().contains(keyWord)) {
                matchingLanguages.add(language);
            }
        }
        return matchingLanguages;
    }

    public User getCurrentUser() {
        return user;
    }

    public void saveAndLogout() {
        if (user != null) {
            new DataWriter().saveUserProgress(user);
            logout();
        }
    }

    public void registerUser(String username, String email, String password) {
        UUID userId = UUID.randomUUID();
        User newUser = new User(userId, username, email, password, new ArrayList<>(), new HashMap<>(), new ArrayList<>(), null, new ArrayList<>(), null, "English");
        userList.addUser(newUser);
        dataWriter.saveUsers(new ArrayList<>(users));
    }

    public boolean hasCourseAccess(Course course) {
        if (user != null) {
            return course.getUserAccess();
        }
        return false;
    }

    public void loadAssessmentQuestions(UUID assessmentId) {
        dataLoader.loadAssessmentById(assessmentId.toString());
    }
}