package com.narration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class ProjectUI {

    private LanguageLearningFacade facade;
    private Scanner scanner;
    private DataLoader dataLoader;
    private Lesson currentLesson;
    private Course course;
    private static Difficulty difficulty;
    private Assessment assessment;


    public ProjectUI() {
        facade = new LanguageLearningFacade();
        scanner = new Scanner(System.in);
        dataLoader = new DataLoader();
    }

//MAIN MENU
    public void start() {
        System.out.println("Welcome to the Language Learning System!");
        boolean exit = false;

        while (!exit) {
            displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    if (!isLoggedIn()) {
                        System.out.println("Please log in first.");
                    } else {
                        selectLanguage();
                    }
                    break;
                case 4:
                    if (!isLoggedIn()) {
                        System.out.println("Please log in first.");
                    } else {
                        startCourse();
                    }
                    break;
                case 5:
                    if (!isLoggedIn()) {
                        System.out.println("Please log in first.");
                    } else {
                        startAssessment();
                    }
                    break;
                case 6:
                    if (!isLoggedIn()) {
                        System.out.println("Please log in first.");
                    } else {
                        trackProgress();
                    }
                    break;
                case 7:
                    if (!isLoggedIn()) {
                        System.out.println("You are not logged in.");
                    } else {
                        logout();
                        System.out.println("Logging out.");
                    }
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Select Language");
        System.out.println("4. Start Course");
        System.out.println("5. Start Assessment");
        System.out.println("6. Track Progress");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
        System.out.print("Please enter your choice: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
//LOGIN
    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (facade.login(username, password)) {
            System.out.println("Login successful! Welcome, " + username);
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private boolean isLoggedIn() {
        return facade.getCurrentUser() != null;
    }
//REGISTER
    private void register() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        String email;
        String password;

        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (email.contains("@") && email.contains(".")) {
                break;
            } else {
                System.out.println("Invalid email, please enter a valid email that includes '@' and '.'");
            }
        }

        while (true) {
            System.out.print("Enter new password: ");
            password = scanner.nextLine();
            if (password.length() >= DataConstants.MIN_PASSWORD_LENGTH && password.length() <= DataConstants.MAX_PASSWORD_LENGTH) {
                break;
            } else {
                System.out.println("Invalid password, please enter a password between " + DataConstants.MIN_PASSWORD_LENGTH + " and " + DataConstants.MAX_PASSWORD_LENGTH + " characters.");
            }
        }
        facade.registerUser(username, email, password);
        System.out.println("Registration successful! Please login to continue.");
    }
//LANGUAGE
    private void selectLanguage() {
        if (!isLoggedIn()) {
            System.out.println("You must log in to select a language.");
            return;
        }
        System.out.println("Available languages:");
        for (Language language : facade.getAllLanguages()) {
            System.out.println("- " + language.getName());
        }
        System.out.print("Select a language: ");
        String languageName = scanner.nextLine();

        facade.selectLanguage(languageName);
        System.out.println("Language selected: " + languageName);
    }
//START COURSE
    private void startCourse() {
        ArrayList<Course> allCourses = facade.getAllCourses();
        if (!isLoggedIn()) {
            System.out.println("You must log in to start a course.");
            return;
        }
        if (allCourses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("Available courses: ");
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.println((i + 1) + ". " + allCourses.get(i).getName());
        }
    
        System.out.print("Select a course by entering the corresponding number: ");
        int courseIndex;
        try {
            courseIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
    
        if (courseIndex < 0 || courseIndex >= allCourses.size()) {
            System.out.println("Invalid selection. Please choose a valid course number.");
            return;
        }
    
        Course selectedCourse = allCourses.get(courseIndex);
        facade.startCourse(selectedCourse); 
        this.course = selectedCourse;
        System.out.println("Course started: " + selectedCourse.getName());

        courseActivitiesMenu();
    }

    private void courseActivitiesMenu() {
        boolean exit = false;
        //ACTIVITIES MENU
        while (!exit) {
            System.out.println("Course Activities:");
            System.out.println("1. Flashcard Practice");
            System.out.println("2. Storytelling");
            System.out.println("3. Exit to Main Menu");
            System.out.print("Please enter your choice: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    startFlashcards();
                    break;
                case 2:
                    startLesson();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting course activities");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
//FLASHCARDS
    private void startFlashcards() {
        System.out.println("Starting Flashcard Practice...");
        
        List<FlashcardQuestion> flashcards = dataLoader.loadFlashcardsFromJson("speek/docs/JSON/Words.json");

        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available.");
            return;
        }

        for (FlashcardQuestion flashcard : flashcards) {
            
            if (flashcard.getFlashcardProgress() >= 100) {
                System.out.println("All flashcards completed! Returning to Course Activities.");
                startAssessment();  // Start assessment after all flashcards are completed
                return;
            }
            if (flashcard.isCompleted()) continue;

            System.out.println("Translate the following: " + flashcard.getFrontInfo());
            Narriator.playSound(flashcard.getFrontInfo());

            String userAnswer = scanner.nextLine().trim();
            flashcard.submitAnswer(userAnswer);

            if (flashcard.checkAnswer()) {
                System.out.println("Correct!");
                Narriator.playSound("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer is: " + flashcard.showCorrectAnswer());
                Narriator.playSound("The correct answer is: " + flashcard.showCorrectAnswer());
            }

            System.out.print("Enter 'done' to mark this flashcard as completed or press Enter to continue: ");
            String continueResponse = scanner.nextLine().trim();

            // Check if the user entered "done"
            if (continueResponse.equalsIgnoreCase("done")) {
                flashcard.markAsCompleted(continueResponse);  // Mark flashcard as completed
                System.out.println("Flashcard marked as complete.");
                startAssessment();  // Start the assessment immediately
                return;  // Exit the flashcard loop
            } else {
                flashcard.markAsCompleted("");  // Optionally mark as complete on empty input
                course.calculateProgress();
            }

            System.out.println("Flashcard Progress: " + flashcard.getFlashcardProgress() + "%");
        }

        System.out.println("Exiting Flashcard Practice.");
        promptForAssessment();
    }
    //REPEATING FLASHCARDS
    private void promptForRepeatFlashcards() {
        System.out.println("Do you want to try the flashcards again? (yes/no)");
        String response = scanner.nextLine().trim();
    
        if (response.equalsIgnoreCase("yes")) {
            startFlashcards();  
        } else {
            System.out.println("Returning to Course Activities.");
            courseActivitiesMenu(); 
        }
    }
//LESSON/STORYTELLING
    private void startLesson() {
        System.out.println("Loading Storytelling...");

        Course currentCourse = facade.getCurrentUser().getCourses().get(0);
        if (currentCourse == null || currentCourse.getAllLessons().isEmpty()) {
            System.out.println("No lessons are available in the current course.");
            return;
        }

    if (!assessment.hasPassed()) {
        System.out.println("You need to pass the assessment to unlock Storytelling. Please retake the assessment.");
        return; // Exit the method if the assessment hasn't been passed
    }

        currentLesson = currentCourse.getAllLessons().get(0);
        System.out.println("Starting Storytelling...");
        
        String spanishStory = currentLesson.getSpanishContent();
        String englishStory = currentLesson.getEnglishContent();

        System.out.println("Reading story in Spanish...");
        Narriator.playSound(spanishStory);
        System.out.println(spanishStory);

        System.out.println("Reading story in English...");
        Narriator.playSound(englishStory);
        System.out.println(englishStory);

        System.out.print("Enter 'done' to mark lesson as completed or press enteras completed or press enter: ");
        String continueResponse = scanner.nextLine().trim();

        if (continueResponse.equalsIgnoreCase("done")) {
            currentLesson.markAsCompleted();
            System.out.println("Storytelling marked completed.");
            course.calculateProgress();
            startAssessment2(); 
        } else {
            System.out.println("Exiting Storytelling.");
        }
    }


    //PROMPT FOR ASSESSMENT AFTER FLASHCARDS
    private void promptForAssessment() {
        System.out.println("Do you want to start an assessment for this lesson? (yes/no)");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("yes")) {
            startAssessment();
        } else {
            System.out.println("Returning to main menu.");
        }
    }


    //ASSESSMENT FOR FLASHCARDS
    private void startAssessment() {
        System.out.println("Starting Assessment...");

        if (course == null) {
            System.out.println("No course selected. Returning to main menu.");
            return;
        }

        WordsList wordsList = DataLoader.loadWords();
        List<Word> words = wordsList.getAllWords();

        if (words.isEmpty()) {
            System.out.println("No words available for assessment.");
            return;
        }

        Random random = new Random();
        
        // Select a random word for the multiple-choice question
        Word multipleChoiceWord = words.get(random.nextInt(words.size()));
        String englishTranslationMCQ = multipleChoiceWord.getTranslation();
        String spanishWordMCQ = multipleChoiceWord.getWordText();

        //Generating two random options for multiple choice questions
        String choice1 = words.get(random.nextInt(words.size())).getWordText();
        String choice2 = words.get(random.nextInt(words.size())).getWordText();

        // Ensure the open-ended question uses a different word
        Word openEndedWord;
        do {
            openEndedWord = words.get(random.nextInt(words.size()));
        } while (openEndedWord.equals(multipleChoiceWord));
        
        String englishTranslationOpenEnded = openEndedWord.getTranslation();
        String spanishWordOpenEnded = openEndedWord.getWordText();

        // Generate assessment questions
        List<Questions> assessmentQuestions = new ArrayList<>();

        // Generate random True/False questions
        System.out.println("Please answer the following True/False questions:");
        for (int i = 0; i < 3; i++) { // Number of True/False questions to generate
            Word word = words.get(random.nextInt(words.size()));
            String trueTranslation = word.getTranslation();
            String questionText;
            boolean correctAnswer;

            // Flip translation with a 50% chance
            if (random.nextBoolean()) {
                // Correct translation
                questionText = word.getWordText() + " means '" + trueTranslation + "'. True/Fal";
                correctAnswer = true;
            } else {
                // Incorrect translation
                Word incorrectWord = words.get(random.nextInt(words.size()));
                while (incorrectWord.equals(word)) {
                    incorrectWord = words.get(random.nextInt(words.size())); // Ensure different word
                }
                questionText = word.getWordText() + " means '" + incorrectWord.getTranslation() + "'. T/F";
                correctAnswer = false;
            }

            assessmentQuestions.add(new Questions(questionText, correctAnswer, Difficulty.RUDIMENTARY));
        }

        // Multiple Choice Question
        List<String> choices = List.of("1. " + choice1, "2. " + choice2, "3. " + spanishWordMCQ);
        String multipleChoiceQuestionText = "What is the Spanish word for '" + englishTranslationMCQ + "'?\n" + String.join("\n", choices);
        Questions multipleChoiceQuestion = new Questions(multipleChoiceQuestionText, choices, "3", Difficulty.INTERMEDIATE); 
        assessmentQuestions.add(multipleChoiceQuestion);

        // Open-Ended Question with a different word
        Questions openEndedQuestion = new Questions("What is the word for '" + englishTranslationOpenEnded + "' in Spanish?", spanishWordOpenEnded, Difficulty.ADVANCED);
        assessmentQuestions.add(openEndedQuestion);

        // Create assessment object
        assessment = new Assessment(UUID.randomUUID(), Assessment.AssessmentType.TRUE_FALSE, assessmentQuestions);

        // Iterate and display questions to user
        for (Questions question : assessment.getQuestions()) {
            System.out.println(question.getQuestionText());
            
            if (question.getOptions() != null) {
                System.out.println("Options: " + String.join(", ", question.getOptions()));
            }

            // Collect and submit user's answer
            String userAnswer = scanner.nextLine().trim();
            question.submitAnswer(userAnswer);
        }
        
        // Calculate and display score
        int score = assessment.calculateScore();
        System.out.println("Your score: " + score + "%");

        // Display rating based on score
        int rating = assessment.calculateRating();
        System.out.println("Your rating: " + rating + " out of 5 stars");

        if (assessment.hasPassed()) {
        System.out.println("Congratulations! You passed the flashcard assessment with a score of " + score + "%. You may now continue to Storytelling lesson");
        } else {
            System.out.println("You did not pass the assessment. Your score is " + score + "%. Please try again to continue to the Storytelling Lesson.");
            System.out.print("Do you want to retake the assessment? (yes/no): ");
        String retakeResponse = scanner.nextLine().trim().toLowerCase();
        if (retakeResponse.equals("yes")) {
            assessment.retakeAssessment();  // Reset the assessment for a retake
            startAssessment();  // Start the assessment again
        } else {
            System.out.println("Returning to the main menu.");
        }
        }
    }

    //START ASSESSMENT STORY2
    private void startAssessment2() {
        System.out.println("Starting Assessment...");

        if (course == null) {
            Narriator.playSound("No course selected. Returning to main menu.");
            System.out.println("No course selected. Returning to main menu.");
            return;
        }

        WordsList wordsList = DataLoader.loadWords();
        List<Word> words = wordsList.getAllWords();

        if (words.isEmpty()) {
            Narriator.playSound("No words available for assessment.");
            System.out.println("No words available for assessment.");
            return;
        }

        Random random = new Random();
        Word multipleChoiceWord = words.get(random.nextInt(words.size()));
        String englishTranslationMCQ = multipleChoiceWord.getTranslation();
        String spanishWordMCQ = multipleChoiceWord.getWordText();
        String choice1 = words.get(random.nextInt(words.size())).getWordText();
        String choice2 = words.get(random.nextInt(words.size())).getWordText();

        Word openEndedWord;
        do {
            openEndedWord = words.get(random.nextInt(words.size()));
        } while (openEndedWord.equals(multipleChoiceWord));

        String englishTranslationOpenEnded = openEndedWord.getTranslation();
        String spanishWordOpenEnded = openEndedWord.getWordText();

        List<Questions> assessmentQuestions = new ArrayList<>();

        Narriator.playSound("Please answer the following True or False questions.");
        for (int i = 0; i < 3; i++) { 
            Word word = words.get(random.nextInt(words.size()));
            String trueTranslation = word.getTranslation();
            String questionText;
            boolean correctAnswer;

            if (random.nextBoolean()) {
                questionText = word.getWordText() + " means '" + trueTranslation + "'. True or False?";
                correctAnswer = true;
            } else {
                Word incorrectWord = words.get(random.nextInt(words.size()));
                while (incorrectWord.equals(word)) {
                    incorrectWord = words.get(random.nextInt(words.size())); 
                }
                questionText = word.getWordText() + " means '" + incorrectWord.getTranslation() + "'. True or False?";
                correctAnswer = false;
            }

            Narriator.playSound(questionText);
            assessmentQuestions.add(new Questions(questionText, correctAnswer, Difficulty.RUDIMENTARY));
        }

        List<String> choices = List.of("1. " + choice1, "2. " + choice2, "3. " + spanishWordMCQ);
        String multipleChoiceQuestionText = "What is the Spanish word for '" + englishTranslationMCQ + "'?\n" + String.join("\n", choices);
        Narriator.playSound(multipleChoiceQuestionText);
        Questions multipleChoiceQuestion = new Questions(multipleChoiceQuestionText, choices, "3", Difficulty.INTERMEDIATE); 
        assessmentQuestions.add(multipleChoiceQuestion);

        String openEndedQuestionText = "What is the word for '" + englishTranslationOpenEnded + "' in Spanish?";
        Narriator.playSound(openEndedQuestionText);
        Questions openEndedQuestion = new Questions(openEndedQuestionText, spanishWordOpenEnded, Difficulty.ADVANCED);
        assessmentQuestions.add(openEndedQuestion);

        assessment = new Assessment(UUID.randomUUID(), Assessment.AssessmentType.TRUE_FALSE, assessmentQuestions);

        for (Questions question : assessment.getQuestions()) {
            Narriator.playSound(question.getQuestionText());
            System.out.println(question.getQuestionText());
            
            if (question.getOptions() != null) {
                Narriator.playSound("Options are: " + String.join(", ", question.getOptions()));
                System.out.println("Options: " + String.join(", ", question.getOptions()));
            }
    
            String userAnswer = scanner.nextLine().trim();
            question.submitAnswer(userAnswer);
        }

        int score = assessment.calculateScore();
        Narriator.playSound("Your score is " + score + " percent.");
        System.out.println("Your score: " + score + "%");
    
        int rating = assessment.calculateRating();
        Narriator.playSound("Your rating is " + rating + " out of 5 stars.");
        System.out.println("Your rating: " + rating + " out of 5 stars");
    
        if (assessment.hasPassed()) {
            Narriator.playSound("Congratulations! You passed the assessment with a score of " + score + " percent. You may now continue to the Storytelling lesson.");
            System.out.println("Congratulations! You passed the assessment with a score of " + score + "%. You may now continue to the Storytelling lesson.");
        } else {
            Narriator.playSound("You did not pass the assessment. Your score is " + score + " percent. Please try again to continue to the Storytelling lesson.");
            System.out.println("You did not pass the assessment. Your score is " + score + "%. Please try again to continue to the Storytelling lesson.");
            System.out.print("Do you want to retake the assessment? (yes/no): ");
            String retakeResponse = scanner.nextLine().trim().toLowerCase();
            if (retakeResponse.equals("yes")) {
                assessment.retakeAssessment();
                startAssessment2();
            } else {
                Narriator.playSound("Returning to the main menu.");
                System.out.println("Returning to the main menu.");
            }
        }
    }

    private void trackProgress() {
        System.out.println("Tracking progress...");
        double progress = facade.trackOverallProgress();
        System.out.println("Your overall progress: " + progress + "%");
    }

    private void logout() {
        facade.saveAndLogout();
        System.out.println("You have been logged out.");
    }

    public static void main(String[] args) {
        ProjectUI languageInterface = new ProjectUI();
        languageInterface.start();
    }
}

