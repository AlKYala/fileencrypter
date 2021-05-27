package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

//@Getter
@Setter
@RequiredArgsConstructor
public class Encrypter implements Serializable {
    private String[] encryptionInformation;
    private byte[] key;
    private Cipher cipher;
    private SecretKey cipherKey;

    public Encrypter(String[] encryptionMethodInformation, String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.encryptionInformation = encryptionMethodInformation;
        this.key = key.getBytes();
        this.cipher = Cipher.getInstance(encryptionInformation[1]);
        this.cipherKey = KeyGenerator.getInstance(encryptionInformation[0]).generateKey();
    }

    public byte[] encrypt(String toEncrypt) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, this.cipherKey);
        return cipher.getIV();
    }

    public String decrypt(byte[] toDecrypt) {
        //TODO
    }
}
