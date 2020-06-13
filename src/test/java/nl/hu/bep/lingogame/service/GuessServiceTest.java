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
        GuessService guessService = new GuessService("test");
        String[][] result = guessService.checkGuess("ttex");
        assertEquals("t", result[0][0]);
        assertEquals("t", result[1][0]);
        assertEquals("e", result[2][0]);
        assertEquals("x", result[3][0]);

        assertEquals("correct", result[0][1]);
        assertEquals("exists", result[1][1]);
        assertEquals("exists", result[2][1]);
        assertEquals("wrong", result[3][1]);
    }
}