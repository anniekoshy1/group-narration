package com.narration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the DataWriter class.
 */
public class DataWriterTest {

    private String originalUsersFile;
    private String originalCoursesFile;
    private String originalLanguagesFile;
    private String originalWordsFile;
    private String originalPhrasesFile;

    private static final String TEST_USERS_FILE = "testData/User.json";
    private static final String TEST_COURSES_FILE = "testData/Courses.json";
    private static final String TEST_LANGUAGES_FILE = "testData/Languages.json";
    private static final String TEST_WORDS_FILE = "testData/words.json";
    private static final String TEST_PHRASES_FILE = "testData/phrases.json";

    @Before
    public void setUp() throws IOException {
        // Backup original file paths
        originalUsersFile = DataWriter.USERS_FILE;
        originalCoursesFile = DataWriter.COURSES_FILE;
        originalLanguagesFile = DataWriter.LANGUAGES_FILE;
        originalWordsFile = DataWriter.WORDS_FILE;
        originalPhrasesFile = DataWriter.PHRASES_FILE;

        // Update DataWriter paths to point to test files
        DataWriter.USERS_FILE = TEST_USERS_FILE;
        DataWriter.COURSES_FILE = TEST_COURSES_FILE;
        DataWriter.LANGUAGES_FILE = TEST_LANGUAGES_FILE;
        DataWriter.WORDS_FILE = TEST_WORDS_FILE;
        DataWriter.PHRASES_FILE = TEST_PHRASES_FILE;

        // Create empty test data files
        createEmptyTestFiles();
    }

    @After
    public void tearDown() {
        // Restore original file paths
        DataWriter.USERS_FILE = originalUsersFile;
        DataWriter.COURSES_FILE = originalCoursesFile;
        DataWriter.LANGUAGES_FILE = originalLanguagesFile;
        DataWriter.WORDS_FILE = originalWordsFile;
        DataWriter.PHRASES_FILE = originalPhrasesFile;

        // Optionally delete test data files after tests
        // For simplicity, we'll leave them in place
    }

    // Helper method to create empty test files
    private void createEmptyTestFiles() throws IOException {
        // Ensure the testData directory exists
        java.io.File dir = new java.io.File("testData");
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Create empty JSON files
        new FileWriter(TEST_USERS_FILE).close();
        new FileWriter(TEST_COURSES_FILE).close();
        new FileWriter(TEST_LANGUAGES_FILE).close();
        new FileWriter(TEST_WORDS_FILE).close();
        new FileWriter(TEST_PHRASES_FILE).close();
    }

