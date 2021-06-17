package de.yalama.fileencrypter.Crypto.Data.Model;

import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Crypto.Key.Model.Key;
import de.yalama.fileencrypter.Util.Base64Util;
import de.yalama.fileencrypter.Util.ByteUtil;
import de.yalama.fileencrypter.Util.FileUtil;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Getter
@Setter
public class Parent implements Serializable {

    /**
     * The generator can be singleton because we dont need n instances of KeyPairGenerator - only one per runtime
     * KeyPairGenerator is not serializable so this is a good strategy that saves some memory
     */
    //private static KeyPairGenerator generator;
    private List<Child> children;
    //private FileHandler fileHandler;
    private String fileExtension;
    private String fileName;

    public Parent() throws NoSuchAlgorithmException {
        this.children = new ArrayList<Child>();
    }

    public static Parent loadParent(String parentPath) throws IOException, ClassNotFoundException {
        File parentFile = new File(parentPath);
        FileInputStream fileInputStream = new FileInputStream(parentFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (Parent) objectInputStream.readObject();
    }

    public void encryptAndStoreValue(String value, double partLength) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {
        Child child = new Child();
        int sum = 0;
        while(sum < value.length()) {
            int howMuchOfTheValueIsEncrypted = (int) (Math.random() * partLength);
            howMuchOfTheValueIsEncrypted = (sum+howMuchOfTheValueIsEncrypted > value.length()) ? value.length()-(sum) : howMuchOfTheValueIsEncrypted;

            String subStringToEncrypt = value.substring(sum, sum+howMuchOfTheValueIsEncrypted);
            child.encryptAndStore(subStringToEncrypt);
            this.children.add(child);
            child = new Child();
            sum += howMuchOfTheValueIsEncrypted;
            System.out.println(sum);
        }
    }

    public void encryptAndStoreValue(String value) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {
        this.encryptAndStoreValue(value, 5000d);
    }

    public void encryptBase64AndStore(String base64, String fileName, String fileExtension, double partLength) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, InvalidKeySpecException, IllegalBlockSizeException, ClassNotFoundException {
        this.encryptAndStoreValue(base64, partLength);
        this.fileExtension = fileExtension;
        this.fileName = fileName;
    }

    public void encryptFileAndStore(File file, double partLength) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, ClassNotFoundException, InvalidAlgorithmParameterException, InvalidKeySpecException, IOException {
        String fileAsBase64 = FileUtil.fileToBase64String(file);
        this.fileExtension = FileUtil.getExtensionFromFullFileName(file);
        this.encryptAndStoreValue(fileAsBase64, partLength);
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
    private boolean checkChildrenAllClear() {
        for(Child c : children) {
            if(c.getKey() != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the keyPairs of the children as Base64
     * @return the keyPairs of the children encoded in Base64: Map<Integer, Key> -> byte[] -> String
     * @throws KeyPairNotFoundException
     * @throws IOException
     */
    public String getKeyPairsOfChildrenAsBase64() throws KeyPairNotFoundException, IOException {
        Map<Integer, Key> keyPairs = this.getKeyPairsOfChildren();
        byte[] keyPairsAsByteArr = ByteUtil.keyMapToByteArr(keyPairs);
        return Base64.getEncoder().encodeToString(keyPairsAsByteArr);
    }

    /**
     * Creates a map where keyPair Objects are indexed by what Index the corresponding child object is saved in Parent::children
     * @return a Map<Integer, KeyMap> objected implemented in HashMap, see description.
     * @throws KeyPairNotFoundException thrown when a child object with no keyPair is found - which only happens if a child object keyMap is cleared
     */
    private Map<Integer, Key> getKeyPairsOfChildren() throws KeyPairNotFoundException {
        Map<Integer, Key> keyPairMap = new HashMap<Integer, Key>();
        for(int i = 0; i < children.size(); i++) {
            if(children.get(i).getKey() == null) {
                throw new KeyPairNotFoundException("Keypair of child not found - probably deleted already");
            }
            keyPairMap.put(i, children.get(i).getKey());
        }
        return keyPairMap;
    }

    private void clearKeyPairsOfChildren() {
        for(Child c: children) {
            c.clearKey();
        }
    }

    /**
     * Extracts the keyMap specified in Parent::getPairOfChildren to a File
     * @param fileName The filename
     * @throws IOException
     * @throws KeyPairNotFoundException
     */
    private void extractKeyMap(String fileName) throws IOException, KeyPairNotFoundException {
        FileUtil.anyObjectToFile(this.getKeyPairsOfChildren(), fileName, "map");
    }

    public void loadKeyMap(String mapFilePath) throws IOException, ClassNotFoundException {
        File keyMapFile = new File(mapFilePath);
        this.loadKeyMapData(keyMapFile);
    }

    /**
     * Method to load the keyMapFile and assign the child instances in Parent::children their instances
     * @param keyMapFile
     */
    private void loadKeyMapData(File keyMapFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(keyMapFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Map<Integer, Key> keyPairMap = (Map<Integer, Key>) objectInputStream.readObject();
        this.feedValuesToChildren(keyPairMap);
    }

    private void feedValuesToChildren(Map<Integer, Key> map) {
        for(Integer index : map.keySet()) {
            this.children.get(index).setKey(map.get(index));
        }
    }

    /**
     * Assembles the Encrypted parts of the children and returns the file
     * as encrypted
     * @return The completely assembled encrypted part
     */
    public String getBase64() {
        StringBuilder sb = new StringBuilder();
        for(Child c : this.children) {
            sb.append(c.getEncryptedPart());
        }
        return sb.toString();
    }

    public String decryptAndGetBase64() throws BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IOException {
        StringBuilder sb = new StringBuilder();
        for(Child c : this.children) {
            sb.append(c.decrypt());
        }
        return sb.toString();
    }

    public void decryptAndWriteToFile(String fileName) throws BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, ClassNotFoundException {
        if(fileExtension.equals(".txt")) {
            FileUtil.writeFilePlainText(this.decryptAndGetBase64(), fileName, this.fileExtension);
            return;
        }
        Base64Util.base64StringToFile(this.decryptAndGetBase64(), fileName, this.fileExtension);
    }
}

/**
 * VERY IMPORTANT: WHEN EXPORTING PARENT, THE KEYPAIR OF CHILDREN IS SET NULL!!!
 * A MAP OF KEYPAIRS IS SAVED SEPERATELY TO A FILE WITH CONTENT
 * <Integer, Keypair> - the key says which child has what keypair!
 *
 * When writing to a file, first the keypair is extracted then the parent
 *
 * Let FileHandler handle all
 */