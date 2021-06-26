package de.yalama.fileencrypter.Crypto.Data.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Setter
@Getter
public class Base64File {
    private String base64;
    private String fileName;
    private String fileExtension;

    public String toString() {
        return String.format("base64: %s\nfile name: %s\nfile extension: %s", this.base64, this.fileName, this.fileExtension);
    }
}
