package com.narration;
import java.util.ArrayList;
import java.util.List;

public class WordsList {
    private List<Word> words;

    public WordsList() {
        this.words = new ArrayList<>();
    }

    // Add a word to the list
    public void addWord(Word word) {
        this.words.add(word);
    }

    // Get words by a specific part of speech
    public List<Word> getWordsByPartOfSpeech(String partOfSpeech) {
        List<Word> filteredWords = new ArrayList<>();
        for (Word word : words) {
            if (word.getPartOfSpeech().equalsIgnoreCase(partOfSpeech)) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    // Get a random word
    public Word getRandomWord() {
        if (words.isEmpty()) return null;
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    // Retrieve all words
    public List<Word> getAllWords() {
        return words;
    }
}
