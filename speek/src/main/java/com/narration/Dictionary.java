package com.narration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private final WordsList wordsList;
    private final Map<String, String> translationMap;

    public Dictionary(WordsList wordsList) {
        this.wordsList = wordsList;
        this.translationMap = new HashMap<>();

        for (Word word : wordsList.getAllWords()) {
            translationMap.put(word.getWordText().toLowerCase(), word.getTranslation().toLowerCase());
        }
    }

    public String translate(String word) {
        if (word == null) {
            return "Translation not found!";
        }
        word = word.toLowerCase();
        return translationMap.getOrDefault(word, "Translation not found!");
    }

    public Map<String, String> translate(List<String> words) {
        Map<String, String> translations = new HashMap<>();
        if (words != null) {
            for (String word : words) {
                translations.put(word, translate(word));
            }
        }
        return translations;
    }

    public void addTranslation(Word word) {
        if (word == null) {
            return; // Prevent adding null word
        }
        wordsList.addWord(word);
        translationMap.put(word.getWordText().toLowerCase(), word.getTranslation().toLowerCase());
    }

    public void removeTranslation(String wordText) {
        if (wordText == null) {
            return; // Prevent removing null word
        }
        String finalWordText = wordText.toLowerCase();
        List<Word> words = wordsList.getAllWords();
        words.removeIf(word -> word.getWordText().equalsIgnoreCase(finalWordText));
        translationMap.remove(finalWordText);
    }

    public int getWordCount() {
        return wordsList.getAllWords().size();
    }

    public Map<String, String> getAllTranslations() {
        return new HashMap<>(translationMap);
    }
}
