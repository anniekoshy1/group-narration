package com.narration;
import java.util.ArrayList;
import java.util.List;

public class PhraseList {
    private List<Phrase> phrases;

    public PhraseList() {
        this.phrases = new ArrayList<>();
    }

    // Add a phrase to the list
    public void addPhrase(Phrase phrase) {
        phrases.add(phrase);
    }

    // Get phrases by a particular part of speech
    public List<Phrase> getPhrasesByPartOfSpeech(String partOfSpeech) {
        List<Phrase> filteredPhrases = new ArrayList<>();
        for (Phrase phrase : phrases) {
            if (phrase.getPartOfSpeech().equalsIgnoreCase(partOfSpeech)) {
                filteredPhrases.add(phrase);
            }
        }
        return filteredPhrases;
    }

    // Get a random phrase
    public Phrase getRandomPhrase() {
        if (phrases.isEmpty()) return null;
        int randomIndex = (int) (Math.random() * phrases.size());
        return phrases.get(randomIndex);
    }

    // Retrieve all phrases
    public List<Phrase> getAllPhrases() {
        return phrases;
    }
}
