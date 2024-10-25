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

public class DataLoader {

    private static final String USERS_FILE = "speek/docs/JSON/User.json";
    private static final String COURSES_FILE = "speek/docs/JSON/Courses.json";
    private static final String LANGUAGES_FILE = "speek/docs/JSON/Languages.json";
    private static final String WORDS_FILE = "speek/docs/JSON/words.json";
    private static final String PHRASES_FILE = "speek/docs/JSON/phrases.json";


    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(USERS_FILE)) {
            JSONArray userArray = (JSONArray) jsonParser.parse(fileReader);

            for (Object userObject : userArray) {
                JSONObject userJson = (JSONObject) userObject;

                UUID id = UUID.fromString((String) userJson.get("id"));
                String username = (String) userJson.get("username");
                String email = (String) userJson.get("email");
                String password = (String) userJson.get("password");

                JSONArray coursesJson = (JSONArray) userJson.get("courses");
                ArrayList<Course> courses = new ArrayList<>();
                for (Object courseObj : coursesJson) {
                    JSONObject courseJson = (JSONObject) courseObj;
                    UUID courseID = UUID.fromString((String) courseJson.get("courseID"));
                    double courseProgress = (Double) courseJson.get("courseProgress");
                    courses.add(new Course(courseID, courseProgress));
                }

                JSONObject progressJson = (JSONObject) userJson.get("progress");
                HashMap<UUID, Double> progress = new HashMap<>();
                for (Object key : progressJson.keySet()) {
                    UUID courseId = UUID.fromString((String) key);
                    double courseProgress = (Double) progressJson.get(key);
                    progress.put(courseId, courseProgress);
                }

                JSONArray completedCoursesJson = (JSONArray) userJson.get("completedCourses");
                ArrayList<UUID> completedCourses = new ArrayList<>();
                for (Object completedCourseId : completedCoursesJson) {
                    completedCourses.add(UUID.fromString((String) completedCourseId));
                }

                JSONObject currentCourseJson = (JSONObject) userJson.get("currentCourse");
                UUID currentCourseID = UUID.fromString((String) currentCourseJson.get("courseID"));

                JSONArray languagesJson = (JSONArray) userJson.get("languages");
                ArrayList<Language> languages = new ArrayList<>();
                for (Object languageObj : languagesJson) {
                    JSONObject languageJson = (JSONObject) languageObj;
                    UUID languageID = UUID.fromString((String) languageJson.get("languageID"));
                    String languageName = (String) languageJson.get("name");
                    languages.add(new Language(languageID, languageName));
                }

                JSONObject currentLanguageJson = (JSONObject) userJson.get("currentLanguage");
                UUID currentLanguageID = UUID.fromString((String) currentLanguageJson.get("languageID"));
                String currentLanguageName = (String) currentLanguageJson.get("name");

                User user = new User(id, username, email, password, courses, progress, completedCourses, currentCourseID, languages, currentLanguageID, currentLanguageName);
                users.add(user);
            }

            System.out.println("Users loaded successfully.");
        } catch (IOException | ParseException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    // Confirm a user by checking the username and password
    public boolean confirmUser(String username, String password) {
        ArrayList<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(COURSES_FILE)) {
            JSONArray courseArray = (JSONArray) jsonParser.parse(fileReader);

            for (Object courseObject : courseArray) {
                JSONObject courseJson = (JSONObject) courseObject;

                UUID courseID = UUID.fromString((String) courseJson.get("courseID"));
                String name = (String) courseJson.get("name");
                String description = (String) courseJson.get("description");
                boolean userAccess = (boolean) courseJson.get("userAccess");
                double courseProgress = ((Number) courseJson.get("courseProgress")).doubleValue();
                boolean completed = (boolean) courseJson.get("completed");

                JSONArray lessonsJson = (JSONArray) courseJson.get("lessons");
                ArrayList<Lesson> lessons = new ArrayList<>();
                for (Object lessonObject : lessonsJson) {
                    JSONObject lessonJson = (JSONObject) lessonObject;
                    UUID lessonID = UUID.fromString((String) lessonJson.get("lessonID"));
                    double lessonProgress = ((Number) lessonJson.get("lessonProgress")).doubleValue();
                    String lessonDescription = (String) lessonJson.get("description");
                    lessons.add(new Lesson(lessonID, lessonProgress, lessonDescription));
                }

                JSONArray assessmentsJson = (JSONArray) courseJson.get("assessments");
                ArrayList<Assessment> assessments = new ArrayList<>();
                for (Object assessmentObject : assessmentsJson) {
                    JSONObject assessmentJson = (JSONObject) assessmentObject;
                    UUID assessmentID = UUID.fromString((String) assessmentJson.get("assessmentID"));
                    String type = (String) assessmentJson.get("type");
                    int attempts = ((Number) assessmentJson.get("attempts")).intValue();
                    assessments.add(new Assessment(assessmentID, Assessment.AssessmentType.valueOf(type), null)); 
                }

                courses.add(new Course(courseID, name, description, userAccess, courseProgress, completed, lessons, assessments, new ArrayList<>()));
            }

            System.out.println("Courses loaded successfully.");
        } catch (IOException | ParseException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }

        return courses;
    }

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

    public void loadUserProgress(User user) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(USERS_FILE)) {
            JSONArray usersArray = (JSONArray) jsonParser.parse(fileReader);

            for (Object userObject : usersArray) {
                JSONObject userJson = (JSONObject) userObject;
                String username = (String) userJson.get("username");

                if (username.equals(user.getUsername())) {
                    JSONObject progressJson = (JSONObject) userJson.get("progress");
                    HashMap<UUID, Double> progressMap = new HashMap<>();

                    for (Object key : progressJson.keySet()) {
                        UUID courseId = UUID.fromString((String) key);
                        double progress = ((Number) progressJson.get(key)).doubleValue();
                        progressMap.put(courseId, progress);
                    }

                    user.setProgress(progressMap);
                    System.out.println("User progress loaded successfully.");
                    return;
                }
            }

        } catch (IOException | ParseException e) {
            System.err.println("Error loading user progress: " + e.getMessage());
        }
    }

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

    // Load phrases from the JSON file
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

    public void saveCourses(ArrayList<Course> courses) {
    }

    public void saveUserProgress(User user) {
    }

    public void saveAssessmentHistory(User user, Assessment assessment) {
    }

	public Assessment loadAssessmentById(String assessmentIDSTR) {
		
		throw new UnsupportedOperationException("Unimplemented method 'loadAssessmentById'");
	}
}