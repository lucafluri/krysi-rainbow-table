import javax.xml.crypto.dsig.DigestMethod;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Main {
    private final int CHAINLENGTH = 2000;
    private final int PASSLENGTH = 7;
    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    /** Hashes String with MD5
     *
     * @param s String to hash in MD5
     * @return hashed String
     */
    private static String  hash(String s){
        MessageDigest md = null;
        byte[] bytes = new byte[0];
        try {
            md = MessageDigest.getInstance("MD5");
            bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, bytes).toString(16);
    }

    public static void main(String[] args){

        System.out.println(hash("HELLO"));
        System.out.println(hash("gg"));


    }
}
