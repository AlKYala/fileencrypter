package de.yalama.fileencrypter.Crypto.Data.Model;

import de.yalama.fileencrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Crypto.Key.Model.Key;
import de.yalama.fileencrypter.Util.Base64Util;
import de.yalama.fileencrypter.Util.ByteUtil;
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

    private List<Child> children;
    private List<Integer> childrenEncryptedLengths;
    private String fileExtension;
    private String fileName;

    public Parent() throws NoSuchAlgorithmException {
        this.children = new ArrayList<Child>();
        this.childrenEncryptedLengths = new ArrayList<Integer>();
    }

    /**
     * Each file is recieved in base64 form which is the basis for encryption
     *
     * @param base64        The content in base64
     * @param fileName      The filename of the file to encrypt
     * @param fileExtension The extension of the file to encrypt
     * @param partLength    A base blocksize to choose (see Parent::encryptAndStore)
     * @throws Exception in place of IOException, NoSuchAlgorithmException, InvalidKeyException,
     *                   InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, InvalidKeySpecException,
     *                   IllegalBlockSizeException, ClassNotFoundException
     */
    public void encryptBase64AndStore(String base64, String fileName, String fileExtension, double partLength) throws Exception {
        this.encryptAndStoreValue(base64, partLength);
        this.fileExtension = fileExtension;
        this.fileName = fileName;
    }

    /**
     * Method to take a string and delegate encryption to children with random sized substrings for each child
     *
     * @param value     The string to encrypt
     * @param blockSize The base blocksize that is multiplied by a random value between 0.0 and 1.0
     * @throws Exception in place of: NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException,
     *                   InvalidKeyException, IllegalBlockSizeException, InvalidKeySpecException, InvalidAlgorithmParameterException,
     *                   IOException, ClassNotFoundException
     */
    public void encryptAndStoreValue(String value, double blockSize) throws Exception {
        Child child = new Child();
        int sum = 0;
        while (sum < value.length()) {
            int howMuchOfTheValueIsEncrypted = (int) (Math.random() * blockSize);
            howMuchOfTheValueIsEncrypted = (sum + howMuchOfTheValueIsEncrypted > value.length()) ? value.length() - (sum) : howMuchOfTheValueIsEncrypted;

            String subStringToEncrypt = value.substring(sum, sum + howMuchOfTheValueIsEncrypted);
            child.encryptAndStore(subStringToEncrypt);
            this.children.add(child);
            this.childrenEncryptedLengths.add(child.getEncryptedLength());
            child = new Child();
            sum += howMuchOfTheValueIsEncrypted;
        }
    }

    /**
     * Checks if all children have no keyPair left. Important for extraction of Parent instance
     *
     * @return false if one child with not null keyPair member exists
     */
    private boolean checkChildrenAllClear() {
        for (Child c : children) {
            if (c.getKey() != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the keyPairs of the children as Base64
     *
     * @return the keyPairs of the children encoded in Base64: Map<Integer, Key> -> byte[] -> String
     * @throws KeyPairNotFoundException
     * @throws IOException
     */
    public String getKeyPairsOfChildrenAsBase64() throws KeyPairNotFoundException, IOException, InsecureExtractionException {
        Map<Integer, Key> keyPairs = this.getKeyPairsOfChildren();
        byte[] keyPairsAsByteArr = ByteUtil.keyMapToByteArr(keyPairs);
        this.clearKeyPairsOfChildren();
        if (!this.checkChildrenAllClear()) {
            throw new InsecureExtractionException("Keypairs of children must be deleted first");
        }
        return Base64.getEncoder().encodeToString(keyPairsAsByteArr);
    }

    /**
     * Creates a map where keyPair Objects are indexed by what Index the corresponding child object is saved in Parent::children
     *
     * @return a Map<Integer, KeyMap> objected implemented in HashMap, see description.
     * @throws KeyPairNotFoundException thrown when a child object with no keyPair is found - which only happens if a child object keyMap is cleared
     */
    private Map<Integer, Key> getKeyPairsOfChildren() throws KeyPairNotFoundException {
        Map<Integer, Key> keyPairMap = new HashMap<Integer, Key>();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getKey() == null) {
                throw new KeyPairNotFoundException("Keypair of child not found - probably deleted already");
            }
            keyPairMap.put(i, children.get(i).getKey());
        }
        return keyPairMap;
    }

    /**
     * Iterates through children and clears the key for safety reasons
     */
    private void clearKeyPairsOfChildren() {
        for (Child c : children) {
            c.clearKey();
        }
    }

    /**
     * Used when loading parent from file
     * Initializes new Children objects that get their data later
     * Assigns the keys through Map<Integer, Key>
     *
     * @param map The map to load
     */
    public void loadKeyMap(Map<Integer, Key> map) {
        this.children = new ArrayList<Child>(map.size());
        for (Integer index : map.keySet()) {
            this.children.add(index, new Child());
            this.children.get(index).setKey(map.get(index));
        }
    }

    /**
     * Assembles the Encrypted parts of the children and returns the file
     * as encrypted
     *
     * @return The completely assembled encrypted part
     */
    public String getBase64() {
        StringBuilder sb = new StringBuilder();
        for (Child c : this.children) {
            sb.append(c.getEncryptedPart());
            c.setEncryptedPart(""); //safety reason
        }
        return sb.toString();
    }

    /**
     * Used in the decryption process - assigns the encrypted string to children
     */
    public void assignBase64ToChildren(String base64) {
        int totalSize = 0;
        for (Integer size : this.childrenEncryptedLengths) {
            totalSize += size;
        }

        int start = 0;
        for (int i = 0; i < children.size(); i++) {

            Child c = children.get(i);
            c.setEncryptedLength(this.childrenEncryptedLengths.get(i));
            String childSubstring = base64.substring(start, start + c.getEncryptedLength());
            c.setEncryptedPart(childSubstring);
            start += c.getEncryptedLength();
        }
    }

    /**
     * Used before writing the parent to a file - checking if:
     * -> The children dont have the extracted parts
     * -> The keys are already deleted
     *
     * @return
     */
    private boolean checkParentCanBeExtracted() {
        for (Child c : this.children) {
            if (c.getEncryptedPart() != null && c.getEncryptedPart().length() > 0) {
                return false;
            }
            if (c.getKey() != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears all the data from its properties to return the
     */
    private void sanitize() {
        for (Child c : this.children) {
            c.setKey(null);
            c.setEncryptedPart("");
        }
    }

    /**
     * Sanitzes the parent instance (see: Parent::sanitize) and returns this instance as base64 through:
     * Parent (as Object) -> byte Array -> base64
     *
     * @return This instance as base64 after sanitize
     * @throws InsecureExtractionException
     * @throws IOException
     */
    public String sanitizeAndGetInstanceBase64() throws InsecureExtractionException, IOException {
        this.sanitize();
        if (!this.checkParentCanBeExtracted()) {
            throw new InsecureExtractionException("The parent cannot be extracted before the encrypted parts and keys are removed");
        }
        byte[] parentAsByteArr = ByteUtil.anyObjectToByteArr(this);
        return Base64Util.byteArrToBase64(parentAsByteArr);
    }

    /**
     * Iterates through children, takes decrypt output (see Child::decrypt) and concatenates with StringBuilder
     *
     * @return The content of the original data - the decrypted file
     * @throws Exception in place of BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
     *                   IllegalBlockSizeException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IOException
     */
    public String decryptAndGetBase64() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Child c : this.children) {
            String decryptedPart = c.decrypt();
            sb.append(decryptedPart);
        }
        return sb.toString();
    }
}