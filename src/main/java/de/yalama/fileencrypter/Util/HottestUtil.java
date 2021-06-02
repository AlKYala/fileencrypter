package de.yalama.fileencrypter.Util;

import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.Key.Parent;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HottestUtil {
    //hottests
    public static void testSimpleEncryptDecrypt() throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        Parent testParent = new Parent();
        testParent.encryptAndStoreValue("AMOGAS");
        System.out.println(testParent.decrypt());
    }

    public static void testEncryptionAndStoringAndLoadingAndDecrypting() throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, KeyPairNotFoundException, IOException, InsecureExtractionException, ClassNotFoundException {
        Parent testParent = new Parent();
        testParent.encryptAndStoreValue("AMOGAS", 4);
        testParent.extractAll("map", "parent.file");
        testParent.loadKeyMap("map.map");
        System.out.println(testParent.decrypt());
    }

    public static void storeAndLoadAndStoreParentToo() throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, KeyPairNotFoundException, IOException, InsecureExtractionException, ClassNotFoundException {
        Parent testParent = new Parent();
        testParent.encryptAndStoreValue(new byte[] {'A', 'M', 'O', 'G', 'U', 'S'}, '4');
        testParent.extractAll("map", "parent.file");
        testParent = Parent.loadParent("parent.file");
        testParent.loadKeyMap("map.map");
        System.out.println(testParent.decrypt());
    }
}
