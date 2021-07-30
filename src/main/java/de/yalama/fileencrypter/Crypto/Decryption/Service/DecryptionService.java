package de.yalama.fileencrypter.Crypto.Decryption.Service;

import com.fasterxml.jackson.databind.ser.Serializers;
import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
import de.yalama.fileencrypter.Crypto.Data.Model.Child;
import de.yalama.fileencrypter.Crypto.Data.Model.ExtendedBase64File;
import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
import de.yalama.fileencrypter.Crypto.Key.Model.DataFrame;
import de.yalama.fileencrypter.Crypto.Key.Model.Key;
import de.yalama.fileencrypter.Util.ByteUtil;
import de.yalama.fileencrypter.Util.FileUtil;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class DecryptionService {

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
    public Base64File decryptEncryptedBase64File(ExtendedBase64File extendedBase64File) throws Exception {
        byte[] dataFrameByteArr = ByteUtil.base64ToByteArr(extendedBase64File.getFrame().getBase64());
        DataFrame frame = (DataFrame) ByteUtil.byteArrToObject(dataFrameByteArr);

        byte[] mapByteArr = ByteUtil.base64ToByteArr(frame.getMapBase64());
        Map<Integer, Key> keyMap = ByteUtil.byteArrToKeyMap(mapByteArr);

        byte[] parentByteArr = ByteUtil.base64ToByteArr(frame.getParentBase64());
        Parent p = (Parent) ByteUtil.byteArrToObject(parentByteArr);
        p.loadKeyMap(keyMap);
        p.assignBase64ToChildren(extendedBase64File.getContent().getBase64());
        return new Base64File(p.decryptAndGetBase64(), extendedBase64File.getContent().getFileName(), extendedBase64File.getContent().getFileExtension());
    }
}
