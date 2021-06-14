package de.yalama.fileencrypter;

import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
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
        SpringApplication.run(FileencrypterApplication.class, args);
        /*Parent p = new Parent();
        p.encryptFileAndStore("jjwt-0.9.1.jar", 500000);
        p.extractAll("map", "testObject");
        p.loadKeyMap("map.map");
        p.decryptAndWriteToFile("test");*/
    }
}
