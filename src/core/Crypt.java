
package core;

import file.FileCrypter;
import file.FileUtility;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Crypt partialy a file
 * 
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public class Crypt {
    
    private String file;
    private int begin;
    private int end;
    private String prefix;
    
    /**
     * Default construcor. Need the file you want to partialy crypt, the lines (begin and end) to crypt.
     * 
     * @param file string path to the file to be encrypted
     * @param begin first line to be encrypted
     * @param end last line to be encrypted
     */
    public Crypt(String file, int begin, int end) {
        this.file = file;
        this.begin = (begin <= 0) ? 0 : begin-1;
        this.end = end-1;
    }
    
    /**
     * Crypt partialy the file. Need the password (to generate the secret key) and the output file.
     * Generate two files (one not crypted, and the other one, with the crypted part).
     * 
     * @param password string password to be used to encrypt file
     * @param output string path to the output file encrypted (partialy)
     * @throws FileNotFoundException
     * @throws GeneralSecurityException
     * @throws IOException 
     */
    public void crypt(String password, String output) throws FileNotFoundException, GeneralSecurityException, IOException {
        this.prefix = FileUtility.split(this.file, this.begin, this.end);
        
        // use the API
        Security.addProvider(new BouncyCastleProvider());
        
        // create a new crypter
        FileCrypter crypter = new FileCrypter();
            
        // get key to be used from the password
        SecretKeySpec key = Password.getKey(password);
        
        // extension_crypt the second file
        crypter.cryptFile(key, FileUtility.tmp + this.prefix + "-2" + FileUtility.extension_tmp, output + FileUtility.extension_crypt);
        
        // recompose the file with an encrypted part in one single file
        FileUtility.aggregate(this.prefix, output);
        
        // clean temporary files
        FileUtility.clean();
    }
    
}
