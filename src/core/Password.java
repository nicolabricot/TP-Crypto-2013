
package core;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.util.Arrays;

/**
 * Utility class to transform a string password into a SecretKeySpec
 * 
 * @author Nicolas Devenet <nicolas@devenet.info>
 */
public abstract class Password {
    
    /**
     * Transform a string password into a secret key
     * 
     * @param password string password to be converted to a SecretKeySpec
     * @return SecretKeySpec generated from the string password
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException 
     */
    public static SecretKeySpec getKey(String password) throws GeneralSecurityException, UnsupportedEncodingException {            
        // create key from password
        byte[] key = password.getBytes("utf-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        
        // return the secrect key
        return new SecretKeySpec(key, "AES");
    }
    
}
