package de.yalama.fileencrypter.Util;

import java.io.File;
import java.io.FileInputStream;

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

}