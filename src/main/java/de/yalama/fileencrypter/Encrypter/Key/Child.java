package de.yalama.fileencrypter.Encrypter.Key;

import com.nimbusds.oauth2.sdk.auth.Secret;
import de.yalama.fileencrypter.Util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

//@Getter

/**
 * The idea behind this is that one Parent has n children that each hold data of the encrypted data
 * They have different keys to decrypt the information
 * Package private used
 */
@Setter
@Getter
public class Child implements Serializable {

    private KeyPair keyPair;
    private String encryptedPart;
    //TODO encrypt these two
    private IvParameterSpec spec;
    private SecretKey key;

    public Child(KeyPairGenerator kpGenerator) {
        this.setKeyPair(kpGenerator.generateKeyPair());
    }

    private String generateRandomSalt(String toEncrypt) {
        return String.format("%dAMOGUS%d%d%s", this.hashCode(), "random".hashCode(),
                this.hashCode() + keyPair.hashCode(), toEncrypt);
    }

    public void encryptAndStore(String toEncrypt) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        this.spec = CryptoUtil.generateInitializationVector();
        this.key = CryptoUtil.generateKey(toEncrypt, this.generateRandomSalt(toEncrypt));

        this.encryptContent(toEncrypt, "AES/CBC/PKCS5Padding");
        //TODO store key
    }

    private void encryptContent(String toEncrypt, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.key, this.spec);
        this.encryptedPart = Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes()));
        //debug
        System.out.printf("%s\n%s\n%s", toEncrypt, encryptedPart, this.decrypt());
    }

    private int getRandomLengthForKey(int length) {
        return (int) Math.random() * length;
    }

    /* Deprecated

    private SecretKey generateKey(String toEncrypt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec pbeSpec = new PBEKeySpec(toEncrypt.toCharArray(), this.generateRandomSalt().getBytes(),
                this.getRandomNumberOfIterations(), 512);
        return keyFactory.generateSecret(pbeSpec);
    }*/

    private void encryptKeyAndStore(SecretKey key) {
        
    }

    /**
     * TODO: Encrypt key, decrypt key, decrypt content
     */

    public String decrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        //TODO
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.key, this.spec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(this.encryptedPart)));
    }

    void removeKeyPair() {
        this.keyPair = null;
    }

    /* DEPRECATED

    public void encryptAndStore(String toEncrypt) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        this.encryptedPart = CryptoUtil.encrypt(toEncrypt, keyPair.getPublic());
    }

    public void encryptAndStore(byte[] toEnCrypt) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        this.encryptedPart = CryptoUtil.encrypt(toEnCrypt, keyPair.getPublic());
    }

*/
}
