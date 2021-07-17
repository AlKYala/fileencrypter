package de.yalama.fileencrypter.Util;

import de.yalama.fileencrypter.Crypto.Key.Model.Key;
import org.apache.commons.io.IOUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Base64;
import java.util.Map;

/**
 * This Util class is used to handle Initialization Vectors and Secret keys and the serialization of them
 * In this project, both SecretKeys and IVs have to be split into bytes before written to a file
 * and when the files are loaded, the bytes are loaded and cast into the an instance of specified class
 */
public class ByteUtil {
    /**
     * Credit: https://stackoverflow.com/questions/10730929/cast-an-object-to-bytearray
     * Takes a SecretKey instance, converts it into a byte Array
     * @param key The secretKey instance
     * @return the byte array that holds the bytes of the secretKey instance
     * @throws IOException
     */
    public static byte[] secretKeyToByteArr(SecretKey key) throws IOException {
        return ByteUtil.anyObjectToByteArr(key);
    }

    /**
     * Takes a byte Array and if it holds data for a SecretKey instance it takes the bytes and
     * generates a secretKey instance from it
     * @param secretKeyInByteArr The byte array, ideally bytes of a secretKey instance
     * @return A SecretKey instance
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static SecretKey byteArrToSecretKey(byte[] secretKeyInByteArr) throws IOException, ClassNotFoundException {
        return (SecretKey) ByteUtil.byteArrToObject(secretKeyInByteArr);
    }

    /**
     * Takes an IvParameterSpec instance and returns a byte array holding the bytes of that instance
     * @param iv the initialzatiion vector parameter spec instance
     * @return the byte array that has the iv's bytes
     * @throws IOException
     */
    public static byte[] ivParameterSpecToByteArr(IvParameterSpec iv) throws IOException {
        return iv.getIV();
    }

    /**
     * Takes a byte array that holds the bytes of an IvParameterSpec instance and returns a IvParameterSpec
     * instance from it
     * @param ivParameterSpecByteArr A byte array, ideally the bytes of a ivParamterSpec instance
     * @return an IvParameterSpec instance
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static IvParameterSpec byteArrToIvParameterSpec(byte[] ivParameterSpecByteArr) throws IOException, ClassNotFoundException {
        return new IvParameterSpec(ivParameterSpecByteArr);
    }

    /**
     * @param map A Map<E, T> instance with E = Integer.class and
     *            T = de.yalama.fileencrypter.Crypto.Key.Model.Key.class
     * @return returns returns the passed map as byte array
     * @throws IOException
     */
    public static byte[] keyMapToByteArr(Map<Integer, Key> map) throws IOException {
        return ByteUtil.anyObjectToByteArr(map);
    }

    /**
     * Takes the bytes of a Map with specified parameters and returns it as instance
     * @param mapAsByteArr The bytes of a Map<E, T> instance with E = Integer.class and
     *                     T = de.yalama.fileencrypter.Crypto.Key.Model.Key.class
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Map<Integer, Key> byteArrToKeyMap(byte[] mapAsByteArr) throws IOException, ClassNotFoundException {
        return (Map<Integer, Key>) ByteUtil.byteArrToObject(mapAsByteArr);
    }

    /**
     * Takes any object and returns the bytes of that object
     * @param object The object passed
     * @return The bytes of that object
     * @throws IOException
     */
    public static byte[] anyObjectToByteArr(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        bos.close();
        oos.close();
        return bos.toByteArray();
    }

    /**
     * Takes a byte Array and generates an Object from it
     * @param arr the byte array
     * @return An Object made from the bytes passed
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object byteArrToObject(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object ret = ois.readObject();
        ois.close();
        bis.close();
        return ret;
    }

    /**
     * Takes a base64 String and returns it in byte
     * @param base64 The string
     * @return The base64 string encoded as a byte array
     */
    public static byte[] base64ToByteArr(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
