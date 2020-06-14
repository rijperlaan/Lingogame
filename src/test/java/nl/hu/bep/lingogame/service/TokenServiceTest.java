package nl.hu.bep.lingogame.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TokenServiceTest {

    @Test
    void testCreatToken() {
        assertNotEquals(TokenService.createToken(), TokenService.createToken());
    }
}