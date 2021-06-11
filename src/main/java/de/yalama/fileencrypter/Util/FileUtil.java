package de.yalama.fileencrypter.Util;

import java.io.*;
import java.util.Arrays;
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

    public static void writeFilePlainText(String content, String fileName, String fileExtension) throws IOException {
        FileWriter file = new FileWriter(String.format("%s.%s", fileName, fileExtension));
        BufferedWriter bw = new BufferedWriter(file);
        try {
            bw.write(content);
            bw.close();
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExtensionFromFullFileName(String fullFileName) {
        String[] split = fullFileName.split("[.]");
        //debug
        System.out.println(Arrays.toString(split));
        return (split.length < 2) ? ".txt" : split[split.length-1];
    }

    public static String getExtensionFromFullFileName(File file) {
        return FileUtil.getExtensionFromFullFileName(file.getAbsolutePath());
    }

    static void byteArrToFile(byte[] arr, String fileName) {
       FileOutputStream fileOutputStream = null;
       try {
           fileOutputStream = new FileOutputStream(fileName);
           fileOutputStream.write(arr);
           fileOutputStream.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public static void anyObjectToFile(Object obj, String fileName, String fileExtension) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(String.format("%s.%s", fileName, fileExtension));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}