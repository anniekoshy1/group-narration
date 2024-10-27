/**
 * Represents a matching question, where users match terms with corresponding answers.
 * Includes functionality to set, check, and reset user matches.
 */
package com.narration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchingQuestion {

    private final Map<String, String> questionPairs;
    private final Map<String, String> userMatches;

    /**
     * Constructs a MatchingQuestion with specified pairs of terms and correct matches.
     *
     * @param questionPairs a map of terms and their correct matches
     */
    public MatchingQuestion(Map<String, String> questionPairs) {
        this.questionPairs = questionPairs;
        this.userMatches = new HashMap<>();
    }

    /**
     * Sets the user's match for a specific term.
     *
     * @param term  the term to match
     * @param match the user's chosen match for the term
     */
    public void setUserMatch(String term, String match) {
        userMatches.put(term, match);
    }

    /**
     * Checks if the user's matches are all correct.
     *
     * @return true if all user matches are correct, false otherwise
     */
    public boolean checkAnswer() {
        for (String term : questionPairs.keySet()) {
            String correctMatch = questionPairs.get(term);
            String userMatch = userMatches.get(term);
            if (userMatch == null || !userMatch.equals(correctMatch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the user's matches.
     */
    public void reset() {
        userMatches.clear();
    }

    /**
     * Retrieves the correct match for a given term.
     *
     * @param term the term to find the correct match for
     * @return the correct match for the term
     */
    public String getCorrectMatch(String term) {
        return questionPairs.get(term);
    }

    /**
     * Gets a list of all terms in the question.
     *
     * @return a list of terms
     */
    public List<String> getTerms() {
        return List.copyOf(questionPairs.keySet());
    }

    /**
     * Gets a list of all correct matches in the question.
     *
     * @return a list of correct matches
     */
    public List<String> getMatches() {
        return List.copyOf(questionPairs.values());
    }

    /**
     * Retrieves the user's match for a given term.
     *
     * @param term the term to retrieve the user's match for
     * @return the user's match, or null if no match is set
     */
    public String getUserMatch(String term) {
        return userMatches.get(term);
    }

    /**
     * Shows all correct answers as a map of terms and their matches.
     *
     * @return a map of terms and correct matches
     */
    public Map<String, String> showCorrectAnswers() {
        return questionPairs;
    }
}