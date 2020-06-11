package nl.hu.bep.lingogame.persistence.model;

import java.util.ArrayList;
import java.util.List;

public class WordList {
    private final List<String> words;
    private final List<String> fiveLetter;
    private final List<String> sixLetter;
    private final List<String> sevenLetter;

    public WordList(Iterable<Word> in) {
        words = new ArrayList<>();
        fiveLetter = new ArrayList<>();
        sixLetter = new ArrayList<>();
        sevenLetter = new ArrayList<>();

        for (Word word : in) {
            String value = word.getWord();
            words.add(value);
            switch (value.length()) {
                case 5: fiveLetter.add(value); break;
                case 6: sixLetter.add(value); break;
                case 7: sevenLetter.add(value); break;
            }
        }
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getWords(int length) {
        switch (length) {
            case 5: return fiveLetter;
            case 6: return sixLetter;
            case 7: return sevenLetter;
            default: return new ArrayList<>();
        }
    }
}
