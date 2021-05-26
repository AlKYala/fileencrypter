package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.io.Serializable;
import javax.crypto.Cipher;
import F

@Getter
@Setter
/**
 * In this project files are
 */
public class PartKey implements Serializable {

    @Autowired
    private KeygeneratorService keygeneratorService;

    /**
     * The Algorithm that sits on top of the part encrypted
     * key
     */
    private String encryptionAlgorithm;
    /**
     * The final key
     */
    private String encryptedPart;

    private String key;

    protected PartKey() {
        this.encryptionAlgorithm = this.keygeneratorService.getAnEncryptionAlgorithm();
    }

    public void encryptAndStore(String partKey) {
        int finalIndex = (partKey.length()-1 > 100) ? 100 : partKey.length()-1;
        this.key = String.format("Amogus%sSus%d", partKey.substring(0, finalIndex), partKey.hashCode());


    }

    public String decrypt() {
        //TODO
        return "";
    }
}
