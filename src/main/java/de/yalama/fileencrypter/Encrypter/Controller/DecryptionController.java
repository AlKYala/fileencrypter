package de.yalama.fileencrypter.Encrypter.Controller;

import de.yalama.fileencrypter.Encrypter.Content.Parent;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyLockedException;
import de.yalama.fileencrypter.Util.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/decrypt")
public class DecryptionController {

    //TODO schnittstelle nach außen

    private File decryptFiles(String mapFilePath, String contentFilePath) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, KeyLockedException, NoSuchPaddingException, InvalidKeyException {
        Parent p = new Parent();
        p.setFileExtension(FileUtil.getExtensionFromFullFileName(contentFilePath));
        p.loadKeyMap(mapFilePath);
        p.decryptAndWriteToFile(contentFilePath);
        return new File(String.format("%s.%s", contentFilePath, p.getFileExtension()));
    }
}
