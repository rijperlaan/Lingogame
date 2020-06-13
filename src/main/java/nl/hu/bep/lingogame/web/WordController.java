package nl.hu.bep.lingogame.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hu.bep.lingogame.persistence.model.WordList;
import nl.hu.bep.lingogame.persistence.repo.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/words")
public class WordController {
    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public ObjectNode getWord() {
        ObjectNode node = mapper.createObjectNode();

        WordList wordList = new WordList(wordRepository.findAll());
        String word = wordList.getRandomWord();
        System.out.println("Game started with word: " + word);

        node.put("word", word);
        return node;
    }

    @GetMapping("/{length}")
    public ObjectNode getWordByLength(@PathVariable int length) {
        ObjectNode node = mapper.createObjectNode();

        WordList wordList = new WordList(wordRepository.findAll());
        String word = wordList.getRandomWord(length);
        System.out.println("Game started with word: " + word);

        node.put("word", word);
        return node;    }

}
