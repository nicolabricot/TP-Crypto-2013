
package test;

import file.FileUtility;
import java.io.File;

/**
 * Delete the temporary folder
 * 
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public class Clean {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // delete temporary files
        FileUtility.clean();
        
        // delete (un-)crypted files
        String crypted = System.getProperty("user.home") + "/Documents/Programmation/netbeans/Projet-Crypto/tests/crypted.txt";
        String decrypted = System.getProperty("user.home") + "/Documents/Programmation/netbeans/Projet-Crypto/tests/decrypted.txt";
        
        if (new File(crypted).exists())
            new File(crypted).delete();
        if (new File(crypted + FileUtility.extension_crypt).exists())
            new File(crypted + FileUtility.extension_crypt).delete();
        if (new File(decrypted).exists())
            new File(decrypted).delete();        
        
    }
}
