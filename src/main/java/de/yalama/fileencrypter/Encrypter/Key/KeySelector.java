package de.yalama.fileencrypter.Encrypter.Key;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Service;

public class KeySelector {

    public static String[] getRandomEncryptionAlgorithm() {
        int num = (int) (Math.random() * 6d);
        switch(num) {
            case 0: return new String[] {"DES", "DES/CBC/NoPadding"};
            case 1: return new String[] {"DESede", "DESede/ECB/PKCS5Padding"};
            case 2: return new String[] {"AES", "AES/GCM/NoPadding"};
            case 3: return new String[] {"AES", "AES/ECB/PKCS5Padding"};
            case 4: return new String[] {"DESede", "DESede/ECB/NoPadding"};
            case 5: return new String[] {"DESede", "DESede/CBC/NoPadding"};
            default: return new String[] {"AES","AES/CBC/PKCS5Padding" };
        }
    }
}
