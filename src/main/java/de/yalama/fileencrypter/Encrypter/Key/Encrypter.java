package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@RequiredArgsConstructor
public class Encrypter implements Serializable {
    private String encryptionMethod;
    private String key;
    private Cipher cipher;

    public Encrypter(String encryptionMethod, String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.encryptionMethod = encryptionMethod;
        this.key = key;
        this.cipher = Cipher.getInstance(encryptionMethod);
    }

    public String encrypt(String toEncrypt) {
        cipher.init(Cipher.ENCRYPT_MODE, this.key);
        //TODO
    }
}
