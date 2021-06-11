package de.yalama.fileencrypter.Encrypter.Key;

import de.yalama.fileencrypter.Util.ByteUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.LockedException;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;

@NoArgsConstructor
public class Key implements Serializable {

    private byte[] secretKey;
    private byte[] ivParameterSpec;

    public void setData (SecretKey secretKey, IvParameterSpec ivParameterSpec) throws IOException {
        this.setSecretKey(secretKey);
        this.setIvParameterSpec(ivParameterSpec);
    }

    private void setSecretKey(SecretKey secretKey) throws IOException {
        this.secretKey = ByteUtil.secretKeyToByteArr(secretKey);
    }

    private void setIvParameterSpec(IvParameterSpec ivParameterSpec) throws IOException {
        this.ivParameterSpec = ByteUtil.ivParameterSpecToByteArr(ivParameterSpec);
    }

    public SecretKey getSecretKey() throws IOException, ClassNotFoundException {
        return ByteUtil.byteArrToSecretKey(this.secretKey);
    }

    public IvParameterSpec getIvParameterSpec() throws IOException, ClassNotFoundException {
        return ByteUtil.byteArrToIvParameterSpec(this.ivParameterSpec);
    }
}
