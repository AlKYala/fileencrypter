package de.yalama.fileencrypter.Crypto.Decryption.Controller;

import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
import de.yalama.fileencrypter.Crypto.Data.Model.ExtendedBase64File;
import de.yalama.fileencrypter.Crypto.Decryption.Service.DecryptionService;
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

@RestController
@RequestMapping("/decrypt")
@RequiredArgsConstructor
public class DecryptionController {

    @Autowired
    private DecryptionService decryptionService;

    @CrossOrigin(origins =  "http://localhost:8080")
    @PostMapping("/bundle")
    public Base64File decrypt(@RequestBody ExtendedBase64File extendedBase64File) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, IOException, BadPaddingException, NoSuchPaddingException, InvalidAlgorithmParameterException, ClassNotFoundException {
        //debug
        System.out.println(extendedBase64File.getContent().getBase64().length());
        return this.decryptionService.decryptEncryptedBase64File(extendedBase64File);
    }
}
