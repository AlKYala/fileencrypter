package de.yalama.fileencrypter.Encrypter.FileHandler;

import de.yalama.fileencrypter.Encrypter.Key.Parent;
import de.yalama.fileencrypter.Util.FileUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Setter
@Getter
@NoArgsConstructor
public class FileHandler {

    private String fileExtension;

    public byte[] fileToByteArr(File file, String fileExtension) {
        this.fileExtension = fileExtension;
        return FileUtil.fileToByteArr(file);
    }
}
