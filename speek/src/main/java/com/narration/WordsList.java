package com.narration;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of Word objects, providing methods to add, filter, and access words by different criteria.
 */
public class WordsList {
    private final List<Word> words;

    /**
     * Constructs an empty WordsList.
     */
    public WordsList() {
        this.words = new ArrayList<>();
    }

    /**
     * Adds a word to the list.
     *
     * @param word The word to be added.
     */
    public void addWord(Word word) {
        this.words.add(word);
    }

    /**
     * Retrieves words filtered by a specific part of speech.
     *
     * @param partOfSpeech The part of speech to filter by.
     * @return A list of words matching the specified part of speech.
     */
    public List<Word> getWordsByPartOfSpeech(String partOfSpeech) {
        List<Word> filteredWords = new ArrayList<>();
        for (Word word : words) {
            if (word.getPartOfSpeech().equalsIgnoreCase(partOfSpeech)) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    /**
     * Retrieves a random word from the list.
     *
     * @return A randomly selected word, or null if the list is empty.
     */
    public Word getRandomWord() {
        if (words.isEmpty()) return null;
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    /**
     * Provides access to the full list of words.
     *
     * @return The complete list of words.
     */
    public List<Word> getAllWords() {
        return words;
    }
}