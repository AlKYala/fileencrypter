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

    /**
     * Each file is recieved in base64 form which is the base for encryption
     * @param base64 The file in base64
     * @param fileName The filename of the file to encrypt
     * @param fileExtension The extension of the file to encrypt
     * @param partLength A base block size, see Parent::encryptAndStore
     * @return returns a 2-Dimensional String Array of Length 4 that returns the following
     * 1. The entire encrypted String (from its children)
     * 2. Base64 form of the map to refer the index of each child (see Parent::children) and the Key objects
     * 3. The sanitized parent in base64 (see: Parent::sanitize) for safety reasons
     * @throws Exception in place of NoSuchAlgorithmException, InvalidAlgorithmParameterException,
     * ClassNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException,
     * InvalidKeyException, InvalidKeySpecException, KeyPairNotFoundException, InsecureExtractionException
     */
    public String[][] encryptAndGetBase64Values(String base64, String fileName, String fileExtension, double partLength) throws Exception {
        Parent p = new Parent();
        p.encryptBase64AndStore(base64, fileName, fileExtension, partLength);
        String base64Encoded = p.getBase64();
        return new String[][]{{base64Encoded, fileName, fileExtension},
                {p.getKeyPairsOfChildrenAsBase64(), "map", "map"}, p.sanitizeAndGetInstanceBase64()};
    }
}
