package com.narration;
import java.util.ArrayList;
import java.util.UUID;

public class Word {

    private UUID id; 
    private String translation;
    private String wordText;  // The word
    private String definition;  // The definition or meaning of the word
    private String partOfSpeech;  // The part of speech of the word 
    private ArrayList<String> translations;
    private String language;  // The language of the word

    public Word(String wordText, String definition, String partOfSpeech, String language) {
        this.id = UUID.randomUUID();
        this.wordText = wordText;
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
        this.language = language;
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

    // Get the language of the word
    public String getLanguage() {
        return language;
    }

    // Set the language of the word
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Word: " + wordText + "\nDefinition: " + definition + "\nPart of Speech: " + partOfSpeech + "\nLanguage: " + language + "\nTranslations: " + translations;
    }
}