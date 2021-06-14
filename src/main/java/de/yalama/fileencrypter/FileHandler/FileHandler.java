package de.yalama.fileencrypter.FileHandler;

import java.io.*;

public class FileHandler {
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
}