package nl.hu.bep.lingogame.persistence.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    @Test
    void testConstructor() {
        Word word = new Word("kaneel");
        assertEquals("kaneel", word.getWord());
        word = new Word();
        assertTrue(word.getWord().isEmpty());
    }

    @Test
    void testToString() {
        Word word = new Word("kaneel");
        assertEquals("kaneel", word.toString());
    }
}