package de.yalama.fileencrypter.Encrypter.FileHandler;

import de.yalama.fileencrypter.Util.FileUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;

@Setter
@Getter
@NoArgsConstructor
public class FileHandler {

    private String fileExtension;

    //TODO input file - via REST call?

    public byte[] fileToByteArr(File file, String fileExtension) {
        this.fileExtension = fileExtension;
        return FileUtil.fileToByteArr(file);
    }

    public void generateFile(byte[] bArr, String fileName, String fileExtension) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        try {
            fileOutputStream = new FileOutputStream(String.format("%s.%s", fileName, fileExtension));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.write(bArr);
            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch(Exception e) {
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
}