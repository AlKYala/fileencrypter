package de.yalama.fileencrypter.Crypto.Encryption.Controller;

import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
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
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/encrypt")
@RequiredArgsConstructor
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;


    @CrossOrigin(origins =  "http://localhost:8080")
    @PostMapping("/singlebase64")
    public String[][] downloadAndEncryptBase64(@RequestBody Base64File info) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, KeyPairNotFoundException, ClassNotFoundException, InsecureExtractionException {
        return encryptionService.encryptAndGetBase64Values(info.getBase64(), info.getFileName(), info.getFileExtension(), 50000d);
    }
}