package de.yalama.fileencrypter.Util;

import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.Key.Parent;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HottestUtil {
    //hottests
    /*public static void testSimpleEncryptDecrypt() throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
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
        testParent.encryptAndStoreValue(new byte[] {'A', 'M', 'O', 'G', 'U', 'S'}, 4, ".file");
        testParent.extractAll("map", "parent.file");
        testParent = Parent.loadParent("parent.file");
        testParent.loadKeyMap("map.map");
        System.out.println(testParent.decrypt());
    }

    public static void loadFromFileEncryptDecryptWriteBackToFile(String fileName, String extension) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, FileNotFoundException {
        File file = new File(fileName);
        System.out.println(file.length());
        Parent testParent = new Parent();
        testParent.encryptAndStoreValue(FileUtil.fileToByteArr(file), 244, extension);
        //TODO testParent.decryptAndWriteToFile("decrypted", extension);
    }*/

    public static void loadFileAndEncryptToBase64(String fileName) {
        File file = new File(fileName);
        System.out.println(FileUtil.fileToBase64String(file));
    }

    //only for testing purposes
    public static void writeStringToFile(String str, String fileNameWithExtension) throws IOException {
        FileWriter fileWriter = new FileWriter(fileNameWithExtension);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    public static void readFileAndWriteBase64(String inputFileNameWithExtension, String outputFileNameWithExtension) throws IOException {
        File file = new File(inputFileNameWithExtension);
        HottestUtil.writeStringToFile(FileUtil.fileToBase64String(file), outputFileNameWithExtension);
    }

    /*public static void fileToBase64EncryptDecrypt(String inputFileNameWithExtension, String outputFileNameWithExtension) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, FileNotFoundException {
        File file = new File(inputFileNameWithExtension);
        String data = FileUtil.fileToBase64String(file);
        Parent testParent = new Parent();
        testParent.encryptAndStoreValue(data, 245);
        testParent.decryptAndWriteToFile(outputFileNameWithExtension);
    }*/
}
