package com.narration;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

    private static final String USERS_FILE = "speek/docs/JSON/User.json";
    private static final String COURSES_FILE = "speek/docs/JSON/Courses.json";
    private static final String LANGUAGES_FILE = "speek/docs/JSON/Languages.json";
    private static final String WORDS_FILE = "speek/docs/JSON/words.json";
    private static final String PHRASES_FILE = "speek/docs/JSON/phrases.json";


    //done3
    public boolean saveUsers(ArrayList<User> users) {
        JSONArray userArray = new JSONArray();

        for (User user : users) {
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
            HashMap<UUID, Double> progress = user.getProgress();  // Parameterized HashMap
            for (UUID courseId : user.getProgress().keySet()) {
                progressJson.put(courseId.toString(), user.getProgress().get(courseId));
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

        return writeToFile(USERS_FILE, userArray);
    }

    //done
    public boolean saveCourses(ArrayList<Course> courses) {
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
            courseJson.put("assessments", assessmentsJson);

            courseArray.add(courseJson);
        }

        return writeToFile(COURSES_FILE, courseArray);
    }

    //done
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

    //done
    private boolean writeToFile(String filePath, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    // Save words to the JSON file
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