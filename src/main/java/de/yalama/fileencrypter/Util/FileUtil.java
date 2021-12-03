package de.yalama.fileencrypter.Util;

import lombok.extern.slf4j.Slf4j;
import java.io.*;

@Slf4j
public class FileUtil {
    /**
     * Takes a a file and returns it as byte array
     * @param file The file instance
     * @return the file instance represented in a byte array - the file split into bytes
     */
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