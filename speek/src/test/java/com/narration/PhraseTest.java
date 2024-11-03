package com.narration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PhraseTest {

    private Phrase phrase;

    @Before
    public void setUp() {
        // Initialize a Phrase object before each test
        phrase = new Phrase("¿Cómo estás?", "How are you?");
    }

    @Test
    public void testAddSynonym() {
        // Add synonyms and verify they are added
        phrase.addSynonym("Hola");
        phrase.addSynonym("Buen día");
        assertEquals(2, phrase.getSynonyms().size());
        assertTrue(phrase.getSynonyms().contains("Hola"));
        assertTrue(phrase.getSynonyms().contains("Buen día"));
    }

    @Test
    public void testAddSynonym_Duplicate() {
        // Add a duplicate synonym and verify it is not added again
        phrase.addSynonym("Hola");
        phrase.addSynonym("Hola"); // Attempt to add duplicate
        assertEquals(1, phrase.getSynonyms().size());
    }

    @Test
    public void testRemoveSynonym() {
        // Add synonyms and remove one
        phrase.addSynonym("Hola");
        phrase.addSynonym("Buen día");
        phrase.removeSynonym("Hola");
        assertEquals(1, phrase.getSynonyms().size());
        assertFalse(phrase.getSynonyms().contains("Hola"));
        assertTrue(phrase.getSynonyms().contains("Buen día"));
    }

    @Test
    public void testRemoveSynonym_NotExisting() {
        // Attempt to remove a synonym that doesn't exist
        phrase.addSynonym("Hola");
        phrase.removeSynonym("Adiós");
        assertEquals(1, phrase.getSynonyms().size());
        assertTrue(phrase.getSynonyms().contains("Hola"));
    }

    @Test
    public void testToString() {
        // Add synonyms and test the toString method
        phrase.addSynonym("Hola");
        phrase.addSynonym("Buen día");
        String expectedString = "Phrase: ¿Cómo estás?\nDefinition: How are you?\nSynonyms: [Hola, Buen día]";
        assertEquals(expectedString, phrase.toString());
    }
}
