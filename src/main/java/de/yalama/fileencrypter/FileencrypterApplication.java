package de.yalama.fileencrypter;

import de.yalama.fileencrypter.Encrypter.Exceptions.InsecureExtractionException;
import de.yalama.fileencrypter.Encrypter.Exceptions.KeyPairNotFoundException;
import de.yalama.fileencrypter.Encrypter.Key.Parent;
import de.yalama.fileencrypter.Util.HottestUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@SpringBootApplication
public class FileencrypterApplication {

    //just testing here
    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, KeyPairNotFoundException, IOException, InsecureExtractionException, ClassNotFoundException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        /*SpringApplication.run(FileencrypterApplication.class, args);
        HottestUtil.testSimpleEncryptDecrypt();
        HottestUtil.storeAndLoadAndStoreParentToo();
        HottestUtil.loadFromFileEncryptDecryptWriteBackToFile("bild.png", "png");*/
        //HottestUtil.loadFileAndEncryptToBase64("Speicher.mp4");
        //HottestUtil.readFileAndWriteBase64("Speicher.mp4", "output.txt");
        //HottestUtil.fileToBase64EncryptDecrypt("bild.png", "ausgabe.txt");
        //System.exit(0);
        Parent p = new Parent();
        /*p.encryptAndStoreValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dictum euismod ligula sit amet aliquam. Fusce quis turpis et est semper efficitur. Etiam ac porttitor elit. Morbi scelerisque ante et nunc scelerisque, sit amet accumsan mi vulputate. Quisque at mattis metus. Nam lacinia semper magna, vel volutpat erat sollicitudin id. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin rutrum ex ac condimentum eleifend. Sed ac lacus ullamcorper, fringilla nisl ac, porta neque. Cras dignissim vehicula lorem vel consequat. In semper justo eu est lobortis, accumsan semper massa tempor. Nulla aliquet velit quis est pretium feugiat. Vivamus porta odio nisl, et semper justo imperdiet a. Praesent vitae tincidunt nibh. Cras non commodo magna, sed pretium diam.\n" +
                "\n" +
                "Nulla facilisi. Fusce sapien sem, tristique a nibh quis, rutrum volutpat lectus. Praesent rutrum orci nec dolor elementum, nec fringilla quam auctor. Sed placerat ut nibh in ultrices. Pellentesque porta efficitur dolor, dictum convallis lorem rutrum at. Aliquam erat volutpat. Aenean sed massa quis orci cursus eleifend. Vivamus mattis ultricies tellus vitae varius. Curabitur porta pulvinar mi at congue. Maecenas lorem leo, rhoncus id mollis eu, volutpat in purus. Cras nisi est, malesuada quis metus in, efficitur convallis nulla.\n" +
                "\n" +
                "Phasellus tempor nec leo quis luctus. Donec eu interdum libero. Vivamus facilisis ultrices consequat. In sed varius nisi, non semper tortor. Vestibulum sagittis ullamcorper diam, et semper mauris fringilla vel. Etiam non sagittis augue, sed dignissim velit. Aenean non leo pulvinar, pellentesque orci nec, varius quam. Ut vel accumsan magna. Quisque euismod efficitur pharetra. Fusce volutpat felis mauris. Mauris ac porttitor sem. Aenean nec tellus ut turpis aliquam dictum quis vel velit. Suspendisse potenti. Curabitur pulvinar nulla magna, aliquam interdum lorem malesuada eget.\n" +
                "\n" +
                "Sed nec urna molestie, cursus eros ac, mattis libero. Sed convallis eros lacus, non laoreet nibh fringilla ut. Donec ac risus vitae lectus porta rhoncus sed sit amet turpis. Aliquam nec nunc est. Proin rutrum velit nec sagittis congue. Mauris bibendum diam suscipit, faucibus erat hendrerit, dapibus neque. Nulla venenatis pretium euismod. Pellentesque eget volutpat lacus. Praesent sed risus vel nisl scelerisque venenatis. Ut posuere at felis nec sagittis. Curabitur porta lobortis metus at imperdiet. Maecenas lacus lacus, eleifend ut tempor et, dignissim ac justo. Vivamus ac felis sit amet neque faucibus egestas. Quisque feugiat arcu sit amet ultrices sodales. Sed ut varius sapien, quis consectetur diam. Phasellus pretium ante nunc, ut bibendum magna dignissim pretium.\n" +
                "\n" +
                "Pellentesque malesuada lobortis lectus, at finibus massa varius sed. Vivamus augue lorem, luctus vel rutrum vel, sagittis mattis massa. Suspendisse ac massa quis augue dignissim dictum id eu neque. Duis ante diam, congue eu egestas nec, maximus at mauris. Vestibulum eleifend tortor auctor metus tristique, a venenatis velit ornare. Quisque feugiat quis nisi ac consectetur. Morbi tincidunt libero augue, vitae ultrices nibh maximus nec.", 2000);
        p.extractAll("keyobject", "parent.file");
        Parent.loadParent("parent.file");
        p.loadKeyMap("keyobject.map");
        p.decryptAndWriteToFile("text", "txt");*/

        p.encryptFileAndStore("Speicher.mp4", 500000);
        p.decryptAndWriteToFile("test");
    }
}
