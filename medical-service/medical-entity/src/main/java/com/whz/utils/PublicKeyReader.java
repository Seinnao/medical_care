package com.whz.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author 文辉正
 * @since 2023/4/4 23:17
 */
public class PublicKeyReader {

    public static RSAPublicKey getPublicKey(String publicKeyString) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static RSAPublicKey getPublicKeyByObject(Object publicKey) {
        String publicKeyString =(String) publicKey;
        return getPublicKey(publicKeyString);
    }
}
