package de.yalama.fileencrypter;

import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.Key.Parent;
import de.yalama.fileencrypter.Util.HottestUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class FileencrypterApplication {

    //just testing here
    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, KeyPairNotFoundException, IOException, InsecureExtractionException, ClassNotFoundException {
        /*SpringApplication.run(FileencrypterApplication.class, args);
        HottestUtil.testSimpleEncryptDecrypt();
        HottestUtil.storeAndLoadAndStoreParentToo();
        HottestUtil.loadFromFileEncryptDecryptWriteBackToFile("bild.png", "png");*/
        HottestUtil.loadFileAndEncryptToBase64("bild.png");
        System.exit(0);
    }
}
