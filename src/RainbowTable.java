
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RainbowTable {
    private static final int CHAINLENGTH = 2000;
    private static final int TABLESIZE = 2000;
    private static final int PASSLENGTH = 7;
    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public final HashMap<String, String> table = new HashMap<>();

    /**
     * Hashes String with MD5
     * @param s String to hash in MD5
     * @return hashed String
     */
    private String  hash(String s){
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
    private String reduce(String hash, int level){
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

    /**
     * Generate Passwords recursively until Limit reached
     */
    private void generatePasswords(){
        generatePasswords("");
    }

    private void generatePasswords(String curr){
        //Check if table size is reached
        if(table.size() == TABLESIZE) return;
        //Check if current password has reached its size limit, if so add it to table
        else if(curr.length() == PASSLENGTH) {
            table.put(curr, null);
        }else{
            StringBuilder newCurr = new StringBuilder(curr);
            for(char c : chars){
                String old = newCurr.toString();
                newCurr.append(c);
                generatePasswords(newCurr.toString());
                //reset curr to change added character
                newCurr = new StringBuilder(old);
            }
            curr = newCurr.toString();
        }
    }

    private void generateChainEnds(){
        int count = 0;
        for(String pass : table.keySet()){
            String end = pass;
            for(int i = 0; i<CHAINLENGTH; i++){
                end = reduce(hash(end), i);
            }
            table.put(pass, end);
            count++;
            System.out.print("\rGenerating Table: " + count + "/" + TABLESIZE);
        }
        System.out.println();
    }

    public void printTable() {
        System.out.println("\n");
        for(Map.Entry<String, String> s : table.entrySet()){
            System.out.print(s.getKey() + " | " + s.getValue() + "\n");
        }
    }

    /**
     * Generates Rainbow Table
     * @return Rainbow Table
     */
    public HashMap<String, String> generateTable(){
        generatePasswords();
        generateChainEnds();
        return table;
    }

    /**
     * Searches for password in table according to Slide 3.26
     * @param hash to find password for
     * @return found password or null if absent or not found
     */
    public String findPassword(String hash){
        System.out.println("Searching Password for hash: " + hash);
        String end = null;

        //Searching chain end
        mainLoop: //Label to break out of nested loop
        for(int i = CHAINLENGTH-1; i>=0; i--){
            System.out.print("\rSeaching from Round " + i);
            String tmp = hash;
            for(int j = i; j<CHAINLENGTH; j++){
                tmp = reduce(tmp, j);
//                System.out.println(tmp);
                if(table.containsValue(tmp)){
                    end = tmp;
                    break mainLoop;
                }
                tmp = hash(tmp);
            }
        }
        System.out.println();
        if(end == null) return null; //if no matching chain end has been found

        //Find start of found chain end in hashmap
        String start = null;
        for(Map.Entry<String, String> entry : table.entrySet()){
            if(entry.getValue().equals(end)) {
                start = entry.getKey();
                break;
            }
        }

        //Recalculate Chain to find password
        String tmp = start;
        String passBefore = start;
        String password = null;
        for(int i = 0; i<CHAINLENGTH; i++){
            assert tmp != null;
            tmp = hash(tmp);
            if(tmp.equals(hash)){
                password = passBefore;
                break;
            }
            tmp = reduce(tmp, i);
            passBefore = tmp;
        }

        System.out.println(password != null ? "Password found: " + password : "No Password found!");
        return password;
    }




}
