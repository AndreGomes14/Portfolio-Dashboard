package com.InvestmentsTracker.investment_portfolio.util;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom(); // SecureRandom instance
        byte[] keyBytes = new byte[64]; // 64 bytes for strong key
        secureRandom.nextBytes(keyBytes); // Fill keyBytes with random data
        String secretKey = Base64.getEncoder().encodeToString(keyBytes); // Encode as base64 string

        System.out.println("Your JWT Secret Key: " + secretKey);
    }
}
