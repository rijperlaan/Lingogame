package nl.hu.bep.lingogame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.hu.bep.lingogame.persistence.model.Word;
import nl.hu.bep.lingogame.persistence.repo.WordRepository;
import nl.hu.bep.lingogame.service.EncryptionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WordControllerTest {

    @Autowired
    private WordController controller;

    @MockBean
    private WordRepository wordRepository;


    @Test
    void testGetWord() {
        List<Word> list = new ArrayList<>();
        list.add(new Word("leter"));
        list.add(new Word("kaaser"));

        Mockito.when(wordRepository.findAll()).thenReturn(list);
        String out = EncryptionService.decrypt(controller.getWordByLength(5).get("word").textValue());
        assertEquals("leter", out);

        out = EncryptionService.decrypt(controller.getWordByLength(6).get("word").textValue());
        assertEquals("kaaser", out);

        Mockito.verify(wordRepository, Mockito.times(2)).findAll();
    }

    @Test
    void testGuess() {
        assertThrows(JsonProcessingException.class, () -> {
            controller.guessWord("x");
        });
    }

    @Test
    public void contexLoads() throws Exception {
        assertNotNull(controller);
    }
}