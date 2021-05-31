package de.yalama.fileencrypter.Encrypter.Key;

import de.yalama.fileencrypter.Encrypter.FileHandler.FileHandler;
import de.yalama.fileencrypter.Util.FileUtil;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Getter
@Setter
public class Parent implements Serializable {

    private List<Child> children;
    private KeyPairGenerator generator;
    private FileHandler fileHandler;

    public Parent() throws NoSuchAlgorithmException {
        this.children = new ArrayList<Child>();
        this.generator = KeyPairGenerator.getInstance("RSA");
        this.generator.initialize(2048);
    }

    public void encryptFileAndStore(File file, double partLength) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] fileAsByte = FileUtil.fileToByteArr(file);
        this.encryptAndStoreValue(fileAsByte, partLength);
    }

    public void encryptFileAndStore(File file) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        this.encryptFileAndStore(file, 5000d);
    }

    public void encryptAndStoreValue(byte[] value, double partLength) throws IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException {
        Child child = new Child(this.generator);
        int sum = 0;
        while(sum < value.length) {
            int lengthToEncrypt = (int) (Math.random() * partLength);
            lengthToEncrypt = (sum + lengthToEncrypt > value.length) ? value.length - (sum) : lengthToEncrypt;
            byte[] partToEncrypt = Arrays.copyOfRange(value, sum, sum+lengthToEncrypt);

            child.encryptAndStore(partToEncrypt);
            this.children.add(child);
            child = new Child(this.generator);
            sum += lengthToEncrypt;
        }
    }

    public void encryptAndStoreValue(byte[] value) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        this.encryptAndStoreValue(value, 5000d);
    }

    public void encryptAndStoreValue(String value, double partLength) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        Child child = new Child(this.generator);
        int sum = 0;
        while(sum < value.length()) {
            int howMuchOfTheValueIsEncrypted = (int) (Math.random() * partLength);
            howMuchOfTheValueIsEncrypted = (sum+howMuchOfTheValueIsEncrypted > value.length()) ? value.length()-(sum) : howMuchOfTheValueIsEncrypted;

            String subStringToEncrypt = value.substring(sum, sum+howMuchOfTheValueIsEncrypted);
            child.encryptAndStore(subStringToEncrypt);
            this.children.add(child);
            child = new Child(this.generator);
            sum += howMuchOfTheValueIsEncrypted;
        }
    }

    public void encryptAndStoreValue(String value) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        this.encryptAndStoreValue(value, 5000d);
    }

    private Map<Integer, KeyPair> extractKeyPairOfChildren() throws KeyPairNotFoundException {
        Map<Integer, KeyPair> keyPairMap = new HashMap<Integer, KeyPair>();
        for(int i = 0; i < children.size(); i++) {
            if(children.get(i).getKeyPair() == null) {
                throw new KeyPairNotFoundException("Keypair of child not found - probably deleted already");
            }
            keyPairMap.put(i, children.get(i).getKeyPair());
        }
        return keyPairMap;
    }

    private void setKeyPairOfChildrenToNull() {
        for(Child c: children) {
            c.removeKeyPair();
        }
    }

    private void extractKeyMap(String path) {
        //TODO Keypair to file then remove keypairs in children
    }

    private void extractParent() {
        //TODO extract keypair then Parent
    }

    private String decrypt() throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        StringBuilder sb = new StringBuilder();
        for(Child child : children) {
            sb.append(child.decrypt());
        }
        return sb.toString();
    }
}

/**
 * VERY IMPORTANT: WHEN EXPORTING PARENT, THE KEYPAIR OF CHILDREN IS SET NULL!!!
 * A MAP OF KEYPAIRS IS SAVED SEPERATELY TO A FILE WITH CONTENT
 * <Integer, Keypair> - the key says which child has what keypair!
 *
 * When writing to a file, first the keypair is extracted then the parent
 */