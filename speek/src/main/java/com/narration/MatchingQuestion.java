package com.narration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchingQuestion {

    private Map<String, String> questionPairs;
    private Map<String, String> userMatches;

    public MatchingQuestion(Map<String, String> questionPairs) {
        this.questionPairs = questionPairs;
        this.userMatches = new HashMap<>();
    }

    public void setUserMatch(String term, String match) {
        userMatches.put(term, match);
    }

    // Check if the user's matches are correct
    public boolean checkAnswer() {
        for (String term : questionPairs.keySet()) {
            String correctMatch = questionPairs.get(term);
            String userMatch = userMatches.get(term);
            if (userMatch == null || !userMatch.equals(correctMatch)) {
                return false;  // Return false if any match is incorrect
            }
        }
        return true;  // Return true if all matches are correct
    }

    public void reset() {
        userMatches.clear();
    }

    // Get the correct match for a term
    public String getCorrectMatch(String term) {
        return questionPairs.get(term);
    }

    // Get the list of all terms in the question
    public List<String> getTerms() {
        return List.copyOf(questionPairs.keySet());
    }

    // Get the list of all possible matches in the question
    public List<String> getMatches() {
        return List.copyOf(questionPairs.values());
    }

    public String getUserMatch(String term) {
        return userMatches.get(term);
    }

    // Display the correct answers for all terms
    public Map<String, String> showCorrectAnswers() {
        return questionPairs;
    }
}