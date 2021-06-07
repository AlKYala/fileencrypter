package de.yalama.fileencrypter.Util;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;

public class ByteUtil {
    //https://stackoverflow.com/questions/10730929/cast-an-object-to-bytearray
    public static byte[]  secretKeyToByteArr(SecretKey key) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bytes);
        objectOutputStream.writeObject(key);
        objectOutputStream.flush();
        bytes.close();
        objectOutputStream.close();
        return bytes.toByteArray();
    }

    public static SecretKey byteArrToSecretKey(byte[] secretKeyInByteArr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(secretKeyInByteArr);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        SecretKey key = (SecretKey) objectInputStream.readObject();
        objectInputStream.close();
        inputStream.close();
        return key;
    }

    public static byte[] ivParameterSpecToByteArr(IvParameterSpec iv) throws IOException {
        return iv.getIV();
    }

    public static IvParameterSpec byteArrToIvParameterSpec(byte[] ivParameterSpecByteArr) throws IOException, ClassNotFoundException {
        return new IvParameterSpec(ivParameterSpecByteArr);
    }
}
