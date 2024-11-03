package com.narration;

import com.narration.Course;
import com.narration.Lesson;
import com.narration.Assessment;
import com.narration.FlashcardQuestion;
import com.narration.Language;
import com.narration.ProjectUI;
import com.narration.CourseList;
import com.narration.DataLoader;
import com.narration.DataWriter;
import com.narration.Difficulty;
import com.narration.LanguageList;
import com.narration.LanguageLearningFacade;
import com.narration.Phrase;
import com.narration.Questions;
import com.narration.TrueFalseQuestion;
import com.narration.PhraseList;
import com.narration.User;
import com.narration.UserList;
import com.narration.Word;
import com.narration.WordsList;


import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

public class CourseTest {

    private String name;
    private String description;
    private boolean userAccess;
    private double courseProgress;
    private ArrayList<Lesson> lessons;
    private ArrayList<Assessment> assessments;
    private ArrayList<String> keyWords;
    private UUID id;
    private boolean completed;
    private ArrayList<String> completedAssessments;
    private FlashcardQuestion flashcard;
    private Lesson lesson;

    @Before
public void setUp() {
    // Initialize your variables
    id = UUID.randomUUID(); // Generates a new random UUID for testing
    name = "Test Course";
    description = "This is a test course for unit testing.";
    userAccess = true;
    courseProgress = 0.0;
    completed = false;
    // Initialize lessons, assessments, and completed assessments
    lessons = new ArrayList<>();
    assessments = new ArrayList<>();
    completedAssessments = new ArrayList<>();
    keyWords = new ArrayList<>();
}

@After
public void tearDown() {
    // Clean up the test variables
    id = null;
    name = null;
    description = null;
    userAccess = false;
    courseProgress = 0.0;
    completed = false;
    lessons = null;
    assessments = null;
    completedAssessments = null;
    keyWords = null;
}



}