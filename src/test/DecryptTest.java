
package test;

import core.Decrypt;


/**
 * Test if methods created for decrypting partialy a file are working 
 * 
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public class DecryptTest {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("CallToThreadDumpStack")
    public static void main(String[] args) {
        
        String file = System.getProperty("user.dir") + "/tests/crypted.txt";
        String out = System.getProperty("user.dir") + "/tests/decrypted.txt";
        
        try {
            
            Decrypt d = new Decrypt(file);
            d.decrypt("this is my secret fucking password 345!", out);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
