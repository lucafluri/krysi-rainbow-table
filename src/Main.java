import java.util.Map;

public class Main {
    public static void main(String[] args){
        RainbowTable rt = new RainbowTable();
        String toFind = "1d56a37fb6b08aa709fe90e12ca59e12";

        rt.generateTable();
        //rt.printTable();

        System.out.println(rt.findPassword(toFind));
    }
}
