
package test;

import core.Crypt;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Test if methods created for crypting partialy a file are working
 * 
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public class CryptTest {
    
     /**
     * @param args the command line arguments
     */
    @SuppressWarnings("CallToThreadDumpStack")
    public static void main(String[] args) {
        
        String suffix = "lorem.txt";
        String file = System.getProperty("user.dir") + "/tests/" + suffix;
        String out = System.getProperty("user.dir") + "/tests/crypted.txt";
        
        try {
            
            Crypt c = new Crypt(file, 4, 5);
            c.crypt("this is my secret fucking password 345!", out);
            
        }
        catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
    
}
