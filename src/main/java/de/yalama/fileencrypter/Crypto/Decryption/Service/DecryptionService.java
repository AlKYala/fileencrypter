package de.yalama.fileencrypter.Crypto.Decryption.Service;

import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Util.FileUtil;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class DecryptionService {
    private File decryptFiles(String mapFilePath, String contentFilePath) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        Parent p = new Parent();
        p.setFileExtension(FileUtil.getExtensionFromFullFileName(contentFilePath));
        p.loadKeyMap(mapFilePath);
        p.decryptAndWriteToFile(contentFilePath);
        return new File(String.format("%s.%s", contentFilePath, p.getFileExtension()));
    }
}
