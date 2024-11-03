package com.narration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the DataLoader class.
 */
public class DataLoaderTest {

    private static String originalUsersFile;
    private static String originalCoursesFile;
    private static String originalLanguagesFile;
    private static String originalWordsFile;
    private static String originalPhrasesFile;

    private static final String TEST_USERS_FILE = "testData/User.json";
    private static final String TEST_COURSES_FILE = "testData/Courses.json";
    private static final String TEST_LANGUAGES_FILE = "testData/Languages.json";
    private static final String TEST_WORDS_FILE = "testData/words.json";
    private static final String TEST_PHRASES_FILE = "testData/phrases.json";

    @Before
    public void setUp() throws IOException {
        // Backup original file paths
        originalUsersFile = DataLoader.USERS_FILE;
        originalCoursesFile = DataLoader.COURSES_FILE;
        originalLanguagesFile = DataLoader.LANGUAGES_FILE;
        originalWordsFile = DataLoader.WORDS_FILE;
        originalPhrasesFile = DataLoader.PHRASES_FILE;

        // Create test data files
        createTestUsersFile();
        createTestCoursesFile();
        createTestLanguagesFile();
        createTestWordsFile();
        createTestPhrasesFile();

        // Update DataLoader paths to point to test files
        DataLoader.USERS_FILE = TEST_USERS_FILE;
        DataLoader.COURSES_FILE = TEST_COURSES_FILE;
        DataLoader.LANGUAGES_FILE = TEST_LANGUAGES_FILE;
        DataLoader.WORDS_FILE = TEST_WORDS_FILE;
        DataLoader.PHRASES_FILE = TEST_PHRASES_FILE;
    }

    @After
    public void tearDown() {
        // Restore original file paths
        DataLoader.USERS_FILE = originalUsersFile;
        DataLoader.COURSES_FILE = originalCoursesFile;
        DataLoader.LANGUAGES_FILE = originalLanguagesFile;
        DataLoader.WORDS_FILE = originalWordsFile;
        DataLoader.PHRASES_FILE = originalPhrasesFile;

        // Optionally delete test data files after tests
        // For simplicity, we'll leave them in place
    }

    @Test
    public void getUsers_ReturnsCorrectNumberOfUsers() {
        // Act
        ArrayList<User> users = DataLoader.getUsers();

        // Assert
        assertEquals("getUsers should return 2 users", 2, users.size());
    }

    @Test
    public void getUsers_ReturnsCorrectUserData() {
        // Act
        ArrayList<User> users = DataLoader.getUsers();
        User user = users.get(0);

        // Assert
        assertEquals("User ID should match", "00000000-0000-0000-0000-000000000001", user.getId().toString());
        assertEquals("Username should match", "testuser1", user.getUsername());
        assertEquals("Email should match", "testuser1@example.com", user.getEmail());
        assertEquals("Password should match", "password1", user.getPassword());
    }

    @Test
    public void loadCourses_ReturnsCorrectNumberOfCourses() {
        // Act
        ArrayList<Course> courses = DataLoader.loadCourses();

        // Assert
        assertEquals("loadCourses should return 2 courses", 2, courses.size());
    }

    @Test
    public void loadCourses_ReturnsCorrectCourseData() {
        // Act
        ArrayList<Course> courses = DataLoader.loadCourses();
        Course course = courses.get(0);

        // Assert
        assertEquals("Course ID should match", "10000000-0000-0000-0000-000000000001", course.getId().toString());
        assertEquals("Course name should match", "Spanish Basics", course.getName());
        assertEquals("Course description should match", "An introductory Spanish course", course.getDescription());
        assertTrue("User access should be true", course.getUserAccess());
        assertFalse("Course should not be completed", course.isCompletedCourse());
        assertEquals("Course progress should be 0.0", 0.0, course.getCourseProgress(), 0.0);
    }

    @Test
    public void getLanguages_ReturnsCorrectNumberOfLanguages() {
        // Act
        ArrayList<Language> languages = DataLoader.getLanguages();

        // Assert
        assertEquals("getLanguages should return 2 languages", 2, languages.size());
    }

    @Test
    public void getLanguages_ReturnsCorrectLanguageData() {
        // Act
        ArrayList<Language> languages = DataLoader.getLanguages();
        Language language = languages.get(0);

        // Assert
        assertEquals("Language ID should match", "20000000-0000-0000-0000-000000000001", language.getId().toString());
        assertEquals("Language name should match", "Spanish", language.getName());
    }

    @Test
    public void loadWords_ReturnsCorrectNumberOfWords() {
        // Act
        WordsList wordsList = DataLoader.loadWords();

        // Assert
        assertEquals("loadWords should return 2 words", 2, wordsList.getAllWords().size());
    }

    @Test
    public void loadWords_ReturnsCorrectWordData() {
        // Act
        WordsList wordsList = DataLoader.loadWords();
        Word word = wordsList.getAllWords().get(0);

        // Assert
        assertEquals("Word text should match", "hola", word.getWordText());
        assertEquals("Definition should match", "hello", word.getDefinition());
        assertEquals("Difficulty should match", "easy", word.getDifficulty());
        assertEquals("Translation should match", "hello", word.getTranslation());
    }

    @Test
    public void loadPhrases_ReturnsCorrectNumberOfPhrases() {
        // Act
        PhraseList phraseList = new DataLoader().loadPhrases();

        // Assert
        assertEquals("loadPhrases should return 2 phrases", 2, phraseList.getAllPhrases().size());
    }

