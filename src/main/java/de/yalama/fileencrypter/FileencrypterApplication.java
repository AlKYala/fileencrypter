package de.yalama.fileencrypter;

import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyLockedException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.Content.Parent;
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
    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, KeyPairNotFoundException, IOException, InsecureExtractionException, ClassNotFoundException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyLockedException {
        //SpringApplication.run(FileencrypterApplication.class, args);
        Parent p = new Parent();
        p.encryptFileAndStore("jjwt-0.9.1.jar", 500000);
        p.extractAll("map", "testObject");
        p.loadKeyMap("map.map");
        p.decryptAndWriteToFile("test");
    }
}
