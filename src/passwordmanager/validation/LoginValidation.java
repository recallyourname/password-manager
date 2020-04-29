package passwordmanager.validation;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import passwordmanager.data.WorkWithIni;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidation {
    private final static Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,15}$");
    private final static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$");

    public static boolean validateUsername(TextField input){
        String username = input.getText();
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        if (matcher.matches()) {
            System.out.println("Username is ok");
            return true;
        }
        System.out.println("Incorrect username");
        return false;
    }

    public static boolean validatePassword(PasswordField input){
        String password = input.getText();
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if(matcher.matches()){
            System.out.println("Password is ok");
            return true;
        }
        System.out.println("password is not ok");
        return false;
    }

    public static boolean checkForLoginAndPassword(TextField inputUsername, TextField inputPassword) throws IOException {
        if (WorkWithIni.readAccountDataFromIni(inputUsername.getText(), inputPassword.getText())){
            System.out.println("Account exist");
            return true;
        }
        else {
            System.out.println("Account doesn't exist");
            return false;
        }
    }

    public static boolean checkIfAccountExist(TextField inputUsername) throws IOException {
        if (WorkWithIni.findAccountByUsername(inputUsername.getText())) {
            return true;
        }
        return false;
    }
}
