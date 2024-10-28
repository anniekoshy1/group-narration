/**
 * A collection of Word objects, providing methods to add, filter, and access words by different criteria
 * @author Four Musketeers
 */
package com.narration;

import java.util.ArrayList;
import java.util.List;

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
     * @param word The word to be added.
     */
    public void addWord(Word word) {
        this.words.add(word);
    }


    /**
     * Retrieves a random word from the list.
     * @return A randomly selected word, or null if the list is empty.
     */
    public Word getRandomWord() {
        if (words.isEmpty()) return null;
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    /**
     * Provides access to the full list of words.
     * @return The complete list of words.
     */
    public List<Word> getAllWords() {
        return words;
    }
}