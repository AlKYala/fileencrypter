package de.yalama.fileencrypter.Crypto.Encryption.Controller;

import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Crypto.Encryption.Service.EncryptionService;
import de.yalama.fileencrypter.Exceptions.FileNameException;
import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Util.FileUtil;
import de.yalama.fileencrypter.Util.ZipUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/encrypt")
@RequiredArgsConstructor
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    /**
     * Prototype, this isnt the final way to do things but
     * @param file the file to encrypt
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     * and the second the encrypted content itself
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String[] downloadAndEncrypt(@RequestParam MultipartFile file) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException, FileNameException {
        this.encryptionService.encrypt(file);
        //TODO just return an Array of Strings -
        //hardcoded for testing purposes
        String[][] fileNames = new String[][] {new String[] {"parent", "file"}, new String[] {"map", "map"}};
        //TODO
    }
}

//TODO in later versions - if multiple files are uploaded zip them together first then encrypt the zip
//note - pass base64 to FrontEnd, let front end handle it.