package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import javax.crypto.NoSuchPaddingException;

//@Getter
@Setter
public class Child implements Serializable {

    private KeyPair keyPair;
    private String encryptedPart;

    public Child(KeyPairGenerator kpGenerator) {
        this.setKeyPair(kpGenerator.generateKeyPair());
    }

    private void encrypt() {

    }
}
