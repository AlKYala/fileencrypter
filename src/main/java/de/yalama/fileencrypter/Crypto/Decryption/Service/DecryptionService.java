package de.yalama.fileencrypter.Crypto.Decryption.Service;

import com.fasterxml.jackson.databind.ser.Serializers;
import de.yalama.fileencrypter.Crypto.Data.Model.Base64File;
import de.yalama.fileencrypter.Crypto.Data.Model.Child;
import de.yalama.fileencrypter.Crypto.Data.Model.ExtendedBase64File;
import de.yalama.fileencrypter.Crypto.Data.Model.Parent;
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
    public Base64File decryptEncryptedBase64File(ExtendedBase64File extendedBase64File) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException {
        byte[] mapByteArr = ByteUtil.base64ToByteArr(extendedBase64File.getKey().getBase64());
        Map<Integer, Key> keyMap = ByteUtil.byteArrToKeyMap(mapByteArr);

        byte[] parentByteArr = ByteUtil.base64ToByteArr(extendedBase64File.getParent().getBase64());
        Parent p = (Parent) ByteUtil.byteArrToObject(parentByteArr);
        p.loadKeyMap(keyMap);
        p.assignBase64ToChildren(extendedBase64File.getContent().getBase64());
        return new Base64File(p.decryptAndGetBase64(), extendedBase64File.getContent().getFileName(), extendedBase64File.getContent().getFileExtension());
    }
}
