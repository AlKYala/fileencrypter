package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Parent implements Serializable {

    private List<Child> children;
    private KeyPairGenerator generator;

    public Parent() throws NoSuchAlgorithmException {
        this.children = new ArrayList<Child>();
        this.generator = KeyPairGenerator.getInstance("RSA");
        this.generator.initialize(2048);
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

    private String decrypt() throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        StringBuilder sb = new StringBuilder();
        for(Child child : children) {
            sb.append(child.decrypt());
        }
        return sb.toString();
    }
}
