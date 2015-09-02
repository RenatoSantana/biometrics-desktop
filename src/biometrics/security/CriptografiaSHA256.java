package biometrics.security;

/**
 *
 * @author 205406813
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CriptografiaSHA256 {
 
    
    public static String encode(String password){
          try {
	            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = messageDigest.digest(password.getBytes("UTF-8"));
	            StringBuilder stringBuilder=  new StringBuilder();
	            for (int i = 0; i < hash.length; i++) {
	                stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            return stringBuilder.toString();
	        } catch (NoSuchAlgorithmException ex) {
	            Logger.getLogger(CriptografiaSHA256.class.getName()).log(Level.SEVERE, null, ex);
	            return "";
	        } catch (UnsupportedEncodingException unsupportedEncodingException) {
	            Logger.getLogger(CriptografiaSHA256.class.getName()).log(Level.SEVERE, null,"CriptografiaSHA256.getAsObject:"+ unsupportedEncodingException);
	            return "";
	        }
    }
}
