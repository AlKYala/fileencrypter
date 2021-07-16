package de.yalama.fileencrypter.Crypto.Decryption.Controller;

import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
import de.yalama.fileencrypter.Crypto.Data.Model.ExtendedBase64File;
import de.yalama.fileencrypter.Crypto.Decryption.Service.DecryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/decrypt")
@RequiredArgsConstructor
public class DecryptionController {

    @Autowired
    private DecryptionService decryptionService;

    /**
     * Data for a extendedBase64File is uploaded through the specified endpoint, it is decrypted and returned
     * as a .zip File with all the Files inside.
     * @param extendedBase64File Input through http endpoint that has data for an Instance for
     *                           extendedBase64File
     * @return Returns a base64File that represents a .zip File that has all the files inside from the input parameter
     * @throws Exception in place ofBadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
     *                   IllegalBlockSizeException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException,
     *                   IOException
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/bundle")
    public Base64File decrypt(@RequestBody ExtendedBase64File extendedBase64File) throws Exception {
        return this.decryptionService.decryptEncryptedBase64File(extendedBase64File);
    }
}
