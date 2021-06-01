package de.yalama.fileencrypter.Encrypter.Key;

import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.FileHandler.FileHandler;
import de.yalama.fileencrypter.Util.FileUtil;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.util.*;

@Getter
@Setter
public class Parent implements Serializable {

    /**
     * The generator can be singleton because we dont need n instances of KeyPairGenerator - only one per runtime
     * KeyPairGenerator is not serializable so this is a good strategy that saves some memory
     */
    private static KeyPairGenerator generator;
    private List<Child> children;
    private FileHandler fileHandler;

    public Parent() throws NoSuchAlgorithmException {
        this.children = new ArrayList<Child>();
        this.initKeyPairGenerator();
    }

    private void initKeyPairGenerator() throws NoSuchAlgorithmException {
        if(Parent.generator == null) {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
        }
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

    /**
     * first extracts the keypairMap to a seperate file, then clears the keyPair from all children
     * for safety reasons then extracts the parent
     * @param mapFileName The filename for the map
     * @param parentFileName The filename for the parent
     */
    public void extractAll(String mapFileName, String parentFileName) throws IOException, KeyPairNotFoundException, InsecureExtractionException {
        this.extractKeyMap(mapFileName);
        this.clearKeyPairsOfChildren();
        this.extractParent(parentFileName);
    }

    /**
     * Method to extract this instance but checks if this instance children has no keypair saved for
     * safety reasons
     * @param fileName the filename for this instance
     * @throws IOException
     */
    private void extractParent(String fileName) throws IOException, InsecureExtractionException {
        if(!this.checkChildrenAllClear()) {
            throw new InsecureExtractionException("This instance has children with keypair instances left. Cannot extract for safety reasons");
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if all children have no keyPair left. Important for extraction of Parent instance
     * @return false if one child with not null keyPair member exists
     */
    //TODO set private again
    public boolean checkChildrenAllClear() {
        for(Child c : children) {
            if(c.getKeyPair() != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a map where keyPair Objects are indexed by what Index the corresponding child object is saved in Parent::children
     * @return a Map<Integer, KeyMap> objected implemented in HashMap, see description.
     * @throws KeyPairNotFoundException thrown when a child object with no keyPair is found - which only happens if a child object keyMap is cleared
     */
    private Map<Integer, KeyPair> getKeyPairOfChildren() throws KeyPairNotFoundException {
        Map<Integer, KeyPair> keyPairMap = new HashMap<Integer, KeyPair>();
        for(int i = 0; i < children.size(); i++) {
            if(children.get(i).getKeyPair() == null) {
                throw new KeyPairNotFoundException("Keypair of child not found - probably deleted already");
            }
            keyPairMap.put(i, children.get(i).getKeyPair());
        }
        return keyPairMap;
    }

    private void clearKeyPairsOfChildren() {
        for(Child c: children) {
            c.removeKeyPair();
        }
    }

    /**
     * Extracts the keyMap specified in Parent::getPairOfChildren to a File
     * @param fileName The filename
     * @throws IOException
     * @throws KeyPairNotFoundException
     */
    private void extractKeyMap(String fileName) throws IOException, KeyPairNotFoundException {
        FileOutputStream fileOutputStream = null;
        Map<Integer, KeyPair> childrenKeyPair = this.getKeyPairOfChildren();
        try {
            fileOutputStream = new FileOutputStream(String.format("%s.map", fileName));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(childrenKeyPair);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadKeyMap(String filenameWithExtension) throws IOException, ClassNotFoundException {
        File keyMapFile = new File(filenameWithExtension);
        this.loadKeyMapData(keyMapFile);
    }

    /**
     * Method to load the keyMapFile and assign the child instances in Parent::children their instances
     * @param keyMapFile
     */
    private void loadKeyMapData(File keyMapFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(keyMapFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Map<Integer, KeyPair> keyPairMap = (Map<Integer, KeyPair>) objectInputStream.readObject();
        //TODO check if this actually works
        this.feedValuesToChildren(keyPairMap);
    }

    private void feedValuesToChildren(Map<Integer, KeyPair> map) {
        for(Integer index : map.keySet()) {
            this.children.get(index).setKeyPair(map.get(index));
        }
    }

    public String decrypt() throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
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
