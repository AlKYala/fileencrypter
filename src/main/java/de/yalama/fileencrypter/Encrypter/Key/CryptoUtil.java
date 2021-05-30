package de.yalama.fileencrypter.Encrypter.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

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

    public static String decrypt(byte[] encryptedInput, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher decrypter = CryptoUtil.getRSACipher(Cipher.DECRYPT_MODE, privateKey);
        return new String(decrypter.doFinal(encryptedInput), StandardCharsets.UTF_8);
    }

    private static Cipher getRSACipher(int mode, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher crypter = Cipher.getInstance("RSA");
        crypter.init(mode, key);
        return crypter;
    }
}
