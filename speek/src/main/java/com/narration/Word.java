package com.narration;

import java.util.ArrayList;
import java.util.UUID;

public class Word {

    private final UUID id; 
    private String translation;
    private String wordText;  // The word
    private String definition;  // The definition or meaning of the word
    private String partOfSpeech;  // The part of speech of the word 
    private final ArrayList<String> translations = new ArrayList<>();  // Initialize and set as final
    private String language;  // The language of the word
    private String difficulty;

    public Word(String wordText, String definition, String difficulty, String translation) {
        this.id = UUID.randomUUID();
        this.wordText = wordText;
        this.definition = definition;
        this.difficulty = difficulty;
        this.translation = translation;
    }

    public UUID getId() {
        return id;
    }

    public String getTranslation() {
        return this.translation;
    }

    public String getWordText() {
        return wordText;
    }
    
    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public ArrayList<String> getTranslations() {
        return translations;
    }

    public void addTranslation(String translation) {
        if (!translations.contains(translation)) {
            translations.add(translation);
        }
    }

    public void removeTranslation(String translation) {
        translations.remove(translation);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    @Override
    public String toString() {
        return "Word: " + wordText + "\nDefinition: " + definition + "\nPart of Speech: " + partOfSpeech + "\nLanguage: " + language + "\nTranslations: " + translations;
    }
}