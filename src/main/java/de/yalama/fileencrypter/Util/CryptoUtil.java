package de.yalama.fileencrypter.Util;

import javax.crypto.*;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoUtil implements Serializable {

    //https://www.baeldung.com/java-aes-encryption-decryption

    /**
     * Generates a psuedo-Random key from the string to encode and the passed salt
     * @param str The string to encode
     * @param salt The salt
     * @return A SecretKey instance
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey generateKey(String str, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     * generates a pseudo-random initializaton vector from 16 bytes
     * @return an IVParameterSpec instance
     */
    public static IvParameterSpec generateInitializationVector() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
