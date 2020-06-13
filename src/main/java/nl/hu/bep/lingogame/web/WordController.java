package nl.hu.bep.lingogame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hu.bep.lingogame.persistence.model.WordList;
import nl.hu.bep.lingogame.persistence.repo.WordRepository;
import nl.hu.bep.lingogame.service.GuessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        ObjectNode response = mapper.createObjectNode();

        WordList wordList = new WordList(wordRepository.findAll());
        String word = wordList.getRandomWord(length);
        System.out.println("Game started with word: " + word);

        response.put("word", word);
        return response;
    }

    @PostMapping("/guess")
    public ArrayNode guessWord(@RequestBody String json) throws JsonProcessingException {
        ObjectNode body = mapper.readValue(json, ObjectNode.class);

        GuessService guessService = new GuessService(body.get("word").textValue());
        String[][] guessResult = guessService.checkGuess(body.get("guess").textValue());
        System.out.println("Word: " + body.get("word").textValue());
        System.out.println("Guess: " + mapper.valueToTree(guessResult));

        return mapper.valueToTree(guessResult);
    }

}
