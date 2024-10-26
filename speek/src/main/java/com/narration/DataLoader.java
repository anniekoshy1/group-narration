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



public class DataLoader extends DataConstants{

    public static final String USERS_FILE = "speek/docs/JSON/User.json";
    public static final String COURSES_FILE = "speek/docs/JSON/Courses.json";
    public static final String LANGUAGES_FILE = "speek/docs/JSON/Languages.json";
    public static final String WORDS_FILE = "speek/docs/JSON/words.json";
    public static final String PHRASES_FILE = "speek/docs/JSON/phrases.json";


    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (FileReader reader = new FileReader(DataConstants.USERS_FILE)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray userList = (JSONArray) jsonParser.parse(reader);

            for (Object obj : userList) {
                JSONObject userJSON = (JSONObject) obj;

                // Extract user data
                UUID id = UUID.fromString((String) userJSON.get("userId"));
                String username = (String) userJSON.get("username");
                String email = (String) userJSON.get("email");
                String password = (String) userJSON.get("password");

                // Parse courses
                JSONArray coursesJSON = (JSONArray) userJSON.get("courses");
                ArrayList<Course> courses = new ArrayList<>();
                for (Object courseObj : coursesJSON) {
                    JSONObject courseJSON = (JSONObject) courseObj;
                    UUID courseId = UUID.fromString((String) courseJSON.get("courseID"));
                    String courseName = (String) courseJSON.get("name");
                    String courseDesc = (String) courseJSON.get("description"); 

                    courses.add(new Course(courseId, courseName, courseDesc));  // Adjust as needed
                }

                // Parse progress
                JSONObject progressJSON = (JSONObject) userJSON.get("progress");
                HashMap<UUID, Double> progress = new HashMap<>();
                for (Object key : progressJSON.keySet()) {
                    UUID courseId = UUID.fromString((String) key);
                    double courseProgress = ((Number) progressJSON.get(key)).doubleValue();
                    progress.put(courseId, courseProgress);
                }

                // Parse completed courses
                JSONArray completedCoursesJSON = (JSONArray) userJSON.get("completedCourses");
                ArrayList<UUID> completedCourses = new ArrayList<>();
                for (Object courseId : completedCoursesJSON) {
                    completedCourses.add(UUID.fromString((String) courseId));
                }

                // Parse languages and current language details
                JSONArray languagesJSON = (JSONArray) userJSON.get("languages");
                ArrayList<Language> languages = new ArrayList<>();
                for (Object lang : languagesJSON) {
                    languages.add(new Language((String) lang));  // Assuming enum Language
                }
                UUID currentCourseID = UUID.fromString((String) userJSON.get("currentCourseID"));
                UUID currentLanguageID = UUID.fromString((String) userJSON.get("currentLanguageID"));
                String currentLanguageName = (String) userJSON.get("currentLanguageName");

                // Instantiate User and add to list
                User user = new User(id, username, email, password, courses, progress, completedCourses, currentCourseID, languages, currentLanguageID, currentLanguageName);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                
                Course course = new Course(id, name, description, userAccess, courseProgress, completed, lessons, assessments, completedAssessments);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (FileReader reader = new FileReader(USER_USERNAME)) {
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);
            for (Object obj : usersJSON) {
                JSONObject userJSON = (JSONObject) obj;
                User user = parseUser(userJSON);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

        private static User parseUser(JSONObject userJSON) {
        String id = (String) userJSON.get(USER_ID);
        String username = (String) userJSON.get(USER_USERNAME);
        String email = (String) userJSON.get(USER_EMAIL);
        double progress = (double) userJSON.get(USER_PROGRESS);
        return new User(id, email, username, pro);
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