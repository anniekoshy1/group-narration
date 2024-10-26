package com.narration;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    private static final String USERS_FILE = "speek/docs/JSON/User.json";
    private static final String COURSES_FILE = "speek/docs/JSON/Courses.json";
    private static final String LANGUAGES_FILE = "speek/docs/JSON/Languages.json";
    private static final String WORDS_FILE = "speek/docs/JSON/words.json";
    private static final String PHRASES_FILE = "speek/docs/JSON/phrases.json";


    //done3
    @SuppressWarnings("unchecked")

    public static void saveUsers(ArrayList<User> users) {
        JSONArray userList = new JSONArray();

        for (User user : users) {
            JSONObject userJSON = new JSONObject();
            userJSON.put("userId", user.getId().toString());
            userJSON.put("username", user.getUsername());
            userJSON.put("email", user.getEmail());
            userJSON.put("password", user.getPassword());

            // Convert courses to JSON
            JSONArray coursesJSON = new JSONArray();
            for (Course course : user.getCourses()) {
                JSONObject courseJSON = new JSONObject();
                courseJSON.put("courseID", course.getId().toString());
                // Add other course fields as needed
                coursesJSON.add(courseJSON);
            }
            userJSON.put("courses", coursesJSON);

            // Convert progress to JSON
            JSONObject progressJSON = new JSONObject();
            for (UUID courseId : user.getProgress().keySet()) {
                progressJSON.put(courseId.toString(), user.getCourseProgress(courseId));
            }
            userJSON.put("progress", progressJSON);

            // Convert completed courses to JSON
            JSONArray completedCoursesJSON = new JSONArray();
            for (UUID courseId : user.getCompletedCourses()) {
                completedCoursesJSON.add(courseId.toString());
            }
            userJSON.put("completedCourses", completedCoursesJSON);

            // Convert languages to JSON
            JSONArray languagesJSON = new JSONArray();
            for (Language lang : user.getLanguages()) {
                languagesJSON.add(lang.name());  // Assuming Language is an enum
            }
            userJSON.put("languages", languagesJSON);

            // Add current course and language info
            userJSON.put("currentCourseID", user.getCurrentCourse().toString());
            userJSON.put("currentLanguageID", user.getCurrentLanguage().toString());
            userJSON.put("currentLanguageName", user.getCurrentLanguageName());

            userList.add(userJSON);
        }

        try (FileWriter file = new FileWriter(DataConstants.USERS_FILE)) {
            file.write(userList.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Helper method to write to the file
private boolean writeToFile(String filePath, JSONArray jsonArray) {
    try (FileWriter file = new FileWriter(filePath)) {
        file.write(jsonArray.toJSONString());
        file.flush();
        return true;
    } catch (IOException e) {
        e.printStackTrace();  // Handle exceptions for file writing
        return false;
    }
}

    //done
    @SuppressWarnings("unchecked")
    public static void saveCourses(ArrayList<Course> courses) {
        JSONArray courseList = new JSONArray();

        for (Course course : courses) {
            JSONObject courseJSON = new JSONObject();
            courseJSON.put("courseID", course.getId());
            courseJSON.put("name", course.getName());
            courseJSON.put("description", course.getDescription());
            // Add additional fields as needed

            courseList.add(courseJSON);
        }

        try (FileWriter file = new FileWriter(DataConstants.COURSE_FILE)) {
            file.write(courseList.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //done
    @SuppressWarnings("unchecked")
    public boolean saveLanguages(ArrayList<Language> languages) {
        JSONArray languageArray = new JSONArray();

        for (Language language : languages) {
            JSONObject languageJson = new JSONObject();
            languageJson.put("languageID", language.getId().toString());
            languageJson.put("name", language.getName());
            languageArray.add(languageJson);
        }

        return writeToFile(LANGUAGES_FILE, languageArray);
    }

    //done
    public void saveUserProgress(User user) {
        ArrayList<User> users = new DataLoader().getUsers();
        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                existingUser.setProgress(user.getProgress());
                break;
            }
        }
        saveUsers(users);
    }

    //done
    public void saveAssessmentHistory(User user, Assessment assessment) {
        ArrayList<User> users = new DataLoader().getUsers();
        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                break;
            }
        }
        saveUsers(users);
    }

    // Save words to the JSON file
    @SuppressWarnings("unchecked")
    public void saveWords(WordsList wordsList) {
        JSONArray wordsArray = new JSONArray();
        for (Word word : wordsList.getAllWords()) {
            JSONObject wordObj = new JSONObject();
            wordObj.put("word", word.getWordText());
            wordObj.put("definition", word.getDefinition());
            wordObj.put("partOfSpeech", word.getPartOfSpeech());
            wordObj.put("language", word.getLanguage());
            wordsArray.add(wordObj);
        }

        try (FileWriter writer = new FileWriter(WORDS_FILE)) {
            writer.write(wordsArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save phrases to the JSON file
    @SuppressWarnings("unchecked")
    public void savePhrases(PhraseList phraseList) {
        JSONArray phrasesArray = new JSONArray();
        for (Phrase phrase : phraseList.getAllPhrases()) {
            JSONObject phraseObj = new JSONObject();
            phraseObj.put("phrase", phrase.getPhraseText());
            phraseObj.put("definition", phrase.getDefinition());
            phraseObj.put("partOfSpeech", phrase.getPartOfSpeech());
            phrasesArray.add(phraseObj);
        }

        try (FileWriter writer = new FileWriter(PHRASES_FILE)) {
            writer.write(phrasesArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}