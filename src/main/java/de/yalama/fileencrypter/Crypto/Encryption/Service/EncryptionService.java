package de.yalama.fileencrypter.Crypto.Encryption.Service;

import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Util.Base64Util;
import de.yalama.fileencrypter.Util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * Same as EncryptionService::encryptAndGetBase64Values but it returns a list of Base64 Objects
     */
    public List<Base64File> encryptAndGetBase64ValuesBase64Files(String base64, String fileName, String fileExtension, double partLength) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, KeyPairNotFoundException, ClassNotFoundException, InsecureExtractionException {
        String[][] data = this.encryptAndGetBase64Values(base64, fileName, fileExtension, partLength);
        List<Base64File> base64Files = new ArrayList<Base64File>();
        for(String[] b64Info: data) {
            base64Files.add(new Base64File(b64Info[0], b64Info[1], b64Info[2]));
        }
        return base64Files;
    }
}
