package com.narration;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DataWriterTest {

    @Test
    public void saveUsers_WhenCalled_SavesUserListToFile() {
        ArrayList<User> users = new ArrayList<>();
        User user = new User(UUID.randomUUID(), "testuser", "test@example.com", "password123", new ArrayList<>(), null, null, UUID.randomUUID(), new ArrayList<>(), UUID.randomUUID(), "English");
        users.add(user);

        DataWriter.saveUsers(users);

        assertTrue("Users should be saved without exceptions", true);
    }

    @Test
    public void saveCourses_WhenCalled_SavesCourseListToFile() {
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course(UUID.randomUUID(), "Sample Course", "Sample Description", true, 0.0, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new FlashcardQuestion("Q", "A"));
        courses.add(course);

        DataWriter.saveCourses(courses);

        assertTrue("Courses should be saved without exceptions", true);
    }

    @Test
    public void saveLanguages_WhenCalled_SavesLanguageListToFile() {
        ArrayList<Language> languages = new ArrayList<>();
        Language language = new Language(UUID.randomUUID(), "English");
        languages.add(language);

        DataWriter dataWriter = new DataWriter();
        boolean success = dataWriter.saveLanguages(languages);

        assertTrue("Languages should be saved successfully", success);
    }

    @Test
    public void saveWords_WhenCalled_SavesWordsListToFile() {
        WordsList wordsList = new WordsList();
        wordsList.addWord(new Word("hello", "greeting", "Beginner", "hola"));

        DataWriter dataWriter = new DataWriter();
        dataWriter.saveWords(wordsList);

        assertTrue("Words should be saved without exceptions", true);
    }

    @Test
    public void savePhrases_WhenCalled_SavesPhraseListToFile() {
        PhraseList phraseList = new PhraseList();
        phraseList.addPhrase(new Phrase("good morning", "morning greeting"));

        DataWriter dataWriter = new DataWriter();
        dataWriter.savePhrases(phraseList);

        assertTrue("Phrases should be saved without exceptions", true);
    }
}