    @Test
    public void loadPhrases_ReturnsCorrectPhraseData() {
        // Act
        PhraseList phraseList = new DataLoader().loadPhrases();
        Phrase phrase = phraseList.getAllPhrases().get(0);

        // Assert
        assertEquals("Phrase text should match", "¿Cómo estás?", phrase.getPhraseText());
        assertEquals("Definition should match", "How are you?", phrase.getDefinition());
    }

    @Test
    public void getEnglishTranslation_ReturnsCorrectTranslation() {
        // Arrange
        String spanishWord = "hola";
        String expectedTranslation = "hello";

        // Act
        String translation = DataLoader.getEnglishTranslation(spanishWord);

        // Assert
        assertEquals("getEnglishTranslation should return the correct translation", expectedTranslation, translation);
    }

    @Test
    public void getEnglishTranslation_ReturnsNullWhenWordNotFound() {
        // Arrange
        String spanishWord = "adios";

        // Act
        String translation = DataLoader.getEnglishTranslation(spanishWord);

        // Assert
        assertNull("getEnglishTranslation should return null when word is not found", translation);
    }

    // Helper methods to create test data files
    @SuppressWarnings("unchecked")
    private void createTestUsersFile() throws IOException {
        JSONArray usersArray = new JSONArray();

        JSONObject user1 = new JSONObject();
        user1.put("userId", "00000000-0000-0000-0000-000000000001");
        user1.put("username", "testuser1");
        user1.put("email", "testuser1@example.com");
        user1.put("password", "password1");
        user1.put("courses", new JSONArray());
        user1.put("progress", new JSONObject());
        user1.put("completedCourses", new JSONArray());
        user1.put("languages", new JSONArray());
        user1.put("currentCourseID", "00000000-0000-0000-0000-000000000000");
        user1.put("currentLanguageID", "00000000-0000-0000-0000-000000000000");
        user1.put("currentLanguageName", "Spanish");

        JSONObject user2 = new JSONObject();
        user2.put("userId", "00000000-0000-0000-0000-000000000002");
        user2.put("username", "testuser2");
        user2.put("email", "testuser2@example.com");
        user2.put("password", "password2");
        user2.put("courses", new JSONArray());
        user2.put("progress", new JSONObject());
        user2.put("completedCourses", new JSONArray());
        user2.put("languages", new JSONArray());
        user2.put("currentCourseID", "00000000-0000-0000-0000-000000000000");
        user2.put("currentLanguageID", "00000000-0000-0000-0000-000000000000");
        user2.put("currentLanguageName", "French");

        usersArray.add(user1);
        usersArray.add(user2);

        writeJSONToFile(TEST_USERS_FILE, usersArray);
    }
    @SuppressWarnings("unchecked")
    private void createTestCoursesFile() throws IOException {
        JSONArray coursesArray = new JSONArray();

        JSONObject course1 = new JSONObject();
        course1.put("courseID", "10000000-0000-0000-0000-000000000001");
        course1.put("name", "Spanish Basics");
        course1.put("description", "An introductory Spanish course");
        course1.put("userAccess", true);
        course1.put("completed", false);
        course1.put("courseProgress", 0.0);
        course1.put("lessons", new JSONArray());
        // Add other fields as necessary

        JSONObject course2 = new JSONObject();
        course2.put("courseID", "10000000-0000-0000-0000-000000000002");
        course2.put("name", "Advanced Spanish");
        course2.put("description", "An advanced Spanish course");
        course2.put("userAccess", false);
        course2.put("completed", false);
        course2.put("courseProgress", 0.0);
        course2.put("lessons", new JSONArray());
        // Add other fields as necessary

        coursesArray.add(course1);
        coursesArray.add(course2);

        writeJSONToFile(TEST_COURSES_FILE, coursesArray);
    }
    @SuppressWarnings("unchecked")
    private void createTestLanguagesFile() throws IOException {
        JSONArray languagesArray = new JSONArray();

        JSONObject language1 = new JSONObject();
        language1.put("languageID", "20000000-0000-0000-0000-000000000001");
        language1.put("name", "Spanish");

        JSONObject language2 = new JSONObject();
        language2.put("languageID", "20000000-0000-0000-0000-000000000002");
        language2.put("name", "French");

        languagesArray.add(language1);
        languagesArray.add(language2);

        writeJSONToFile(TEST_LANGUAGES_FILE, languagesArray);
    }
    @SuppressWarnings("unchecked")
    private void createTestWordsFile() throws IOException {
        JSONArray wordsArray = new JSONArray();

        JSONObject word1 = new JSONObject();
        word1.put("word", "hola");
        word1.put("definition", "hello");
        word1.put("difficulty", "easy");
        word1.put("translation", "hello");

        JSONObject word2 = new JSONObject();
        word2.put("word", "gracias");
        word2.put("definition", "thank you");
        word2.put("difficulty", "easy");
        word2.put("translation", "thank you");

        wordsArray.add(word1);
        wordsArray.add(word2);

        writeJSONToFile(TEST_WORDS_FILE, wordsArray);
    }
    @SuppressWarnings("unchecked")
    private void createTestPhrasesFile() throws IOException {
        JSONArray phrasesArray = new JSONArray();

        JSONObject phrase1 = new JSONObject();
        phrase1.put("phrase", "¿Cómo estás?");
        phrase1.put("definition", "How are you?");

        JSONObject phrase2 = new JSONObject();
        phrase2.put("phrase", "Buenos días");
        phrase2.put("definition", "Good morning");

        phrasesArray.add(phrase1);
        phrasesArray.add(phrase2);

        writeJSONToFile(TEST_PHRASES_FILE, phrasesArray);
    }

    private void writeJSONToFile(String filePath, JSONArray jsonArray) throws IOException {
        // Ensure the testData directory exists
        java.io.File dir = new java.io.File("testData");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        }
    }
}
