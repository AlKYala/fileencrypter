package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class KeyObject implements Serializable {
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;
}
