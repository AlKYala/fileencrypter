package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Parent implements Serializable {

    private List<Child> children;
    private KeyPairGenerator generator;

    public Parent() throws NoSuchAlgorithmException {
        this.children = new ArrayList<Child>();
        this.generator = KeyPairGenerator.getInstance("RSA");
        this.generator.initialize(2048);
    }

    private void encryptAndStoreValue(String key, String value) throws NoSuchAlgorithmException, NoSuchPaddingException {
        /*Child child = new Child(key);
        int sum = 0;
        while(sum < value.length()) {
            int howMuchOfTheValueIsEncrypted = (int) (Math.random() * 5000d);
            howMuchOfTheValueIsEncrypted = (sum+howMuchOfTheValueIsEncrypted > value.length() -1) ? value.length()-1 - sum : howMuchOfTheValueIsEncrypted;
            String subStringToEncrypt = value.substring(sum, sum+howMuchOfTheValueIsEncrypted);
            child.encryptAndStore(subStringToEncrypt);
            this.children.add(child);
            child = new Child(key);
            sum += howMuchOfTheValueIsEncrypted;
        }*/
    }

    private String decrypt() {
        /*StringBuilder sb = new StringBuilder();
        for(Child child : children) {
            sb.append(child.decrypt());
        }
        return sb.toString();*/
    }
}
