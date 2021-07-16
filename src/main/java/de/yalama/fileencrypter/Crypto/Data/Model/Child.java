package de.yalama.fileencrypter.Crypto.Data.Model;

import de.yalama.fileencrypter.Crypto.Key.Model.Key;
import de.yalama.fileencrypter.Util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

/**
 * The idea behind this is that one Parent has n children that each hold data of the encrypted data
 * They have different keys to decrypt the information
 * Package private used
 */
@Setter
@Getter
public class Child implements Serializable {

    private String encryptedPart;
    private Key key;
    private int encryptedLength;

    public Child() {
        this.key = new Key();
    }

    /**
     * Method to run the encryption in a child instance:
     * 1. calls methods to generate initialization vector and secretKey
     * 2. Saves a Key instance based on values from 1.
     * 3. Encrypts content with key from 2. and AES-128, saves it as instance member
     *
     * @param toEncrypt The part that has to be encrypted
     * @throws Exception in place of: NoSuchAlgorithmException, InvalidAlgorithmParameterException,
     *                   InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException,
     *                   InvalidKeySpecException, IOException, ClassNotFoundException
     */
    public void encryptAndStore(String toEncrypt) throws Exception {
        IvParameterSpec spec = CryptoUtil.generateInitializationVector();
        SecretKey sKey = CryptoUtil.generateKey(toEncrypt, this.generateRandomSalt(toEncrypt));
        this.key.setData(sKey, spec);
        this.encryptContent(toEncrypt, "AES/CBC/PKCS5Padding");
    }

    /**
     * Generates salt for encryption based on the part-string it has to encrypt
     *
     * @param toEncrypt the part this child has to encrypt
     * @return random generated salt
     */
    private String generateRandomSalt(String toEncrypt) {
        return String.format("%dAMOGUS%d%d%s", this.hashCode(), "random".hashCode(),
                this.hashCode() + this.key.hashCode(), toEncrypt);
    }

    /**
     * Encrypts the string to store in this instance (in Encrypted form)
     * assigns member variables the values
     *
     * @param toEncrypt The string to encrypt in this instance
     * @param algorithm The encryption algorithm
     * @throws Exception in place of: NoSuchAlgorithmException, InvalidAlgorithmParameterException,
     *                   InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException,
     *                   InvalidKeySpecException, IOException, ClassNotFoundException
     */
    private void encryptContent(String toEncrypt, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.key.getSecretKey(), this.key.getIvParameterSpec());
        this.encryptedPart = Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes()));
        this.encryptedLength = this.encryptedPart.length();
    }

    /**
     * sets the key instance null for safety reasons
     */
    public void clearKey() {
        this.key = null;
    }

    /**
     * Decrypts the encrypted String saved before
     * Decryption only happens after contents are loaded and values are assigned
     *
     * @return returns the decrypted (original) string
     * @throws Exception in place of: IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
     *                   NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
     *                   IOException, ClassNotFoundException
     */
    public String decrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.key.getSecretKey(), this.key.getIvParameterSpec());
        return new String(cipher.doFinal(Base64.getDecoder().decode(this.encryptedPart)));
    }

    /**
     * only used for debug purposes
     */
    public String toString() {
        return String.format("Encrypted part: %s\nKey: %s\nEncrypted length: %d",
                this.encryptedPart,
                this.key.toString(),
                this.encryptedLength);
    }
}
