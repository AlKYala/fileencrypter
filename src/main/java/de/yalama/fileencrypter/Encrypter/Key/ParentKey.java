package de.yalama.fileencrypter.Encrypter.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParentKey implements Serializable {

    private List<PartKey> partKeys;

    private void encryptAndStoreValue(String value) {
        PartKey partKey = new PartKey();
        int sum = 0;
        while(sum < value.length()) {
            int howMuchOfTheValueIsEncrypted = (int) (Math.random() * 5000d);
            howMuchOfTheValueIsEncrypted = (sum+howMuchOfTheValueIsEncrypted > value.length() -1) ? value.length()-1 - sum : howMuchOfTheValueIsEncrypted;
            String subStringToEncrypt = value.substring(sum, sum+howMuchOfTheValueIsEncrypted);
            partKey.encryptAndStore(subStringToEncrypt);
            this.partKeys.add(partKey);
            partKey = new PartKey();
            sum += howMuchOfTheValueIsEncrypted;
        }
    }

    private String decrypt() {
        StringBuilder sb = new StringBuilder();
        for(PartKey partKey : partKeys) {
            sb.append(partKey.decrypt());
        }
        return sb.toString();
    }
}
