package nl.hu.bep.lingogame.service;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptionService {
    private static final char[] KEY = "random-array-for-bep".toCharArray();

    public static String encrypt(String word) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(KEY);
        return textEncryptor.encrypt(word);
    }

    public static String decrypt(String word) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(KEY);
        return textEncryptor.decrypt(word);
    }

}
