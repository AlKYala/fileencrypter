package de.yalama.fileencrypter.Encrypter.Controller;

import de.yalama.fileencrypter.Encrypter.Content.Parent;
import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.FileHandler.FileHandler;
import de.yalama.fileencrypter.Util.Base64Util;
import de.yalama.fileencrypter.Util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/encrypt")
@RequiredArgsConstructor
public class EncryptionController {

    /**
     * Prototype, this isnt the final way to do things but
     * @param file the file to encrypt
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     * and the second the encrypted content itself
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public File[] encrypt(MultipartFile file) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        return this.encryptFile(file.getBytes(), 50000000, file.getOriginalFilename());
    }

    /**
     * A method to handle
     * @param file The file to encrypt
     * @param partLength The file to encrypt is split in to many parts - this parameter specifies how long
     *                   each part should be
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     *      * and the second the encrypted content itself
     */
    private File[] encryptFile(File file, double partLength) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        Parent p = new Parent();
        p.encryptFileAndStore(file, partLength);
        p.extractAll("map", "encrypted.file");
        String[] filePaths = new String[] {"map.map", "encrypted.file"};
        return FileHandler.wrapFiles(filePaths);
    }
    //TODO
    private File[] encryptFile(byte[] fileInByteArr, double partLength, String fileName) throws NoSuchAlgorithmException {
        String[] names = fileName.split("[.]");
        Parent p = new Parent();
        p.encryptBase64AndStore(Base64Util.byteArrToBase64(fileInByteArr), names[0], names[1], 5000000);
        //TODO see Parent.java
    }

}
