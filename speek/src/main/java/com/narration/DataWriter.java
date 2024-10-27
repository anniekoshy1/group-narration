/**
 * Class responsible for writing data to JSON files, including user data, courses, languages, words, and phrases
 */
package com.narration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    private static final String USERS_FILE = "speek/docs/JSON/User.json";
    private static final String COURSES_FILE = "speek/docs/JSON/Courses.json";
    private static final String LANGUAGES_FILE = "speek/docs/JSON/Languages.json";
    private static final String WORDS_FILE = "speek/docs/JSON/words.json";
    private static final String PHRASES_FILE = "speek/docs/JSON/phrases.json";

    /**
     * Saves a list of users to the JSON file.
     * @param users the list of users to be saved
     */
    @SuppressWarnings("unchecked")
    public static void saveUsers(ArrayList<User> users) {
        JSONArray userList = new JSONArray();

        for (User user : users) {
            JSONObject userJSON = new JSONObject();
            userJSON.put("userId", user.getId().toString());
            userJSON.put("username", user.getUsername());
            userJSON.put("email", user.getEmail());
            userJSON.put("password", user.getPassword());

            JSONArray coursesJSON = new JSONArray();
            for (Course course : user.getCourses()) {
                JSONObject courseJSON = new JSONObject();
                courseJSON.put("courseID", course.getId().toString());
                coursesJSON.add(courseJSON);
            }
            userJSON.put("courses", coursesJSON);

            JSONObject progressJSON = new JSONObject();
            for (UUID courseId : user.getProgress().keySet()) {
                progressJSON.put(courseId.toString(), user.getCourseProgress(courseId));
            }
            userJSON.put("progress", progressJSON);

            JSONArray completedCoursesJSON = new JSONArray();
            for (UUID courseId : user.getCompletedCourses()) {
                completedCoursesJSON.add(courseId.toString());
            }
            userJSON.put("completedCourses", completedCoursesJSON);

            JSONArray languagesJSON = new JSONArray();
            for (Language lang : user.getLanguages()) {
                languagesJSON.add(lang.getName());
            }
            userJSON.put("languages", languagesJSON);

            userJSON.put("currentCourseID", user.getCurrentCourse().toString());
            userJSON.put("currentLanguageID", user.getCurrentLanguage().toString());
            userJSON.put("currentLanguageName", user.getCurrentLanguageName());

            userList.add(userJSON);
        }

        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(userList.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a JSON array to the specified file.
     * @param filePath the path of the file
     * @param jsonArray the JSON array to write
     * @return true if writing was successful, false otherwise
     */
    private boolean writeToFile(String filePath, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves a list of courses to the JSON file.
     * @param courses the list of courses to save
     */
    @SuppressWarnings("unchecked")
    public static void saveCourses(ArrayList<Course> courses) {
        JSONArray courseList = new JSONArray();

        for (Course course : courses) {
            JSONObject courseJSON = new JSONObject();
            courseJSON.put("courseID", course.getId());
            courseJSON.put("name", course.getName());
            courseJSON.put("description", course.getDescription());
            courseList.add(courseJSON);
        }

        try (FileWriter file = new FileWriter(COURSES_FILE)) {
            file.write(courseList.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a list of languages to the JSON file.
     * @param languages the list of languages to save
     * @return true if successful, false otherwise
     */
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

    /**
     * Saves the user progress to the JSON file by updating the user's data.
     * @param user the user whose progress is being saved
     */
    public void saveUserProgress(User user) {
        ArrayList<User> users = DataLoader.getUsers();
        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                existingUser.setProgress(user.getProgress());
                break;
            }
        }
        saveUsers(users);
    }

    /**
     * Saves the assessment history for a user.
     *
     * @param user the user whose assessment history is saved
     * @param assessment the assessment being saved
     */
    public void saveAssessmentHistory(User user, Assessment assessment) {
        ArrayList<User> users = DataLoader.getUsers();
        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                break;
            }
        }
        saveUsers(users);
    }

    /**
     * Saves words from the WordsList to the JSON file.
     * @param wordsList the WordsList containing words to save
     */
    @SuppressWarnings("unchecked")
    public void saveWords(WordsList wordsList) {
        JSONArray wordsArray = new JSONArray();
        for (Word word : wordsList.getAllWords()) {
            JSONObject wordObj = new JSONObject();
            wordObj.put("word", word.getWordText());
            wordObj.put("definition", word.getDefinition());
            wordsArray.add(wordObj);
        }

        try (FileWriter writer = new FileWriter(WORDS_FILE)) {
            writer.write(wordsArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves phrases from the PhraseList to the JSON file.
     * @param phraseList the PhraseList containing phrases to save
     */
    @SuppressWarnings("unchecked")
    public void savePhrases(PhraseList phraseList) {
        JSONArray phrasesArray = new JSONArray();
        for (Phrase phrase : phraseList.getAllPhrases()) {
            JSONObject phraseObj = new JSONObject();
            phraseObj.put("phrase", phrase.getPhraseText());
            phraseObj.put("definition", phrase.getDefinition());
            phrasesArray.add(phraseObj);
        }

        try (FileWriter writer = new FileWriter(PHRASES_FILE)) {
            writer.write(phrasesArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    };    
