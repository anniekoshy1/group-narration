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
    public boolean saveUsers(ArrayList<User> newUsers) {
    JSONArray userArray = new JSONArray();

    // Read existing users from the file
    try (FileReader reader = new FileReader(USERS_FILE)) {
        JSONArray existingUsers = (JSONArray) new org.json.simple.parser.JSONParser().parse(reader);
        userArray.addAll(existingUsers);  // Append existing users
    } catch (Exception e) {
        e.printStackTrace();  // Handle exceptions for file reading/parsing
    }

    // Add new users to the userArray
    for (User user : newUsers) {
        JSONObject userJson = new JSONObject();
        userJson.put("id", user.getId().toString());
        userJson.put("username", user.getUsername());
        userJson.put("email", user.getEmail());
        userJson.put("password", user.getPassword());

        JSONArray coursesJson = new JSONArray();
        for (Course course : user.getCourses()) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("courseID", course.getId().toString());
            courseJson.put("courseProgress", course.getCourseProgress());
            coursesJson.add(courseJson);
        }
        userJson.put("courses", coursesJson);

        JSONObject progressJson = new JSONObject();
        HashMap<UUID, Double> progress = user.getProgress();  // Assuming you have this method
        for (UUID courseId : progress.keySet()) {
            progressJson.put(courseId.toString(), progress.get(courseId));
        }
        userJson.put("progress", progressJson);

        JSONArray completedCoursesJson = new JSONArray();
        for (UUID completedCourseId : user.getCompletedCourses()) {
            completedCoursesJson.add(completedCourseId.toString());
        }
        userJson.put("completedCourses", completedCoursesJson);

        JSONObject currentCourseJson = new JSONObject();
        currentCourseJson.put("courseID", user.getCurrentCourse().toString());
        userJson.put("currentCourse", currentCourseJson);

        JSONArray languagesJson = new JSONArray();
        for (Language language : user.getLanguages()) {
            JSONObject languageJson = new JSONObject();
            languageJson.put("languageID", language.getId().toString());
            languageJson.put("name", language.getName());
            languagesJson.add(languageJson);
        }
        userJson.put("languages", languagesJson);

        JSONObject currentLanguageJson = new JSONObject();
        currentLanguageJson.put("languageID", user.getCurrentLanguage().toString());
        currentLanguageJson.put("name", user.getCurrentLanguageName());
        userJson.put("currentLanguage", currentLanguageJson);

        userArray.add(userJson);
    }

    // Write the updated userArray back to the file
    return writeToFile(USERS_FILE, userArray);
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