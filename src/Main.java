import javax.xml.crypto.dsig.DigestMethod;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Main {
    private static final int CHAINLENGTH = 2000;
    private static final int PASSLENGTH = 7;
    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static void main(String[] args){

        System.out.println(hash("HELLO"));
        System.out.println(reduce(hash("HELLO"), 0));


    }


    /**
     * Hashes String with MD5
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


    /**
     * Reduces the Hash according to Slide 3.27
     * @param hash Hash value as String
     * @param level current level
     * @return reduced hash value
     */
    private static String reduce(String hash, int level){
        BigInteger hashAsNum = new BigInteger(hash, 16);
        hashAsNum = hashAsNum.add(BigInteger.valueOf(level));
        StringBuilder sb = new StringBuilder();
        BigInteger alphabetSize = BigInteger.valueOf(chars.length);

        for(int i = 1; i<=PASSLENGTH; i++){
            sb.append(chars[hashAsNum.mod(alphabetSize).intValue()]);
            hashAsNum = hashAsNum.divide(alphabetSize);
        }
        sb.reverse();
        return sb.toString();
    }




}
