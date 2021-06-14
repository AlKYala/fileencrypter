package de.yalama.fileencrypter.Crypto.Encryption.Service;

import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.FileHandler.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
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


@RequiredArgsConstructor
@Service
public class EncryptionService {

    /**
     * Prototype, this isnt the final way to do things but
     * @param file the file to encrypt
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     * and the second the encrypted content itself
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ResponseEntity<Resource>> encrypt(MultipartFile file) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        //TODO
        return null;
    }

    /**
     * A method to handle
     * @param file The file to encrypt
     * @param partLength The file to encrypt is split in to many parts - this parameter specifies how long
     *                   each part should be
     * @return An array of files (by default of size 2) where the first index holds the key for the file
     *      * and the second the encrypted content itself
     */
    private File[] encryptFile(File file, double partLength) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InsecureExtractionException, KeyPairNotFoundException {
        Parent p = new Parent();
        p.encryptFileAndStore(file, partLength);
        p.extractAll("map", "encrypted.file");
        String[] filePaths = new String[] {"map.map", "encrypted.file"};
        return FileHandler.wrapFiles(filePaths);
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
