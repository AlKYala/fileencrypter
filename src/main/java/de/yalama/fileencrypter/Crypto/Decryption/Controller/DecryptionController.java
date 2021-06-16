package de.yalama.fileencrypter.Crypto.Decryption.Controller;

import de.yalama.fileencrypter.Crypto.Decryption.Service.DecryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decrypt")
@RequiredArgsConstructor
public class DecryptionController {

    @Autowired
    private DecryptionService decryptionService;


}
