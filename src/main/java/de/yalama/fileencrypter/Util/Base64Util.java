package de.yalama.fileencrypter.Util;

import java.util.Base64;

public class Base64Util {

    public static void base64StringToFile(String base64Encoded, String fileName, String extension) {
        byte[] content = Base64.getDecoder().decode(base64Encoded);
        FileUtil.byteArrToFile(content, String.format("%s.%s", fileName, extension));
    }

    public static String byteArrToBase64(byte[] arr) {
        return new String(Base64.getEncoder().encode(arr));
    }
}
