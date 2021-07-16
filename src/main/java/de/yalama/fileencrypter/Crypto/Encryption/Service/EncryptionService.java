package de.yalama.fileencrypter.Crypto.Encryption.Service;


import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class EncryptionService {

    public String[][] encryptAndGetBase64Values(String base64, String fileName, String fileExtension, double partLength) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, ClassNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, KeyPairNotFoundException, InsecureExtractionException {
        Parent p = new Parent();
        p.encryptBase64AndStore(base64, fileName, fileExtension, partLength);
        String base64Encoded = p.getBase64();
        return new String[][]{{base64Encoded, fileName, fileExtension},
                {p.getKeyPairsOfChildrenAsBase64(), "map", "map"}, p.sanitizeAndGetInstanceBase64()};
    }
}
