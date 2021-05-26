package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.io.Serializable;

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
    private SignatureAlgorithm encryptionAlgorithm;
    /**
     * The final key
     */
    private String key;

    protected PartKey() {
        this.encryptionAlgorithm = this.keygeneratorService.getAnEncryptionAlgorithm();
    }
}
