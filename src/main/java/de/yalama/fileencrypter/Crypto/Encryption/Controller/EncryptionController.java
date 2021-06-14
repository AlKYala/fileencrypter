package de.yalama.fileencrypter.Crypto.Encryption.Controller;

import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Crypto.Encryption.Service.EncryptionService;
import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping("/encrypt")
public class EncryptionController {

    private EncryptionService encryptionService;

    @Autowired
    public EncryptionController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    /**
     * Prototype, this isnt the final way to do things but
     * @param file the file to encrypt
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     * and the second the encrypted content itself
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ResponseEntity<Resource>> downloadAndEncrypt(@RequestParam MultipartFile file) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        this.encryptionService.encrypt(file);
        //TODO now the files are saved on the disk - upload them to client or find better way
        return null;
    }

    /**
     * trigger upload so client downloads files
     * @param toUpload the array of files to download (usually 2)
     */
    public void triggerUpload(File[] toUpload) {

    }

    //TODO - this has to trigger download!
    /*private List<ResponseEntity<Resource>> encryptFile(byte[] fileInByteArr, double partLength, String fileName) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, ClassNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        String[] names = fileName.split("[.]");
        Parent p = new Parent();
        p.encryptBase64AndStore(Base64Util.byteArrToBase64(fileInByteArr), names[0], names[1], 5000000);
        List<ResponseEntity<Resource>> ret = new ArrayList<ResponseEntity<Resource>>();
        ret.add(this.triggerDownload(p.getBase64().getBytes(), fileName, p.getFileExtension()));
        //TODO download map
        return ret;
    }*/

    /**
     * for encrypted files base64 -> byte[] to generate File
     * @return
     */
    /*
    private ResponseEntity<Resource> triggerDownload(byte[] content, String fileName, String fileExtension) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s.%s", fileName, fileExtension))
                .body(new ByteArrayResource(content));
    }*/

    /*public void getFile(String fileName, String fileExtension) {
        try {
            InputStream is = new FileInputStream(String.format("%s.%s", fileName, fileExtension));
            //TODO eine HttpServletResponse
            HttpServletResponse response = new HttpServletResponseWrapper();
            org.apache.commons.io.IOUtils.copy(is, )
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }*/
}
