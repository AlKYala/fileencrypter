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
import java.util.Base64;
import java.util.List;

@Service
public class EncryptionService {

    public String[][] encrypt(MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, InsecureExtractionException, KeyPairNotFoundException, InvalidKeySpecException, IllegalBlockSizeException, ClassNotFoundException {
        String[] names = FileUtil.getFileNameAndExtensionFromFullFileName(file.getOriginalFilename());
        //debug
        System.out.printf("%s \n%s\n", names[0], names[1]);
        return this.encryptAndGetBase64Values(Base64.getEncoder().encodeToString(file.getBytes()), names[0], names[1], 5000000d);
    }

    /**
     * A method to handle
     * @param file The file to encrypt
     * @param partLength The file to encrypt is split in to many parts - this parameter specifies how long
     *                   each part should be
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     * and the second the encrypted content itself
     */
    public void encrypt(File file, String fileName, String fileExtension, double partLength) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        this.encrypt(FileUtil.fileToBase64String(file), fileName, fileExtension, partLength);
    }

    public void encrypt(byte[] arr, String fileName, String fileExtension, double partLength) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, KeyPairNotFoundException, ClassNotFoundException, InsecureExtractionException {
        this.encrypt(Base64Util.byteArrToBase64(arr), fileName, fileExtension, partLength);
    }

    public void encrypt(String base64, String fileName, String fileExtension, double partLength) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, ClassNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InsecureExtractionException, KeyPairNotFoundException {
        Parent p = new Parent();
        p.encryptBase64AndStore(base64, fileName, fileExtension, partLength);
        p.extractAll("map", "encrypted.file");
    }

    public String[][] encryptAndGetBase64Values(String base64, String fileName, String fileExtension, double partLength) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, ClassNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, KeyPairNotFoundException, InsecureExtractionException {
        Parent p = new Parent();
        p.encryptBase64AndStore(base64, fileName, fileExtension, partLength);
        return new String[][]{{p.getBase64(), fileName, fileExtension},
                {p.getKeyPairsOfChildrenAsBase64(), "map", "map"}};
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

/**
 * TODO find a better way to handle files for upload - right now: write files to drive, then offer as download
 */
