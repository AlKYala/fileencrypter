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

    private String encryptedPart;
    //TODO encrypt these two
    private KeyObject key;

    public Child() {
        this.key = new KeyObject();
    }

    private String generateRandomSalt(String toEncrypt) {
        return String.format("%dAMOGUS%d%d%s", this.hashCode(), "random".hashCode(),
                this.hashCode() + this.key.hashCode(), toEncrypt);
    }

    public void encryptAndStore(String toEncrypt) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        IvParameterSpec spec = CryptoUtil.generateInitializationVector();
        SecretKey sKey = CryptoUtil.generateKey(toEncrypt, this.generateRandomSalt(toEncrypt));
        this.key.setIvParameterSpec(spec);
        this.key.setSecretKey(sKey);
        this.encryptContent(toEncrypt, "AES/CBC/PKCS5Padding");
        //TODO store key
    }

    private void encryptContent(String toEncrypt, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.key.getSecretKey(), this.key.getIvParameterSpec());
        this.encryptedPart = Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes()));
        //debug
        System.out.printf("%s\n%s\n%s", toEncrypt, encryptedPart, this.decrypt());
    }

    private int getRandomLengthForKey(int length) {
        return (int) Math.random() * length;
    }

    private void encryptKeyAndStore(SecretKey key) {
        
    }

    public void clearKeyObject() {
        this.key = null;
    }

    /**
     * TODO: Auslagern iv und spec in eine eigene datei
     */

    public String decrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        //TODO
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.key.getSecretKey(), this.key.getIvParameterSpec());
        return new String(cipher.doFinal(Base64.getDecoder().decode(this.encryptedPart)));
    }
}
