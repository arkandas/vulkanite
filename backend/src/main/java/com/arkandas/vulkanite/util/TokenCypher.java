package com.arkandas.vulkanite.util;

import com.arkandas.vulkanite.errors.InternalErrorException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Instant;

public class TokenCypher {

    public static String GenerateToken(String username, Instant expirationDate) throws InternalErrorException {
        try {
            MessageDigest m = MessageDigest.getInstance("SHA1");
            m.reset();
            m.update((username + expirationDate.toString()).getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String tokenStr = bigInt.toString(16);
            while (tokenStr.length() < 32) {
                tokenStr = "0" + tokenStr;
            }
            return tokenStr;
        } catch (Exception e) {
            throw new InternalErrorException(e.toString());
        }
    }
}