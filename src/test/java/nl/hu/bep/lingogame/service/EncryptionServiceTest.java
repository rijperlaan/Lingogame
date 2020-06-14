package nl.hu.bep.lingogame.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EncryptionServiceTest {

    @Test
    void testEncrypt() {
        String string = "kaasboer";
        String encrypted = EncryptionService.encrypt(string);

        assertNotEquals(string, encrypted);
    }

    @Test
    void testDecrypt() {
        String string = "kaasboer";
        String encrypted = EncryptionService.encrypt(string);

        assertEquals(string, EncryptionService.decrypt(encrypted));
    }
}