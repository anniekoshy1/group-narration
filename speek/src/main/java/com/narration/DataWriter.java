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

    // Static method to save users to JSON file
    @SuppressWarnings("unchecked")
    public static boolean saveUsers() {
        ArrayList<User> newUsers = UserList.getInstance().getUsers(); // Access singleton
        JSONArray userArray = new JSONArray();

        // Read existing users from file
        try (FileReader reader = new FileReader(USERS_FILE)) {
            JSONArray existingUsers = (JSONArray) new org.json.simple.parser.JSONParser().parse(reader);
            userArray.addAll(existingUsers); // Append existing users
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add new users to JSON array
        for (User user : newUsers) {
            JSONObject userJson = createUserJson(user);
            userArray.add(userJson);
        }

        return writeToFile(USERS_FILE, userArray);
    }

    // Static helper method to create JSON object for a user
    @SuppressWarnings("unchecked")
    private static JSONObject createUserJson(User user) {
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
        HashMap<UUID, Double> progress = user.getProgress();
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

        return userJson;
    }

    // Static method to write JSON array to file
    private static boolean writeToFile(String filePath, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Static method to save courses
    @SuppressWarnings("unchecked")
    public static boolean saveCourses() {
        ArrayList<Course> courses = CourseList.getInstance().getCourses(); // Access singleton
        JSONArray courseArray = new JSONArray();

        for (Course course : courses) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("courseID", course.getId().toString());
            courseJson.put("name", course.getName());
            courseJson.put("description", course.getDescription());
            courseJson.put("userAccess", course.getUserAccess());
            courseJson.put("courseProgress", course.getCourseProgress());
            courseJson.put("completed", course.isCompletedCourse());

            JSONArray lessonsJson = new JSONArray();
            for (Lesson lesson : course.getAllLessons()) {
                JSONObject lessonJson = new JSONObject();
                lessonJson.put("lessonID", lesson.getId().toString());
                lessonJson.put("lessonProgress", lesson.getLessonProgress());
                lessonJson.put("description", lesson.getDescription());
                lessonsJson.add(lessonJson);
            }
            courseJson.put("lessons", lessonsJson);

            JSONArray assessmentsJson = new JSONArray();
            for (Assessment assessment : course.getAllAssessments()) {
                JSONObject assessmentJson = new JSONObject();
                assessmentJson.put("assessmentID", assessment.getId().toString());
                assessmentJson.put("type", assessment.getType().toString());
                assessmentJson.put("attempts", assessment.getAttempts());
                assessmentsJson.add(assessmentJson);
            }
            courseArray.add(courseJson);
        }

        return writeToFile(COURSES_FILE, courseArray);
    }

    // Static method to save languages
    @SuppressWarnings("unchecked")
    public static boolean saveLanguages() {
        ArrayList<Language> languages = LanguageList.getInstance().getLanguages(); // Access singleton
        JSONArray languageArray = new JSONArray();

        for (Language language : languages) {
            JSONObject languageJson = new JSONObject();
            languageJson.put("languageID", language.getId().toString());
            languageJson.put("name", language.getName());
            languageArray.add(languageJson);
        }

        return writeToFile(LANGUAGES_FILE, languageArray);
    }

    // Static method to save words
    @SuppressWarnings("unchecked")
    public static void saveWords() {
        WordsList wordsList = WordsList.getInstance(); // Access singleton
        JSONArray wordsArray = new JSONArray();

        for (Word word : wordsList.getAllWords()) {
            JSONObject wordObj = new JSONObject();
            wordObj.put("word", word.getWordText());
            wordObj.put("definition", word.getDefinition());
            wordObj.put("partOfSpeech", word.getPartOfSpeech());
            wordObj.put("language", word.getLanguage());
            wordsArray.add(wordObj);
        }

        writeToFile(WORDS_FILE, wordsArray);
    }

    // Static method to save phrases
    @SuppressWarnings("unchecked")
    public static void savePhrases() {
        PhraseList phraseList = PhraseList.getInstance(); // Access singleton
        JSONArray phrasesArray = new JSONArray();

        for (Phrase phrase : phraseList.getAllPhrases()) {
            JSONObject phraseObj = new JSONObject();
            phraseObj.put("phrase", phrase.getPhraseText());
            phraseObj.put("definition", phrase.getDefinition());
            phraseObj.put("partOfSpeech", phrase.getPartOfSpeech());
            phrasesArray.add(phraseObj);
        }

        writeToFile(PHRASES_FILE, phrasesArray);
    }
}
