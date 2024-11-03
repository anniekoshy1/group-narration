package com.narration;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DifficultyTest {

    @Test
    public void enumValues_ContainsAllDifficultyLevels() {
        Difficulty[] levels = Difficulty.values();
        assertEquals(3, levels.length);
        assertArrayEquals(new Difficulty[]{Difficulty.RUDIMENTARY, Difficulty.INTERMEDIATE, Difficulty.ADVANCED}, levels);
    }

    @Test
    public void valueOf_ReturnsCorrectEnumConstantForValidName() {
        assertEquals(Difficulty.RUDIMENTARY, Difficulty.valueOf("RUDIMENTARY"));
        assertEquals(Difficulty.INTERMEDIATE, Difficulty.valueOf("INTERMEDIATE"));
        assertEquals(Difficulty.ADVANCED, Difficulty.valueOf("ADVANCED"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOf_ThrowsExceptionForInvalidName() {
        Difficulty.valueOf("BEGINNER");
    }
}