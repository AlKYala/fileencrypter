package de.yalama.fileencrypter.Encrypter.FileHandler;

import de.yalama.fileencrypter.Encrypter.Key.Parent;
import de.yalama.fileencrypter.Util.FileUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public void generateFile(byte[] bArr, String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        try {
            fileOutputStream.write(bArr);
            fileOutputStream.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
