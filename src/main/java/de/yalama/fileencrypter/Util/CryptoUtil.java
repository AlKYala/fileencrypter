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

    public static byte[] encrypt(String input, PublicKey publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        return CryptoUtil.encrypt(input.getBytes(StandardCharsets.UTF_8), publicKey);
    }

    public static byte[] encrypt(byte[] input, PublicKey publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher encrypter = CryptoUtil.getRSACipher(Cipher.ENCRYPT_MODE, publicKey);
        return CryptoUtil.getEncryptedBytes(input, encrypter);
    }

    private static byte[] getEncryptedBytes(byte[] inputByteArray, Cipher encrypter) throws BadPaddingException, IllegalBlockSizeException {
        return encrypter.doFinal(inputByteArray);
    }

    /*public static String decrypt(byte[] encryptedInput, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher decrypter = CryptoUtil.getRSACipher(Cipher.DECRYPT_MODE, privateKey);
        return new String(decrypter.doFinal(encryptedInput), StandardCharsets.UTF_8);
    }*/

    public static byte[] decrypt(byte[] encryptedInput, PrivateKey privateKey) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher decrypter = CryptoUtil.getRSACipher(Cipher.DECRYPT_MODE, privateKey);
        return decrypter.doFinal(encryptedInput);
    }

    private static Cipher getRSACipher(int mode, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher crypter = Cipher.getInstance("RSA");
        crypter.init(mode, key);
        return crypter;
    }

    //https://www.baeldung.com/java-aes-encryption-decryption
    public static SecretKey generateKey(String str, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    public static IvParameterSpec generateInitializationVector() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
