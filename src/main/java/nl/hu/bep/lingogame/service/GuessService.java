package nl.hu.bep.lingogame.service;

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
        String[][] result = new String[guess.length()][2];

        for (int i = 0; i < guess.length(); i++) {
            String guessLetter = guess.substring(i, i + 1);
            result[i][0] = guessLetter;
            if (guessLetter.equals(wordArray[i])) {
                result[i][1] = "correct";
            } else if (word.contains(guessLetter)) {
                result[i][1] = "exists";
            } else {
                result[i][1] = "wrong";
            }
        }

        return result;
    }
}
