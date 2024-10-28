/**
 * This class loads data from JSON files, including users, courses, languages, words, and phrases, into the language learning system.
 * @author Four Musketeers
 */
package com.narration;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static JSONObject wordsData;
    public DataLoader() {
        loadWordsData();
    }

        
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
                UUID id = parseUUID((String) userJSON.get("userId"), "userID");
                String username = (String) userJSON.get("username");
                String email = (String) userJSON.get("email");
                String password = (String) userJSON.get("password");

                ArrayList<Course> courses = new ArrayList<>();
                JSONArray coursesJSON = (JSONArray) userJSON.get("courses");

                // Null check for coursesJSON before iterating
                if (coursesJSON != null) {
                    for (Object courseObj : coursesJSON) {
                        JSONObject courseJSON = (JSONObject) courseObj;
                        UUID courseId = parseUUID((String) courseJSON.get("courseID"), "courseID");
                        String courseName = (String) courseJSON.get("name");
                        String courseDescription = (String) courseJSON.get("description");
                        boolean userAccess = courseJSON.containsKey("userAccess");
                        double courseProgress = courseJSON.containsKey("courseProgress") ? ((Number) courseJSON.get("courseProgress")).doubleValue() : 0.0;
                        boolean completed = courseJSON.containsKey("completed");

                        // Assuming these fields have default values for Course
                        ArrayList<Assessment> assessments = new ArrayList<>();
                        ArrayList<String> completedAssessments = new ArrayList<>();

                        ArrayList<Lesson> lessons = new ArrayList<>();
                        JSONArray lessonsArray = (JSONArray) courseJSON.get("lessons");
                        if (lessonsArray != null) {
                            for (Object lessonObj : lessonsArray) {
                                JSONObject lessonJSON = (JSONObject) lessonObj;
                                String lessonName = (String) lessonJSON.get("lessonName");
                                UUID lessonId = UUID.fromString((String) lessonJSON.get("lessonID"));
                                String lessonDescription = (String) lessonJSON.get("description");
                                double lessonProgress = ((Number) lessonJSON.get("lessonProgress")).doubleValue();
                                String englishContent = (String) lessonJSON.get("englishContent");
                                String spanishContent = (String) lessonJSON.get("spanishContent");

                                Lesson lesson = new Lesson(lessonName, lessonId, lessonDescription, lessonProgress, englishContent, spanishContent);
                                lessons.add(lesson);
                            }
                        }
                        FlashcardQuestion flashcard = new FlashcardQuestion("Default Question", "Default Answer");

                        Course course = new Course(courseId, courseName, courseDescription, userAccess, courseProgress, completed, lessons, assessments, completedAssessments, flashcard);
                        courses.add(course);
                    }
                }

            // Similar null checks can be added for other fields if needed
            HashMap<UUID, Double> progress = new HashMap<>();
            JSONObject progressJSON = (JSONObject) userJSON.get("progress");
            if (progressJSON != null) {
                for (Object key : progressJSON.keySet()) {
                    UUID courseId = parseUUID((String) key, "progress");
                    double progressValue = ((Number) progressJSON.get(key)).doubleValue();
                    progress.put(courseId, progressValue);
                }
            }

            ArrayList<UUID> completedCourses = new ArrayList<>();
            JSONArray completedCoursesJSON = (JSONArray) userJSON.get("completedCourses");
            if (completedCoursesJSON != null) {
                for (Object courseId : completedCoursesJSON) {
                    completedCourses.add(parseUUID((String) courseId, "completedCourses"));
                }
            }

            UUID currentCourseID = parseUUID((String) userJSON.get("currentCourseID"), "currentCourseID");
            UUID currentLanguageID = parseUUID((String) userJSON.get("currentLanguageID"), "currentLanguageID");
            String currentLanguageName = (String) userJSON.get("currentLanguageName");

            User user = new User(id, username, email, password, courses, progress, completedCourses, currentCourseID, new ArrayList<>(), currentLanguageID, currentLanguageName);
            users.add(user);
            System.out.println("Loaded user: " + user.getUsername());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("Total users loaded: " + users.size());
    return users;
}


    private static UUID parseUUID(String uuidString, String fieldName) {
        if (uuidString == null || uuidString.trim().isEmpty()) {
            System.err.println("Warning: Missing or empty UUID for field: " + fieldName);
            return null;  // Return null or handle as needed
        }
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID format for field: " + fieldName + ", value: " + uuidString);
            return null; // Return null or handle as needed
        }
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

                //Load assessments for this courses
                ArrayList<Assessment> assessments = new ArrayList<>();
                ArrayList<String> completedAssessments = new ArrayList<>();

                //Load lessons for this course
                ArrayList<Lesson> lessons = new ArrayList<>();
                JSONArray lessonsArray = (JSONArray) courseJSON.get("lessons");
                for (Object lessonObj : lessonsArray) {
                    JSONObject lessonJSON = (JSONObject) lessonObj;
                    String lessonName = (String) lessonJSON.get("lessonName");
                    UUID lessonId = UUID.fromString((String) lessonJSON.get("lessonID"));
                    String lessonDescription = (String) lessonJSON.get("description");
                    double lessonProgress = ((Number) lessonJSON.get("lessonProgress")).doubleValue();
                    String englishContent = (String) lessonJSON.get("englishContent");
                    String spanishContent = (String) lessonJSON.get("spanishContent");

                Lesson lesson = new Lesson(lessonName, lessonId, lessonDescription, lessonProgress, englishContent, spanishContent);
                lessons.add(lesson);
            }
            FlashcardQuestion flashcard = new FlashcardQuestion("Default Question", "Default Answer");
                
                Course course = new Course(id, name, description, userAccess, courseProgress, completed, lessons, assessments, completedAssessments, flashcard);
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
    public static ArrayList<Language> getLanguages() {
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
    public static WordsList loadWords() {
        WordsList wordsList = new WordsList();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(WORDS_FILE)) {
            JSONArray wordsArray = (JSONArray) parser.parse(reader);
            for (Object obj : wordsArray) {
                JSONObject wordObject = (JSONObject) obj;
                String wordText = (String) wordObject.get("word");
                String definition = (String) wordObject.get("definition");
                String difficulty = (String) wordObject.get("difficulty");
                String translation = (String) wordObject.get("translation");

                Word word = new Word(wordText, definition, difficulty, translation);
                wordsList.addWord(word);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return wordsList;
    }

private static void loadWordsData() {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(WORDS_FILE)) {
        JSONArray wordsArray = (JSONArray) parser.parse(reader);  // Parse as JSONArray
        wordsData = new JSONObject();
        wordsData.put("words", wordsArray);  // Wrap it in a JSONObject if needed for other methods
    } catch (IOException | ParseException e) {
        e.printStackTrace();
    }
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

                Phrase phrase = new Phrase(phraseText, definition);
                phraseList.addPhrase(phrase);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return phraseList;
    }

    public static String getEnglishTranslation(String spanishWord) {
        if (wordsData == null){
            loadWordsData();
        }
        JSONArray wordsArray = (JSONArray) wordsData.get("words");
        for (Object wordObj : wordsArray) {
            JSONObject wordJson = (JSONObject) wordObj;
            if (wordJson.get("word").equals(spanishWord)) {
                return (String) wordJson.get("translation");
            }
        }
        return null; // Return null if not found
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
    public static Assessment loadAssessmentById(String assessmentIDSTR) {
        throw new UnsupportedOperationException("Unimplemented method 'loadAssessmentById'");
    }
    public static List<FlashcardQuestion> loadFlashcardsFromJson(String filePath) {
        List<FlashcardQuestion> flashcards = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray wordsArray = (JSONArray) parser.parse(reader);
            for (Object obj : wordsArray) {
                JSONObject wordObject = (JSONObject) obj;
                String frontInfo = (String) wordObject.get("word");  // Correct key
                String backAnswer = (String) wordObject.get("translation");  // Correct key
                flashcards.add(new FlashcardQuestion(frontInfo, backAnswer));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return flashcards;
    }

}