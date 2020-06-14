package nl.hu.bep.lingogame.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.hu.bep.lingogame.persistence.model.WordList;
import nl.hu.bep.lingogame.persistence.repo.WordRepository;
import nl.hu.bep.lingogame.service.EncryptionService;
import nl.hu.bep.lingogame.service.GuessService;
import nl.hu.bep.lingogame.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/words")
public class WordController {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private ObjectMapper mapper;
    private final Map<String, Integer> session = new HashMap<>();

    @GetMapping("/{length}")
    public ObjectNode getWordByLength(@PathVariable int length) {
        ObjectNode response = mapper.createObjectNode();

        WordList wordList = new WordList(wordRepository.findAll());
        String word = wordList.getRandomWord(length);
        System.out.println("Game started: " + word);

        String sessionToken = TokenService.createToken();
        session.put(sessionToken, 0);

        response.put("word", EncryptionService.encrypt(word));
        response.put("sessionToken", sessionToken);
        response.put("firstLetter", word.substring(0, 1));
        return response;
    }

    @PostMapping("/guess")
    public ObjectNode guessWord(@RequestBody String json) throws JsonProcessingException {
        ObjectNode body = mapper.readValue(json, ObjectNode.class);
        ObjectNode response = mapper.createObjectNode();

        String word = EncryptionService.decrypt(body.get("word").textValue());
        String sessionToken = body.get("sessionToken").textValue();

        GuessService guessService = new GuessService(word);
        String[][] guessResult = guessService.checkGuess(body.get("guess").textValue());
        System.out.println("guess = " + mapper.valueToTree(guessResult));

        int attempts = session.get(sessionToken) + 1;

        if (attempts >= 5 && !guessService.isCorrect()) {
            session.remove(sessionToken);
            response.put("gameOver", true);
            response.put("answer", word);
        } else {
            session.put(sessionToken, attempts);
            response.put("gameOver", false);
        }

        response.set("guessResult", mapper.valueToTree(guessResult));
        return response;
    }

}
