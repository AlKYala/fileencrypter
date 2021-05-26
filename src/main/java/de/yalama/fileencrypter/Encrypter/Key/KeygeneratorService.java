package de.yalama.fileencrypter.Encrypter.Key;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class KeygeneratorService {




    public SignatureAlgorithm getAnEncryptionAlgorithm() {
        int num = (int) (Math.random() * 7d);
        switch(num) {
            case 0: return SignatureAlgorithm.ES256;
            case 1: return SignatureAlgorithm.ES384;
            case 2: return SignatureAlgorithm.ES512;
            case 3: return SignatureAlgorithm.PS256;
            case 4: return SignatureAlgorithm.PS384;
            case 5: return SignatureAlgorithm.PS512;
            case 6: return SignatureAlgorithm.RS256;
            case 7: return SignatureAlgorithm.RS384;
            default: return SignatureAlgorithm.RS512;
        }
    }
}
