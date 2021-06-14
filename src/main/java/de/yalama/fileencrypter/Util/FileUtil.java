package de.yalama.fileencrypter.Util;

import org.springframework.web.multipart.MultipartFile;

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
        //System.out.println(Arrays.toString(split));
        return (split.length < 2) ? ".txt" : split[split.length-1];
    }

    public static String[] getFileNameAndExtensionFromFullFileName(String fullFileName) {
        return fullFileName.split("[.]");
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

    /**
     * Helps trigger downloads in the controller by taking
     * filepaths, making files of them and returning an array of them
     * @param paths The file paths where the files are located
     * @return The files
     */
    public static File[] wrapFiles(String[] paths) {
        File[] files = new File[paths.length];
        for(int i = 0; i < paths.length; i++) {
            files[i] = new File(paths[i]);
        }
        return files;
    }

    public static byte[] multipartFileToByteArr(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}