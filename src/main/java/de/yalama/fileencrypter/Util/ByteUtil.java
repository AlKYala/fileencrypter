package de.yalama.fileencrypter.Util;

import de.yalama.fileencrypter.Crypto.Key.Model.Key;
import org.apache.commons.io.IOUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Map;

public class ByteUtil {
    //https://stackoverflow.com/questions/10730929/cast-an-object-to-bytearray
    public static byte[] secretKeyToByteArr(SecretKey key) throws IOException {
        return ByteUtil.anyObjectToByteArr(key);
    }

    public static SecretKey byteArrToSecretKey(byte[] secretKeyInByteArr) throws IOException, ClassNotFoundException {
        return (SecretKey) ByteUtil.byteArrToObject(secretKeyInByteArr);
    }

    public static byte[] ivParameterSpecToByteArr(IvParameterSpec iv) throws IOException {
        return iv.getIV();
    }

    public static IvParameterSpec byteArrToIvParameterSpec(byte[] ivParameterSpecByteArr) throws IOException, ClassNotFoundException {
        return new IvParameterSpec(ivParameterSpecByteArr);
    }

    public static byte[] keyMapToByteArr(Map<Integer, Key> map) throws IOException {
        return ByteUtil.anyObjectToByteArr(map);
    }

    public static Map<Integer, Key> byteArrToKeyMap(byte[] mapAsByteArr) throws IOException, ClassNotFoundException {
        return (Map<Integer, Key>) ByteUtil.byteArrToObject(mapAsByteArr);
    }

    public static byte[] anyObjectToByteArr(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        bos.close();
        oos.close();
        return bos.toByteArray();
    }

    public static Object byteArrToObject(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object ret = ois.readObject();
        ois.close();
        bis.close();
        return ret;
    }
}
