package de.yalama.fileencrypter.Crypto.Key.Model;

import de.yalama.fileencrypter.Util.Base64Util;
import de.yalama.fileencrypter.Util.ByteUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * The idea is to bundle Parent and Map in one instance to make storing the "frame" data easier
 * It just stores the base64 encodings
 */

@Getter
@Setter
public class DataFrame {
    private String parentBase64;
    private String mapBase64;

    public DataFrame(String parentBase64, String mapBase64) {
        this.parentBase64 = parentBase64;
        this.mapBase64 = mapBase64;
    }

    public String getBase64() throws IOException {
        byte[] dataFrameAsBase64 = ByteUtil.anyObjectToByteArr(this);
        return Base64Util.byteArrToBase64(dataFrameAsBase64);
    }
}