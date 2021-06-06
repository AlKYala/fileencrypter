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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@SpringBootApplication
public class FileencrypterApplication {

    //just testing here
    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, KeyPairNotFoundException, IOException, InsecureExtractionException, ClassNotFoundException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        /*SpringApplication.run(FileencrypterApplication.class, args);
        HottestUtil.testSimpleEncryptDecrypt();
        HottestUtil.storeAndLoadAndStoreParentToo();
        HottestUtil.loadFromFileEncryptDecryptWriteBackToFile("bild.png", "png");*/
        //HottestUtil.loadFileAndEncryptToBase64("Speicher.mp4");
        //HottestUtil.readFileAndWriteBase64("Speicher.mp4", "output.txt");
        //HottestUtil.fileToBase64EncryptDecrypt("bild.png", "ausgabe.txt");
        //System.exit(0);
        Parent p = new Parent();
        p.encryptAndStoreValue("Textt", 50);
    }
}
