/**
 * Manages a list of languages within the system, providing functionality to add, remove, and retrieve languages by name or keyword
 * @author Four Musketeers
 */
package com.narration;

import java.util.ArrayList;

public class LanguageList {

    private static LanguageList instance;
    private final ArrayList<Language> languages;

    /**
     * Private constructor to initialize the language list, ensuring it is only instantiated once.
     */
    private LanguageList() {
        languages = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of the LanguageList.
     * @return the singleton LanguageList instance
     */
    public static LanguageList getInstance() {
        if (instance == null) {
            instance = new LanguageList();
        }
        return instance;
    }

    /**
     * Adds a language to the list.
     * @param language the language to add
     */
    public void addLanguage(Language language) {
        languages.add(language);
    }

    /**
     * Removes a language from the list.
     * @param language the language to remove
     * @return true if the language was successfully removed, false otherwise
     */
    public boolean removeLanguage(Language language) {
        return languages.remove(language);
    }

    /**
     * Retrieves the list of all languages.
     *
     * @return an ArrayList containing all languages
     */
    public ArrayList<Language> getLanguages() {
        return languages;
    }

    /**
     * Finds a language by its name
     * @param name the name of the language to find
     * @return the Language object if found, otherwise null
     */
    public Language findLanguageByName(String name) {
        for (Language language : languages) {
            if (language.getName().equalsIgnoreCase(name)) {
                return language;
            }
        }
        return null;
    }

    /**
     * Finds languages that contain a specified keyword
     * @param keyWord the keyword to search for
     * @return an ArrayList of languages containing the keyword
     */
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