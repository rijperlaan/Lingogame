package nl.hu.bep.lingogame.service;

import java.util.Arrays;
import java.util.List;

public class GuessService {
    private String word;

    public GuessService(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[][] checkGuess(String guess) {
        String[] wordArray = word.split("");
        String[] guessArray = guess.split("");
        String[][] result = new String[guess.length()][2];

        for (int i = 0; i < guess.length(); i++) {
            result[i][0] = guessArray[i];
            if (guessArray[i].equals(wordArray[i])) {
                result[i][1] = "correct";
                guessArray[i] = "";
                wordArray[i] = "";
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (!guessArray[i].isEmpty()) {
                List<String> wordArrayAsList = Arrays.asList(wordArray);
                if (wordArrayAsList.contains(guessArray[i])) {
                    result[i][1] = "present";
                    wordArray[wordArrayAsList.indexOf(guessArray[i])] = "";
                    guessArray[i] = "";
                } else {
                    result[i][1] = "absent";
                }
            }
        }

        return result;
    }
}
