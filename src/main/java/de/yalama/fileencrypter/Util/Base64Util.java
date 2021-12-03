package de.yalama.fileencrypter.Util;

import java.util.Base64;

public class Base64Util {
    public static String byteArrToBase64(byte[] arr) {
        return new String(Base64.getEncoder().encode(arr));
    }
}
