package com.narration;
import java.util.ArrayList;

public class LanguageList {

    private static LanguageList instance;  
    private final ArrayList<Language> languages; // Made final

    private LanguageList() {
        languages = new ArrayList<>();
    }

    public static LanguageList getInstance() {
        if (instance == null) {
            instance = new LanguageList();
        }
        return instance;
    }

    // Add a language to the list
    public void addLanguage(Language language) {
        languages.add(language);
    }

    // Remove a language from the list
    public boolean removeLanguage(Language language) {
        return languages.remove(language);
    }

    // Get the list of all languages
    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public Language findLanguageByName(String name) {
        for (Language language : languages) {
            if (language.getName().equalsIgnoreCase(name)) {
                return language;
            }
        }
        return null;
    }

    public ArrayList<Language> findLanguagesByKeyWord(String keyWord) {
        ArrayList<Language> matchingLanguages = new ArrayList<>();
        for (Language language : languages) {
            if (language.getKeyWords().contains(keyWord)) {
                matchingLanguages.add(language);
            }
        }
        return matchingLanguages;
    }
}