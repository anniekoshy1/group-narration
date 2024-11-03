package com.narration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {

    private Dictionary dictionary;
    private WordsList wordsList;

    @Before
    public void setUp() {
        // Initialize WordsList with sample data
        wordsList = new WordsList();
        Word word1 = new Word("hola", "hello", "easy", "hello");
        Word word2 = new Word("adiós", "goodbye", "easy", "goodbye");
        wordsList.addWord(word1);
        wordsList.addWord(word2);
        // Initialize Dictionary with the WordsList
        dictionary = new Dictionary(wordsList);
    }

    @Test
    public void testTranslate_SingleWord_Found() {
        // Act
        String translation = dictionary.translate("hola");
        // Assert
        assertEquals("hello", translation);
    }

    @Test
    public void testTranslate_SingleWord_NotFound() {
        // Act
        String translation = dictionary.translate("amigo");
        // Assert
        assertEquals("Translation not found!", translation);
    }

    @Test
    public void testTranslate_SingleWord_CaseInsensitive() {
        // Act
        String translation = dictionary.translate("HoLa");
        // Assert
        assertEquals("hello", translation);
    }

    @Test
    public void testTranslate_MultipleWords() {
        // Arrange
        List<String> words = Arrays.asList("hola", "adiós", "amigo");
        // Act
        Map<String, String> translations = dictionary.translate(words);
        // Assert
        assertEquals(3, translations.size());
        assertEquals("hello", translations.get("hola"));
        assertEquals("goodbye", translations.get("adiós"));
        assertEquals("Translation not found!", translations.get("amigo"));
    }

    @Test
    public void testAddTranslation() {
        // Arrange
        Word newWord = new Word("amigo", "friend", "easy", "friend");
        // Act
        dictionary.addTranslation(newWord);
        // Assert
        String translation = dictionary.translate("amigo");
        assertEquals("friend", translation);
        assertEquals(3, dictionary.getWordCount());
    }

    @Test
    public void testRemoveTranslation() {
        // Act
        dictionary.removeTranslation("adiós");
        // Assert
        String translation = dictionary.translate("adiós");
        assertEquals("Translation not found!", translation);
        assertEquals(1, dictionary.getWordCount());
    }

    @Test
    public void testGetWordCount() {
        // Act
        int count = dictionary.getWordCount();
        // Assert
        assertEquals(2, count);
    }

    @Test
    public void testGetAllTranslations() {
        // Act
        Map<String, String> allTranslations = dictionary.getAllTranslations();
        // Assert
        assertEquals(2, allTranslations.size());
        assertEquals("hello", allTranslations.get("hola"));
        assertEquals("goodbye", allTranslations.get("adiós"));
    }
}
