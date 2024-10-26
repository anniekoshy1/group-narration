package com.narration;

import java.util.ArrayList;
import java.util.UUID;

public class Phrase {

    private final UUID id; 
    private String phraseText;  // The phrase itself
    private String definition;  // The definition of the phrase
    private String partOfSpeech;  // The part of speech of the phrase
    private final ArrayList<String> synonyms;  // List of synonyms for the phrase

    public Phrase(String phraseText, String definition, String partOfSpeech) {
        this.id = UUID.randomUUID();
        this.phraseText = phraseText;
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
        this.synonyms = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getPhraseText() {
        return phraseText;
    }

    public void setPhraseText(String phraseText) {
        this.phraseText = phraseText;
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

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void addSynonym(String synonym) {
        if (!synonyms.contains(synonym)) {
            synonyms.add(synonym);
        }
    }

    public void removeSynonym(String synonym) {
        synonyms.remove(synonym);
    }
    
    @Override
    public String toString() {
        return "Phrase: " + phraseText + "\nDefinition: " + definition + "\nPart of Speech: " + partOfSpeech + "\nSynonyms: " + synonyms;
    }
}