package nl.hu.bep.lingogame.web;

import nl.hu.bep.lingogame.persistence.model.WordList;
import nl.hu.bep.lingogame.persistence.repo.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordController {
    @Autowired
    private WordRepository wordRepository;

    @GetMapping
    public List<String> findAll() {
        WordList wordList = new WordList(wordRepository.findAll());
        return wordList.getWords();
    }

    @GetMapping("/{length}")
    public List<String> findByLength(@PathVariable int length) {
        WordList wordList = new WordList(wordRepository.findAll());
        return wordList.getWords(length);
    }

}
