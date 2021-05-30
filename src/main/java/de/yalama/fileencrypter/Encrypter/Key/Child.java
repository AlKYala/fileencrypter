package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//@Getter

/**
 * The idea behind this is that one Parent has n children that each hold data of the encrypted data
 * They have different keys to decrypt the information
 * Package private used
 */
@Setter
public class Child implements Serializable {

    private KeyPair keyPair;
    private byte[] encryptedPart;

    Child(KeyPairGenerator kpGenerator) {
        this.setKeyPair(kpGenerator.generateKeyPair());
    }

    void encryptAndStore(String toEncrypt) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        this.encryptedPart = CryptoUtil.encryptString(toEncrypt, keyPair.getPublic());
    }

    String decrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return CryptoUtil.decrypt(this.encryptedPart, this.keyPair.getPrivate());
    }
}
