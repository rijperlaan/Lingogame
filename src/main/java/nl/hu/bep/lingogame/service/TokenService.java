package nl.hu.bep.lingogame.service;

import java.util.UUID;

public class TokenService {
    public static String createToken() {
        UUID token = UUID.randomUUID();
        return token.toString();
    }
}
