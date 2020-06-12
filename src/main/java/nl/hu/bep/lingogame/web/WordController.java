package nl.hu.bep.lingogame.web;

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

    @GetMapping
    public String getWord() {
        WordList wordList = new WordList(wordRepository.findAll());
        String word = wordList.getRandomWord();
        System.out.println("Game started with word: " + word);
        return word;
    }

    @GetMapping("/{length}")
    public String getWordByLength(@PathVariable int length) {
        WordList wordList = new WordList(wordRepository.findAll());
        String word = wordList.getRandomWord(length);
        System.out.println("Game started with word: " + word);
        return word;
    }

}
