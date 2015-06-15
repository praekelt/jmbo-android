package praekelt.weblistingapp.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by altus on 2015/04/01.
 */
public class StringUtils {

    /**
     * Creates unique MD5 string from incoming String
     * @param input String to be converted to MD5
     * @return
     */
    public static String uniqueMD5(String input) {
        // Creates a unique MD5 key from the incoming string
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(input.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
