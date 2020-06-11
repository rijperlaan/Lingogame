package nl.hu.bep.lingogame.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "words")
public class Word {

    @Id
    private final String word;

    public Word() {
        word = "";
    }

    public Word(String s) {
        word = s;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word;
    }
}
