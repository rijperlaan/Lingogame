package nl.hu.bep.lingogame.persistence.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordListTest {

    @Test
    void testConstructor() {
        List<Word> words = new ArrayList<>();
        WordList wordList = new WordList(words);
        assertTrue(wordList.getWords().isEmpty());

        words.add(new Word("pizza"));
        assertTrue(wordList.getWords().isEmpty());

        wordList = new WordList(words);
        assertFalse(wordList.getWords().isEmpty());
    }

    @Test
    void testGetWordsWithLength() {
        List<Word> words = new ArrayList<>();
        // 5 letters
        words.add(new Word("dagje"));
        words.add(new Word("daken"));
        // 6 letters
        words.add(new Word("aanbod"));
        // 7 letters
        words.add(new Word("vlotter"));
        words.add(new Word("vuisten"));
        words.add(new Word("namaken"));

        WordList wordList = new WordList(words);
        assertEquals(6, wordList.getWords().size());
        assertEquals(0, wordList.getWords(4).size());
        assertEquals(0, wordList.getWords(8).size());
        assertEquals(2, wordList.getWords(5).size());
        assertEquals(1, wordList.getWords(6).size());
        assertEquals(3, wordList.getWords(7).size());
    }

    @Test
    void testGetRandomWordWithLength() {
        List<Word> words = new ArrayList<>();
        words.add(new Word("daken"));
        words.add(new Word("dakens"));
        WordList wordList = new WordList(words);

        assertEquals("daken", wordList.getRandomWord(5));
        assertEquals("dakens", wordList.getRandomWord(6));
        assertEquals("", wordList.getRandomWord(-1));
        assertEquals("", wordList.getRandomWord(8));
        assertEquals("", wordList.getRandomWord(7));
    }

    @Test
    void testGetRandomWord() {
        List<Word> words = new ArrayList<>();
        WordList wordList = new WordList(words);

        assertEquals("", wordList.getRandomWord());

        words.add(new Word("daken"));
        wordList = new WordList(words);

        assertEquals("daken", wordList.getRandomWord());
    }
}