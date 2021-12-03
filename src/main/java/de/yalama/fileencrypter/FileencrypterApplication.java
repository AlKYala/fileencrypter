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

    public static void main(String[] args) {
        SpringApplication.run(FileencrypterApplication.class, args);
    }
}
