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
}