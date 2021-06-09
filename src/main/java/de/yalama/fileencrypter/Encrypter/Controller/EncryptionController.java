package de.yalama.fileencrypter.Encrypter.Controller;

import de.yalama.fileencrypter.Encrypter.Content.Parent;
import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyLockedException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.FileHandler.FileHandler;
import de.yalama.fileencrypter.Util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/crypter")
public class EncryptionController {

    /**
     * Prototype, this isnt the final way to do things but
     * @param file the file to encrypt
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     * and the second the encrypted content itself
     */
    @GetMapping("/encrypt")
    public File[] encrypt(@RequestBody File file) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, KeyLockedException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        return this.encryptFile(file, 5000000);
    }

    /**
     * A method to handle
     * @param file The file to encrypt
     * @param partLength The file to encrypt is split in to many parts - this parameter specifies how long
     *                   each part should be
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     *      * and the second the encrypted content itself
     */
    private File[] encryptFile(File file, double partLength) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, KeyLockedException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        Parent p = new Parent();
        p.encryptFileAndStore(file, partLength);
        p.extractAll("map", "encrypted.file");
        String[] filePaths = new String[] {"map.map", "encrypted.file"};
        return FileHandler.wrapFiles(filePaths);
    }

    private File decryptFiles(String mapFilePath, String contentFilePath) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, KeyLockedException, NoSuchPaddingException, InvalidKeyException {
        Parent p = new Parent();
        p.setFileExtension(FileUtil.getExtensionFromFullFileName(contentFilePath));
        p.loadKeyMap(mapFilePath);
        p.decryptAndWriteToFile(contentFilePath);
        return new File(String.format("%s.%s", contentFilePath, p.getFileExtension()));
    }
}
