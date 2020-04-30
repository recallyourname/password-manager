package passwordmanager.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Password {
    private String source;
    private String username;
    private String value;

    public Password(){
        this.source = "";
        this.username = "";
        this.value = "";
    }

    public Password(String source, String username, String value){
        this.source = source;
        this.username = username;
        this.value = value;
    }

    public void addPasswordToDB(String account, String source, String username, String value){
        WorkWithIni.writePasswordToIni(account, source, username, value);
    }

    public static String generatePassword(String lenStringValue, boolean uppercase, boolean lowercase, boolean specsymbols, boolean nums) {
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String specchar = "{}()[]#;:^,.?!|&_`~@$%+=-* ";
        String numbers = "1234567890";
        String password = "";
        String resultPoolOfSymbols = "";
        int index;
        int len = Integer.parseInt(lenStringValue);
        if (uppercase) {
            resultPoolOfSymbols += alphabet.toUpperCase();
        }
        if (lowercase) {
            resultPoolOfSymbols += alphabet;
        }
        if (specsymbols) {
            resultPoolOfSymbols += specchar;
        }
        if (nums) {
            resultPoolOfSymbols += numbers;
        }

        String[] allowedSymbols = resultPoolOfSymbols.split("");
        List<String> shuffleSymbols = Arrays.asList(allowedSymbols);
        Collections.shuffle(shuffleSymbols);

        for(int i = 0; i <= len - 1; i++) {
            index = random.nextInt(shuffleSymbols.size());
            password += shuffleSymbols.get(index);
        }

        return password;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
