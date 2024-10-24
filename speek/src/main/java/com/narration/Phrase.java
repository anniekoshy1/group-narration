package com.narration;
import java.util.ArrayList;
import java.util.UUID;

public class Phrase {

    private UUID id; 
    private String phraseText;  // The phrase itself
    private String definition;  // The definition of the phrase
    private String partOfSpeech;  // The part of speech of the phrase
    private ArrayList<String> synonyms;  // List of synonyms for the phrase

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

    // Get the phrase text
    public String getPhraseText() {
        return phraseText;
    }

    // Set the phrase text
    public void setPhraseText(String phraseText) {
        this.phraseText = phraseText;
    }

    // Get the definition of the phrase
    public String getDefinition() {
        return definition;
    }

    // Set the definition of the phrase
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    // Get the part of speech of the phrase
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    // Set the part of speech of the phrase
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    // Get the list of synonyms for the phrase
    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    // Add a synonym to the phrase
    public void addSynonym(String synonym) {
        if (!synonyms.contains(synonym)) {
            synonyms.add(synonym);
        }
    }

    // Remove a synonym from the phrase
    public void removeSynonym(String synonym) {
        synonyms.remove(synonym);
    }
    
    @Override
    public String toString() {
        return "Phrase: " + phraseText + "\nDefinition: " + definition + "\nPart of Speech: " + partOfSpeech + "\nSynonyms: " + synonyms;
    }
}