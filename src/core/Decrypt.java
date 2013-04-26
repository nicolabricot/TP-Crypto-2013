
package core;

import file.FileCrypter;
import file.FileUtility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *  Decrypt a file partialy encrypted
 * 
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public class Decrypt {
    
    private String file;
    private int marker;
    private String prefix;
    
    /**
     * Default constructor. Need the path to the uncrypted file
     * 
     * @param file String path to the first file not encrypted
     * @throws Exception 
     */
    public Decrypt(String file) throws Exception {
        this.file = file;
        
        // get the line marker to split aggregated uncrypted file
        try {
            this.marker = Integer.decode(FileUtility.last(this.file));
        } catch (Exception ex) {
            throw new Exception("File \n" + this.file + "\n" + "is not correct!");
        }
        
        // if crypted part file not found...
        if (!new File(this.file + FileUtility.extension_crypt).exists())
            throw new Exception("Crypted file \n" + this.file + FileUtility.extension_crypt + "\n" + "not found!");
    }
    
    /**
     * Decrypt a partialy file encrypted. Generate a signle file, totally decrypted.
     * 
     * @param password String password used to crypt the file
     * @param output String path to the output file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    public void decrypt(String password, String output) throws FileNotFoundException, IOException, GeneralSecurityException {
        this.prefix = FileUtility.unaggregate(this.file, this.marker);
        
        // use the API
        Security.addProvider(new BouncyCastleProvider());
        
        // create a new crypter
        FileCrypter crypter = new FileCrypter();
            
        // get key to be used from the password
        SecretKeySpec key = Password.getKey(password);
        
        // decrypt the second file (which is supposed to be crypted)
        crypter.decryptFile(key, this.file + FileUtility.extension_crypt, FileUtility.tmp + this.prefix + "-2" + FileUtility.extension_tmp);
        
        // recompose the file with an encrypted part in one single file
        FileUtility.recompose(this.prefix, output);
        
        // clean temporary files
        FileUtility.clean();
    }
    
}
