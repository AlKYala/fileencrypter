package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

@Getter
@Setter
/**
 * In this project files are
 */
public class PartKey implements Serializable {

    /**
     * The Algorithm that sits on top of the part encrypted
     * key
     */
    private String[] encryptionInformation;
    /**
     * The final key
     */
    private String encryptedPart;

    private String key;

    private Encrypter encrypter;

    //package Private
    PartKey(String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.encryptionInformation = KeySelector.getRandomEncryptionAlgorithm();
        this.setKey(key);
        this.initEncrypter(this.encryptionInformation, this.key);
    }

    private void setKey(String keyToUse) {
        int lengthToUse = (keyToUse.length() < 100) ? keyToUse.length() : 100;
        this.key = String.format("Amogus%sSus%d", keyToUse.substring(0, lengthToUse), keyToUse.hashCode());
    }

    private void initEncrypter(String[] encryptionInformation, String key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.encrypter = new Encrypter(encryptionInformation, key);
    }

    public void encryptAndStore(String toEncrypt) {

    }

    public String decrypt() {
        //TODO
        return "";
    }
}
