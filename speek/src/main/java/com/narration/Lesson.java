package com.narration;
import java.util.ArrayList;
import java.util.UUID;

public class Lesson {

    private UUID id;
    private double lessonProgress;  // The user's progress in the lesson
    private String description;  // A brief description of the lesson
    private ArrayList<Word> words;  // List of words associated with the lesson
    private ArrayList<Questions> questions;  // List of questions related to the lesson
    private ArrayList<String> historicalFacts;  // Historical facts associated with the lesson
    private ArrayList<String> culturalFigures;  // Cultural figures associated with the lesson
    private ArrayList<String> holidays;  // Holidays discussed in the lesson
    private ArrayList<String> foods;  // Foods associated with the lesson
    private Dictionary topic;  // Dictionary of words related to the lesson
    private boolean completed; 

    public Lesson(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.lessonProgress = 0.0;
        this.words = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.historicalFacts = new ArrayList<>();
        this.culturalFigures = new ArrayList<>();
        this.holidays = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.topic = new Dictionary(new WordsList());
    }

    public Lesson(UUID id, double lessonProgress, String description) {
        this.id = id;
        this.lessonProgress = lessonProgress;
        this.description = description;
        this.words = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.historicalFacts = new ArrayList<>();
        this.culturalFigures = new ArrayList<>();
        this.holidays = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.topic = new Dictionary(new WordsList());
    }

    public UUID getId() {
        return id;
    }

    public double getLessonProgress() {
        return lessonProgress;
    }

    // Set the progress for the lesson
    public void setLessonProgress(double progress) {
        this.lessonProgress = progress;
    }

    // Get the description of the lesson
    public String getDescription() {
        return description;
    }

    // Set the description of the lesson
    public void setDescription(String description) {
        this.description = description;
    }

    // Get all words associated with the lesson
    public ArrayList<Word> getWords() {
        return words;
    }

    // Add a word to the lesson
    public void addWord(Word word) {
        words.add(word);
    }

    // Get all questions associated with the lesson
    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    // Add a question to the lesson
    public void addQuestion(Questions question) {
        questions.add(question);
    }

    // Get historical facts related to the lesson
    public ArrayList<String> getHistoricalFacts() {
        return historicalFacts;
    }

    // Add a historical fact to the lesson
    public void addHistoricalFact(String fact) {
        historicalFacts.add(fact);
    }

    // Get cultural figures associated with the lesson
    public ArrayList<String> getCulturalFigures() {
        return culturalFigures;
    }

    // Add a cultural figure to the lesson
    public void addCulturalFigure(String figure) {
        culturalFigures.add(figure);
    }

    // Get holidays associated with the lesson
    public ArrayList<String> getHolidays() {
        return holidays;
    }

    // Add a holiday to the lesson
    public void addHoliday(String holiday) {
        holidays.add(holiday);
    }

    // Get foods discussed in the lesson
    public ArrayList<String> getFoods() {
        return foods;
    }

    // Add a food to the lesson
    public void addFood(String food) {
        foods.add(food);
    }

    // Get the dictionary related to the lesson's topic
    public Dictionary getTopic() {
        return topic;
    }

    // Set the dictionary topic for the lesson
    public void setTopic(Dictionary topic) {
        this.topic = topic;
    }

    public boolean isCompletedLesson() {
        return this.lessonProgress ==100;
    }

    // Set the lesson as completed
    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            this.lessonProgress = 100.0;  // If the course is marked completed, set progress to 100%
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    // Get the next lesson in a course
    public Lesson getNextLesson(Course course) {
        return null;
    }

    // Get the previous lesson in a course 
    public Lesson getPreviousLesson(Course course) {
        return null;
    }

    // Get a random piece of content 
    public String getRandomContent() {
        ArrayList<String> combinedContent = new ArrayList<>();
        combinedContent.addAll(historicalFacts);
        combinedContent.addAll(culturalFigures);
        combinedContent.addAll(holidays);
        combinedContent.addAll(foods);

        if (!combinedContent.isEmpty()) {
            int randomIndex = (int) (Math.random() * combinedContent.size());
            return combinedContent.get(randomIndex);
        }
        return "No content available";
    }

    @Override
    public String toString() {
        return "Lesson Description: " + description;
    }
}