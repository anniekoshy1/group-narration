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

    public ProjectUI() {
        facade = new LanguageLearningFacade();
        scanner = new Scanner(System.in);
        dataLoader = new DataLoader();
    }


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

    private void startLesson() {
        System.out.println("Starting Storytelling...");

        Course currentCourse = facade.getCurrentUser().getCourses().get(0);
        if (currentCourse == null || currentCourse.getAllLessons().isEmpty()) {
            System.out.println("No lessons are available in the current course.");
            return;
        }

        currentLesson = currentCourse.getAllLessons().get(0);
        
        String spanishStory = "Una mañana, la familia Méndez se estaba preparando para ir a la escuela. Mía, la hija, tenía que cepillarse los dientes, cepillarse el pelo, desayunar y vestirse. El hijo, Juan, tuvo que cepillarse los dientes, desayunar y hacer la mochila. Mamá preparó la comida y papá los acompañó a la escuela.";
        String englishStory = "One morning, the Mendez family was getting ready for school. Mia, the daughter, had to brush her teeth, brush her hair, eat breakfast, and put on clothes. The son, Juan, had to brush his teeth, eat breakfast, and pack his backpack. Mom made the food, and dad walked them to school.";

        System.out.println("Reading story in Spanish...");
        Narriator.playSound(spanishStory);
        System.out.println(spanishStory);

        System.out.println("Now reading the translation...");
        Narriator.playSound(englishStory);
        System.out.println(englishStory);

        System.out.print("Enter 'done' to mark lesson as completed or press enteras completed or press enter: ");
        String continueResponse = scanner.nextLine().trim();
        if (continueResponse.equalsIgnoreCase("done")) {
            currentLesson.markAsCompleted();
            System.out.println("Storytelling marked completed");
            course.calculateProgress();
            startAssessment();
            
        }
        System.out.println("Exiting Storytelling.");
        promptForAssessment();
    }

    private void promptForAssessment() {
        System.out.println("Do you want to start an assessment for this lesson? (yes/no)");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("yes")) {
            startAssessment();
        } else {
            System.out.println("Retunring to main menu.");
        }
    }


    private void startAssessment() {
    System.out.println("Starting Assessment...");

    if (course == null) {
        System.out.println("No course selected. Returning to main menu.");
        return;
    }

    // Load words and ensure words list is populated
    WordsList wordsList = dataLoader.loadWords();
    List<Word> words = wordsList.getAllWords();

    if (words.isEmpty()) {
        System.out.println("No words available for assessment.");
        return;
    }

    // Retrieve English translation and Spanish word for the assessment questions
    Random random = new Random();
    Word randomWord = words.get(random.nextInt(words.size()));  // Using first word for demonstration
    String englishTranslation = randomWord.getTranslation();  
    String spanishWord = randomWord.getWordText();  

    // Generate assessment questions
    List<Questions> assessmentQuestions = new ArrayList<>();

    // True/False questions
    System.out.println("Please answer the following True/False questions:");
    assessmentQuestions.add(new Questions("Hola means Hello. T/F", true, Difficulty.RUDIMENTARY));
    assessmentQuestions.add(new Questions("The Spanish word for hat is amigo. T/F", false, Difficulty.INTERMEDIATE));
    assessmentQuestions.add(new Questions("Padre means father. T/F", true, Difficulty.ADVANCED));

    // Multiple Choice Question
    List<String> choices = List.of("1. Adiós", "2. Gracias", "3. " + spanishWord);
    String multipleChoiceQuestionText = "What is the Spanish word for '" + englishTranslation + "'?\n" + String.join("\n", choices);
    Questions multipleChoiceQuestion = new Questions(multipleChoiceQuestionText, choices, "3", Difficulty.INTERMEDIATE); 
    assessmentQuestions.add(multipleChoiceQuestion);

    // Open-Ended Question
    Questions openEndedQuestion = new Questions("What is the word for '" + englishTranslation + "' in Spanish?", "", Difficulty.ADVANCED);
    assessmentQuestions.add(openEndedQuestion);

    // Create assessment object
    Assessment assessment = new Assessment(UUID.randomUUID(), Assessment.AssessmentType.TRUE_FALSE, assessmentQuestions);

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

