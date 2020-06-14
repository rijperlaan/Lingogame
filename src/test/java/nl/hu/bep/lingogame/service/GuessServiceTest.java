package nl.hu.bep.lingogame.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessServiceTest {

    @Test
    void testConstructor() {
        GuessService guessService = new GuessService("test");
        assertEquals("test", guessService.getWord());
    }

    @Test
    void testSetter() {
        GuessService guessService = new GuessService("test");
        guessService.setWord("kaas");
        assertEquals("kaas", guessService.getWord());
    }

    @Test
    void testCheckGuess() {
        GuessService guessService = new GuessService("testop");
        String[][] result = guessService.checkGuess("tsxott");
        assertEquals("t", result[0][0]);
        assertEquals("s", result[1][0]);
        assertEquals("x", result[2][0]);
        assertEquals("o", result[3][0]);
        assertEquals("t", result[4][0]);
        assertEquals("t", result[5][0]);

        assertEquals("correct", result[0][1]);
        assertEquals("present", result[1][1]);
        assertEquals("absent", result[2][1]);
        assertEquals("present", result[3][1]);
        assertEquals("present", result[4][1]);
        assertEquals("absent", result[5][1]);
    }
}