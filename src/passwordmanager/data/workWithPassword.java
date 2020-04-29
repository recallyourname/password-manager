package passwordmanager.data;

import java.util.*;

public class workWithPassword {

    public static void printPasswords(){

    }

    public static void addExistingPassword(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter link: ");
        String link = scanner.nextLine();

        System.out.println("Enter login: ");
        String login = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        workWithIni.writePasswordToIni(link, login, password);
    }

    public static String generatePassword(int len, boolean uppercase, boolean lowercase, boolean specsymbols, boolean nums) {
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String specchar = "{}()[]#;:^,.?!|&_`~@$%+=-* ";
        String numbers = "1234567890";
        String password = "";
        String resultPoolOfSymbols = "";
        int index;

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

        System.out.println(password);
        return password;
    }

}
