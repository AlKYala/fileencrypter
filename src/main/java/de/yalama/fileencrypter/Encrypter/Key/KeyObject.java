package de.yalama.fileencrypter.Encrypter.Key;

import de.yalama.fileencrypter.Util.ByteUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;

@NoArgsConstructor
public class KeyObject implements Serializable {

    private byte[] secretKey;
    private byte[] ivParameterSpec;

    public void setSecretKey(SecretKey secretKey) throws IOException {
        this.secretKey = ByteUtil.secretKeyToByteArr(secretKey);
    }

    public void setIvParameterSpec(IvParameterSpec ivParameterSpec) throws IOException {
        this.ivParameterSpec = ByteUtil.ivParameterSpecToByteArr(ivParameterSpec);
    }

    public SecretKey getSecretKey() throws IOException, ClassNotFoundException {
        return ByteUtil.byteArrToSecretKey(this.secretKey);
    }

    public IvParameterSpec getIvParameterSpec() throws IOException, ClassNotFoundException {
        return ByteUtil.byteArrToIvParameteSpec(this.ivParameterSpec);
    }
}
