package de.yalama.fileencrypter.Crypto.Encryption.Controller;

import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
import de.yalama.fileencrypter.Crypto.Encryption.Service.EncryptionService;
import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/encrypt")
@RequiredArgsConstructor
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    /**
     * @param info Takes data for a Base64File object, encrypts it
     * @return A 2d String consisting of data for a file (in front end)
     * @throws Exception in place of IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
     *                   InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
     *                   BadPaddingException, KeyPairNotFoundException,
     *                   ClassNotFoundException, InsecureExtractionException
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/singlebase64")
    public String[][] downloadAndEncryptBase64(@RequestBody Base64File info) throws Exception {
        return encryptionService.encryptAndGetBase64Values(info.getBase64(), info.getFileName(),
                info.getFileExtension(), 50000d);
    }
}