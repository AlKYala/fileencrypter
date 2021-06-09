package de.yalama.fileencrypter.Util;

import java.io.*;

/**
 * This is just a collection of methods used in the main method to test a few things quickly
 */
public class HottestUtil {
    public static void loadFileAndEncryptToBase64(String fileName) {
        File file = new File(fileName);
        System.out.println(FileUtil.fileToBase64String(file));
    }

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
}
