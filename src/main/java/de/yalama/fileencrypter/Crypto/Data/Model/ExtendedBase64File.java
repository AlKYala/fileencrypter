package de.yalama.fileencrypter.Crypto.Data.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * Used to transmit encrypted files easier from FrontEnd to Backend!
 */
@Getter
@Setter
public class ExtendedBase64File {
    private Base64File content;
    private Base64File key;
    private Base64File parent;
}
