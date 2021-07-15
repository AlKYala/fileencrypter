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
    private Key key;
    private int encryptedLength;

    public Child() {
        this.key = new Key();
    }

    private String generateRandomSalt(String toEncrypt) {
        return String.format("%dAMOGUS%d%d%s", this.hashCode(), "random".hashCode(),
                this.hashCode() + this.key.hashCode(), toEncrypt);
    }

    public void encryptAndStore(String toEncrypt) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, IOException, ClassNotFoundException {
        IvParameterSpec spec = CryptoUtil.generateInitializationVector();
        SecretKey sKey = CryptoUtil.generateKey(toEncrypt, this.generateRandomSalt(toEncrypt));
        this.key.setData(sKey, spec);
        this.encryptContent(toEncrypt, "AES/CBC/PKCS5Padding");
    }

    private void encryptContent(String toEncrypt, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, ClassNotFoundException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.key.getSecretKey(), this.key.getIvParameterSpec());
        this.encryptedPart = Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes()));
        this.encryptedLength = this.encryptedPart.length();
    }

    public void clearKey() {
        this.key = null;
    }

    public String decrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {
        //debug
        System.out.println(this.encryptedPart);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.key.getSecretKey(), this.key.getIvParameterSpec());
        //TODO: LAST UNIT DOES NOT HAVE ENOUGH BITS!
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
