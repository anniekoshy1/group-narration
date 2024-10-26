package com.narration;
import java.util.List;
import java.util.Scanner;

public class ProjectUI {

    private LanguageLearningFacade facade;
    private Scanner scanner;
    private DataConstants dataConstants;

    public ProjectUI() {
        facade = new LanguageLearningFacade();
        scanner = new Scanner(System.in);
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

    // Display the main menu options to the user
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

    // Get the user's menu choice
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;  // Return an invalid choice if input isn't a number
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
        if (!isLoggedIn()) {
            System.out.println("You must log in to start a course.");
            return;
        }

        System.out.println("Available courses: ");
        for (Course course : facade.getAllCourses()) {
            System.out.println("- " + course.getName());
        }

        System.out.print("Select a course to start: ");
        String courseName = scanner.nextLine(); 

        Course selectedCourse = facade.getAllCourses().stream()
                .filter(course -> course.getName().equalsIgnoreCase(courseName) || 
                        "Starting Out".equalsIgnoreCase(courseName))
                .findFirst()
                .orElse(null);
                
                if (selectedCourse != null) {
                    facade.startCourse(selectedCourse); 
                    System.out.println("Course started: " + selectedCourse.getName());
                    courseActivitiesMenu(); // Go directly to activities menu
                } else {
                    System.out.println("Course not found.");
                }
    }

    private void courseActivitiesMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nCourse Activities:");
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
                    startStoryTelling();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting course activites");
                    break;
                default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void startFlashcards() {
        System.out.println("Starting Flashcard Practice...");

        List<FlashcardQuestion> flashcards = loadFlashcardsFromJson("speek/docs/JSON/Words.json");

        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available.");
            return;
        }

        for (FlashcardQuestion flashcard : flashcards) {
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
            flashcard.markAsCompleted(continueResponse);

            if (flashcard.isCompleted()) {
                System.out.println("Flashcard marked as complete.");
            }
            System.out.println("Flashcard Progress: " + flashcard.getFlashcardProgress() + "%");
        }

        System.out.println("Exiting Flashcard Practice.");
    }

        private void startStoryTelling() {
            System.out.println("Starting Storytelling...");

            String spanishStory = "Una mañana, la familia Méndez se estaba preparando para ir a la escuela. Mía, la hija, tenía que cepillarse los dientes, cepillarse el pelo, desayunar y vestirse. El hijo, Juan, tuvo que cepillarse los dientes, desayunar y hacer la mochila. Mamá preparó la comida y papá los acompañó a la escuela.";
            String englishStory = "One morning, the Mendez family was getting ready for school. Mia, the daughter, had to brush her teeth, brush her hair, eat breakfast, and put on clothes. The son, Juan, had to brush his teeth, eat breakfast, and pack his backpack. Mom made the food, and dad walked them to school.";

            System.out.println("Reading story in Spanish...");
            Narriator.playSound(spanishStory);
            System.out.println(spanishStory);

            System.out.println("Now reading the translation...");
            Narriator.playSound(englishStory);
            System.out.println(englishStory);

            System.out.print("Enter 'done' to mark stoytelling as completed or press enter to continue");
            String continueResponse = scanner.nextLine().trim();
            if (continueResponse.equalsIgnoreCase("done")) {
                facade.markStoryTellingComplete();
            }
            System.out.println("Exiting Storytelling.");
        }

        private void startAssessment() {
        Course currentCourse = facade.getCurrentUser().getCourses().get(0);

        currentCourse.calculateProgress();

        if (currentCourse.completedCourse()) {
            System.out.println("Course is completed. Do you want to start the assessment? (yes/no)");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                Assessment assessment = currentCourse.getAllAssessments().get(0);
                loadQuestionsFromJson(assessment);
                facade.startAssessment(assessment);
                System.out.println("Assessment started.");
            } else {
                System.out.println("Assessment not started.");
            }
        } else {
            System.out.println("You need to complete the course before taking the assessment.");
        }
    }

    private void loadQuestionsFromJson(Assessment assessment) {
        facade.loadAssessmentQuestions(assessment.getId());
        
        System.out.println("Questions loaded from JSON:");
        
        if (assessment.getQuestions() != null && !assessment.getQuestions().isEmpty()) {
            for (Questions question : assessment.getQuestions()) {
                System.out.println(question.getQuestionText()); 
            }
        } else {
            System.out.println("No questions available for this assessment.");
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