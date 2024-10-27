/**
 * This class loads data from JSON files, including users, courses, languages, words, and phrases, into the language learning system.
 */
package com.narration;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataLoader extends DataConstants {

    public static final String USERS_FILE = "speek/docs/JSON/User.json";
    public static final String COURSES_FILE = "speek/docs/JSON/Courses.json";
    public static final String LANGUAGES_FILE = "speek/docs/JSON/Languages.json";
    public static final String WORDS_FILE = "speek/docs/JSON/words.json";
    public static final String PHRASES_FILE = "speek/docs/JSON/phrases.json";

    /**
     * Loads users from the JSON file and constructs a list of User objects.
     * @return a list of User objects
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
    
        try (FileReader reader = new FileReader(DataConstants.USERS_FILE)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray userList = (JSONArray) jsonParser.parse(reader);
    
            for (Object obj : userList) {
                JSONObject userJSON = (JSONObject) obj;
                UUID id = UUID.fromString((String) userJSON.get("userId"));
                String username = (String) userJSON.get("username");
                String email = (String) userJSON.get("email");
                String password = (String) userJSON.get("password");

                ArrayList<Course> courses = new ArrayList<>();
                JSONArray coursesJSON = (JSONArray) userJSON.get("courses");
                for (Object courseObj : coursesJSON) {
                    JSONObject courseJSON = (JSONObject) courseObj;
                    UUID courseId = UUID.fromString((String) courseJSON.get("courseID"));
                    String courseName = (String) courseJSON.get("name");
                    String courseDescription = (String) courseJSON.get("description");
                    boolean userAccess = courseJSON.containsKey("userAccess");
                    double courseProgress = courseJSON.containsKey("courseProgress") ? ((Number) courseJSON.get("courseProgress")).doubleValue() : 0.0;
                    boolean completed = courseJSON.containsKey("completed");
                    ArrayList<Assessment> assessments = new ArrayList<>();
                    ArrayList<String> completedAssessments = new ArrayList<>();
                    ArrayList<Lesson> lessons = new ArrayList<>();
                    Lesson lesson = new Lesson(UUID.randomUUID(), "Default Lesson", 0.0, "Default Description");
                    FlashcardQuestion flashcard = new FlashcardQuestion("Default Question", "Default Answer");

                    Course course = new Course(courseId, courseName, courseDescription, userAccess, courseProgress, completed, lessons, assessments, completedAssessments, lesson, flashcard);
                    courses.add(course);
                }

                HashMap<UUID, Double> progress = new HashMap<>();
                JSONObject progressJSON = (JSONObject) userJSON.get("progress");
                for (Object key : progressJSON.keySet()) {
                    UUID courseId = UUID.fromString((String) key);
                    double progressValue = ((Number) progressJSON.get(key)).doubleValue();
                    progress.put(courseId, progressValue);
                }

                ArrayList<UUID> completedCourses = new ArrayList<>();
                JSONArray completedCoursesJSON = (JSONArray) userJSON.get("completedCourses");
                for (Object courseId : completedCoursesJSON) {
                    completedCourses.add(UUID.fromString((String) courseId));
                }

                UUID currentCourseID = UUID.fromString((String) userJSON.get("currentCourseID"));
                UUID currentLanguageID = UUID.fromString((String) userJSON.get("currentLanguageID"));
                String currentLanguageName = (String) userJSON.get("currentLanguageName");

                User user = new User(id, username, email, password, courses, progress, completedCourses, currentCourseID, new ArrayList<>(), currentLanguageID, currentLanguageName);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return users;
    }

    /**
     * Confirms if a user exists based on the username and password.
     * @param username the user's username
     * @param password the user's password
     * @return true if the user is confirmed, false otherwise
     */
    public boolean confirmUser(String username, String password) {
        ArrayList<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loads courses from the JSON file and returns a list of Course objects
     * @return a list of Course objects
     */
    public static ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try (FileReader reader = new FileReader(COURSES_FILE)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray courseList = (JSONArray) jsonParser.parse(reader);

            for (Object obj : courseList) {
                JSONObject courseJSON = (JSONObject) obj;
                UUID id = UUID.fromString((String) courseJSON.get("courseID"));
                String name = (String) courseJSON.get("name");
                String description = (String) courseJSON.get("description");
                boolean userAccess = (Boolean) courseJSON.get("userAccess");
                boolean completed = (Boolean) courseJSON.get("completed");
                double courseProgress = ((Number) courseJSON.get("courseProgress")).doubleValue();
                ArrayList<Assessment> assessments = new ArrayList<>();
                ArrayList<String> completedAssessments = new ArrayList<>();
                ArrayList<Lesson> lessons = new ArrayList<>();
                Lesson lesson = new Lesson(UUID.randomUUID(), "Default Lesson", 0.0, "Default Description");
                FlashcardQuestion flashcard = new FlashcardQuestion("Default Question", "Default Answer");
                
                Course course = new Course(id, name, description, userAccess, courseProgress, completed, lessons, assessments, completedAssessments, lesson, flashcard);
                courses.add(course);
                System.out.println("Loaded course: " + course.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total courses loaded: " + courses.size());
        return courses;
    }

    /**
     * Loads supported languages from the JSON file
     * @return a list of Language objects
     */
    public ArrayList<Language> getLanguages() {
        ArrayList<Language> languages = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(LANGUAGES_FILE)) {
            JSONArray languageArray = (JSONArray) jsonParser.parse(fileReader);

            for (Object languageObject : languageArray) {
                JSONObject languageJson = (JSONObject) languageObject;
                UUID languageID = UUID.fromString((String) languageJson.get("languageID"));
                String name = (String) languageJson.get("name");
                languages.add(new Language(languageID, name));
            }
            System.out.println("Languages loaded successfully.");
        } catch (IOException | ParseException e) {
            System.err.println("Error loading languages: " + e.getMessage());
        }

        return languages;
    }

    /**
     * Loads words from the JSON file into a WordsList object
     * @return a WordsList object containing all loaded words
     */
    public WordsList loadWords() {
        WordsList wordsList = new WordsList();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(WORDS_FILE)) {
            JSONArray wordsArray = (JSONArray) parser.parse(reader);
            for (Object obj : wordsArray) {
                JSONObject wordObj = (JSONObject) obj;
                String wordText = (String) wordObj.get("word");
                String definition = (String) wordObj.get("definition");
                String partOfSpeech = (String) wordObj.get("partOfSpeech");
                String language = (String) wordObj.get("language");

                Word word = new Word(wordText, definition, partOfSpeech, language);
                wordsList.addWord(word);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return wordsList;
    }

    /**
     * Loads phrases from the JSON file into a PhraseList object
     * @return a PhraseList object containing all loaded phrases
     */
    public PhraseList loadPhrases() {
        PhraseList phraseList = new PhraseList();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(PHRASES_FILE)) {
            JSONArray phrasesArray = (JSONArray) parser.parse(reader);
            for (Object obj : phrasesArray) {
                JSONObject phraseObj = (JSONObject) obj;
                String phraseText = (String) phraseObj.get("phrase");
                String definition = (String) phraseObj.get("definition");
                String partOfSpeech = (String) phraseObj.get("partOfSpeech");

                Phrase phrase = new Phrase(phraseText, definition, partOfSpeech);
                phraseList.addPhrase(phrase);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return phraseList;
    }


    /**
     * Saves the list of courses to storage.
     * @param courses the list of courses to be saved
     */
    public void saveCourses(ArrayList<Course> courses) {}

    /**
     * Saves the user progress to storage.
     * @param user the User whose progress is saved
     */
    public void saveUserProgress(User user) {}

    /**
     * Saves the assessment history for a user.
     * @param user the User for whom the history is saved
     * @param assessment the Assessment being saved
     */
    public void saveAssessmentHistory(User user, Assessment assessment) {}

    /**
     * Loads an assessment by its ID.
     * @param assessmentIDSTR the ID of the assessment as a String
     * @return the loaded Assessment object
     * @throws UnsupportedOperationException if the method is not implemented
     */
    public Assessment loadAssessmentById(String assessmentIDSTR) {
        throw new UnsupportedOperationException("Unimplemented method 'loadAssessmentById'");
    }
}