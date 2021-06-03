package de.yalama.fileencrypter.Util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class FileUtil {

    public static byte[] fileToByteArr(File file) {
        byte[] fileInBytes = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileInBytes);
            fileInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fileInBytes;
    }

    public static String fileToBase64String(File file) {
        byte[] fileInByteArr = FileUtil.fileToByteArr(file);
        return Base64.getEncoder().encodeToString(fileInByteArr);
    }
}