    @Test
    public void testSaveUsers_WritesCorrectData() {
        // Arrange
        ArrayList<User> users = new ArrayList<>();

        // Create test users
        User user1 = new User(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "testuser1",
            "testuser1@example.com",
            "password1",
            new ArrayList<>(), // courses
            new HashMap<>(), // progress
            new ArrayList<>(), // completedCourses
            UUID.fromString("00000000-0000-0000-0000-000000000000"), // currentCourseID
            new ArrayList<>(), // languages
            UUID.fromString("00000000-0000-0000-0000-000000000000"), // currentLanguageID
            "Spanish"
        );

        User user2 = new User(
            UUID.fromString("00000000-0000-0000-0000-000000000002"),
            "testuser2",
            "testuser2@example.com",
            "password2",
            new ArrayList<>(), // courses
            new HashMap<>(), // progress
            new ArrayList<>(), // completedCourses
            UUID.fromString("00000000-0000-0000-0000-000000000000"), // currentCourseID
            new ArrayList<>(), // languages
            UUID.fromString("00000000-0000-0000-0000-000000000000"), // currentLanguageID
            "French"
        );

        users.add(user1);
        users.add(user2);

        // Act
        DataWriter.saveUsers(users);

        // Assert
        // Read the file and verify the data
        try (FileReader reader = new FileReader(TEST_USERS_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray usersArray = (JSONArray) parser.parse(reader);

            assertEquals("Number of users saved should be 2", 2, usersArray.size());

            JSONObject userJson1 = (JSONObject) usersArray.get(0);
            assertEquals("User ID should match", user1.getId().toString(), userJson1.get("userId"));
            assertEquals("Username should match", user1.getUsername(), userJson1.get("username"));
            assertEquals("Email should match", user1.getEmail(), userJson1.get("email"));
            assertEquals("Password should match", user1.getPassword(), userJson1.get("password"));

            JSONObject userJson2 = (JSONObject) usersArray.get(1);
            assertEquals("User ID should match", user2.getId().toString(), userJson2.get("userId"));
            assertEquals("Username should match", user2.getUsername(), userJson2.get("username"));
            assertEquals("Email should match", user2.getEmail(), userJson2.get("email"));
            assertEquals("Password should match", user2.getPassword(), userJson2.get("password"));
        } catch (IOException | ParseException e) {
            fail("Exception occurred while reading users file: " + e.getMessage());
        }
    }

    @Test
    public void testSaveCourses_WritesCorrectData() {
        // Arrange
        ArrayList<Course> courses = new ArrayList<>();

        // Create test courses
        Course course1 = new Course(
            UUID.fromString("10000000-0000-0000-0000-000000000001"),
            "Course 1",
            "Description 1",
            true, // userAccess
            0.0, // courseProgress
            false, // completed
            new ArrayList<>(), // lessons
            new ArrayList<>(), // assessments
            new ArrayList<>(), // completedAssessments
            new FlashcardQuestion("Question?", "Answer")
        );

        Course course2 = new Course(
            UUID.fromString("10000000-0000-0000-0000-000000000002"),
            "Course 2",
            "Description 2",
            true, // userAccess
            0.0, // courseProgress
            false, // completed
            new ArrayList<>(), // lessons
            new ArrayList<>(), // assessments
            new ArrayList<>(), // completedAssessments
            new FlashcardQuestion("Question?", "Answer")
        );

        courses.add(course1);
        courses.add(course2);

        // Act
        DataWriter.saveCourses(courses);

        // Assert
        // Read the file and verify the data
        try (FileReader reader = new FileReader(TEST_COURSES_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray coursesArray = (JSONArray) parser.parse(reader);

            assertEquals("Number of courses saved should be 2", 2, coursesArray.size());

            JSONObject courseJson1 = (JSONObject) coursesArray.get(0);
            assertEquals("Course ID should match", course1.getId().toString(), courseJson1.get("courseID").toString());
            assertEquals("Course name should match", course1.getName(), courseJson1.get("name"));
            assertEquals("Course description should match", course1.getDescription(), courseJson1.get("description"));

            JSONObject courseJson2 = (JSONObject) coursesArray.get(1);
            assertEquals("Course ID should match", course2.getId().toString(), courseJson2.get("courseID").toString());
            assertEquals("Course name should match", course2.getName(), courseJson2.get("name"));
            assertEquals("Course description should match", course2.getDescription(), courseJson2.get("description"));
        } catch (IOException | ParseException e) {
            fail("Exception occurred while reading courses file: " + e.getMessage());
        }
    }

    @Test
    public void testSaveLanguages_WritesCorrectData() {
        // Arrange
        ArrayList<Language> languages = new ArrayList<>();

        // Create test languages
        Language language1 = new Language(
            UUID.fromString("20000000-0000-0000-0000-000000000001"),
            "Language 1"
        );

        Language language2 = new Language(
            UUID.fromString("20000000-0000-0000-0000-000000000002"),
            "Language 2"
        );

        languages.add(language1);
        languages.add(language2);

        // Act
        DataWriter dataWriter = new DataWriter();
        dataWriter.saveLanguages(languages);

        // Assert
        // Read the file and verify the data
        try (FileReader reader = new FileReader(TEST_LANGUAGES_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray languagesArray = (JSONArray) parser.parse(reader);

            assertEquals("Number of languages saved should be 2", 2, languagesArray.size());

            JSONObject languageJson1 = (JSONObject) languagesArray.get(0);
            assertEquals("Language ID should match", language1.getId().toString(), languageJson1.get("languageID"));
            assertEquals("Language name should match", language1.getName(), languageJson1.get("name"));

            JSONObject languageJson2 = (JSONObject) languagesArray.get(1);
            assertEquals("Language ID should match", language2.getId().toString(), languageJson2.get("languageID"));
            assertEquals("Language name should match", language2.getName(), languageJson2.get("name"));
        } catch (IOException | ParseException e) {
            fail("Exception occurred while reading languages file: " + e.getMessage());
        }
    }

    @Test
    public void testSaveWords_WritesCorrectData() {
        // Arrange
        WordsList wordsList = new WordsList();

        // Create test words
        Word word1 = new Word("hola", "hello", "easy", "hello");
        Word word2 = new Word("adios", "goodbye", "easy", "goodbye");

        wordsList.addWord(word1);
        wordsList.addWord(word2);

        // Act
        DataWriter dataWriter = new DataWriter();
        dataWriter.saveWords(wordsList);

        // Assert
        // Read the file and verify the data
        try (FileReader reader = new FileReader(TEST_WORDS_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray wordsArray = (JSONArray) parser.parse(reader);

            assertEquals("Number of words saved should be 2", 2, wordsArray.size());

            JSONObject wordJson1 = (JSONObject) wordsArray.get(0);
            assertEquals("Word text should match", word1.getWordText(), wordJson1.get("word"));
            assertEquals("Definition should match", word1.getDefinition(), wordJson1.get("definition"));
            // Note: 'difficulty' and 'translation' are not saved according to the DataWriter code

            JSONObject wordJson2 = (JSONObject) wordsArray.get(1);
            assertEquals("Word text should match", word2.getWordText(), wordJson2.get("word"));
            assertEquals("Definition should match", word2.getDefinition(), wordJson2.get("definition"));
        } catch (IOException | ParseException e) {
            fail("Exception occurred while reading words file: " + e.getMessage());
        }
    }

    @Test
    public void testSavePhrases_WritesCorrectData() {
        // Arrange
        PhraseList phraseList = new PhraseList();

        // Create test phrases
        Phrase phrase1 = new Phrase("¿Cómo estás?", "How are you?");
        Phrase phrase2 = new Phrase("Buenos días", "Good morning");

        phraseList.addPhrase(phrase1);
        phraseList.addPhrase(phrase2);

        // Act
        DataWriter dataWriter = new DataWriter();
        dataWriter.savePhrases(phraseList);

        // Assert
        // Read the file and verify the data
        try (FileReader reader = new FileReader(TEST_PHRASES_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray phrasesArray = (JSONArray) parser.parse(reader);

            assertEquals("Number of phrases saved should be 2", 2, phrasesArray.size());

            JSONObject phraseJson1 = (JSONObject) phrasesArray.get(0);
            assertEquals("Phrase text should match", phrase1.getPhraseText(), phraseJson1.get("phrase"));
            assertEquals("Definition should match", phrase1.getDefinition(), phraseJson1.get("definition"));

            JSONObject phraseJson2 = (JSONObject) phrasesArray.get(1);
            assertEquals("Phrase text should match", phrase2.getPhraseText(), phraseJson2.get("phrase"));
            assertEquals("Definition should match", phrase2.getDefinition(), phraseJson2.get("definition"));
        } catch (IOException | ParseException e) {
            fail("Exception occurred while reading phrases file: " + e.getMessage());
        }
    }

    @Test
    public void testSaveUserProgress_UpdatesUserProgress() {
        // Arrange
        // Create initial users and save them
        ArrayList<User> users = new ArrayList<>();
        User user = new User(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "testuser",
            "testuser@example.com",
            "password",
            new ArrayList<>(), // courses
            new HashMap<>(), // progress
            new ArrayList<>(), // completedCourses
            UUID.fromString("00000000-0000-0000-0000-000000000000"), // currentCourseID
            new ArrayList<>(), // languages
            UUID.fromString("00000000-0000-0000-0000-000000000000"), // currentLanguageID
            "Spanish"
        );
        users.add(user);

        DataWriter.saveUsers(users);

        // Modify user's progress
        HashMap<UUID, Double> newProgress = new HashMap<>();
        newProgress.put(UUID.fromString("10000000-0000-0000-0000-000000000001"), 50.0);
        user.setProgress(newProgress);

        // Act
        DataWriter dataWriter = new DataWriter();
        dataWriter.saveUserProgress(user);

        // Assert
        // Read the users file and verify that the user's progress has been updated
        try (FileReader reader = new FileReader(TEST_USERS_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray usersArray = (JSONArray) parser.parse(reader);
            JSONObject userJson = (JSONObject) usersArray.get(0);

            JSONObject progressJson = (JSONObject) userJson.get("progress");
            assertNotNull("Progress should not be null", progressJson);
            assertEquals("User should have one progress entry", 1, progressJson.size());
            assertEquals("Progress value should match", 50.0, ((Number)progressJson.get("10000000-0000-0000-0000-000000000001")).doubleValue(), 0.0);
        } catch (IOException | ParseException e) {
            fail("Exception occurred while reading users file: " + e.getMessage());
        }
    }

    // Since saveAssessmentHistory doesn't have an implementation, we'll skip testing it
    // Alternatively, you can implement it and then write tests accordingly

}
