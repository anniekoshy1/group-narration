import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private WordsList wordsList;  
    private Map<String, String> translationMap;  // Map to store translations (e.g., "hello" -> "hola")

    // Constructor that takes in a WordsList
    public Dictionary(WordsList wordsList) {
        this.wordsList = wordsList;
        this.translationMap = new HashMap<>();

        // Populate the translation map with words from WordsList
        for (Word word : wordsList.getAllWords()) {
            translationMap.put(word.getWordText().toLowerCase(), word.getTranslation().toLowerCase());
        }
    }

    // Method to translate a word from the source language to the target language
    public String translate(String word) {
        // Convert to lowercase for consistent lookup
        word = word.toLowerCase();

        // Check if the word exists in the translation map
        if (translationMap.containsKey(word)) {
            return translationMap.get(word); 
        } else {
            return "Translation not found!";
        }
    }

    // Method to translate a list of words
    public Map<String, String> translate(List<String> words) {
        Map<String, String> translations = new HashMap<>();

        for (String word : words) {
            translations.put(word, translate(word));
        }

        return translations;
    }

    // Method to add a new word and its translation to the dictionary
    public void addTranslation(Word word) {
        wordsList.addWord(word);  // Add to WordsList
        translationMap.put(word.getWordText().toLowerCase(), word.getTranslation().toLowerCase());  // Add to translation map
    }

    // Method to remove a word from the dictionary
    public void removeTranslation(String wordText) {
        String finalWordText = wordText.toLowerCase();

        // Find the word in the WordsList and remove it
        List<Word> words = wordsList.getAllWords();
        words.removeIf(word -> word.getWordText().equalsIgnoreCase(finalWordText));

        // Remove the word from the translation map
        translationMap.remove(finalWordText);
    }

    // Retrieve the number of words in the dictionary
    public int getWordCount() {
        return wordsList.getAllWords().size();
    }

    // Get all translations
    public Map<String, String> getAllTranslations() {
        return new HashMap<>(translationMap);  // Return a copy of the translation map
    }
}
