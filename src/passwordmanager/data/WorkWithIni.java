package passwordmanager.data;

import javafx.collections.ObservableList;
import org.apache.commons.codec.digest.DigestUtils;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

public class WorkWithIni {

    public static String checkSum(){
        File file = new File("passwords.ini");
        String checksum = null;
        try {
            checksum = DigestUtils.md5Hex(new FileInputStream(file));
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
        return checksum;
    }

    public static void initGeneral(){
        File file = new File("passwords.ini");
        try{
            Wini ini = new Wini(file);
            ini.put("GENERAL", "checksum", checkSum());
            ini.store();
        }
        catch(IOException e){
            System.out.println(e.toString());
        }
    }

    public static void writePasswordToIni(String account, String link, String login, String pass) {
        File file = new File("passwords.ini");
        try {
            Wini ini = new Wini(file);
            ini.put(link, "account", account);
            ini.put(link, "login", login);
            ini.put(link, "password", pass);
            ini.store();
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static ObservableList<Password> parseSectionsContainsAccount(ObservableList<Password> passwords, String currentAccount){
        File file = new File("passwords.ini");
        try {
            Wini ini = new Wini(file);
            Collection<Profile.Section> list = ini.values();
            for(Profile.Section section : list){
                if (section.get("account").equals(currentAccount)) {
                    Password pass = new Password(section.getName(), section.get("login"), section.get("password"));
                    passwords.add(pass);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return passwords;
    }

    public static void readPasswordFromIni(String link){
        File file = new File("passwords.ini");
        try {
            Wini ini = new Wini(file);
            String password = ini.get(link, "password", String.class);
            String login = ini.get(link, "login", String.class);
            System.out.println("Login for " + link + " is: " + login + "\nPassword is: " + password);
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void writeAccountDataToIni(String username, String password){
        File file = new File("accounts.ini");
        try {
            Wini ini = new Wini(file);
            ini.put(username, "username", username);
            ini.put(username, "password", password);
            ini.store();
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static boolean readAccountDataFromIni(String username, String password){
        File file = new File("accounts.ini");
        Wini ini = null;
        try {
            ini = new Wini(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String usernameToCompare = ini.get(username, "username", String.class);
        String passwordToCompare = ini.get(username, "password", String.class);
        System.out.println(usernameToCompare + " " + passwordToCompare + " " + username + " " + password);
        if (usernameToCompare == null || passwordToCompare == null) return false;
        else if (usernameToCompare.compareTo(username) == 0 && passwordToCompare.compareTo(password) == 0) return true;
        else return false;
    }



    public static boolean findAccountByUsername(String username) throws IOException {
        File file = new File("accounts.ini");
        Wini ini = new Wini(file);

        String usernameToCompare = ini.get(username, "username", String.class);
        if (usernameToCompare == null) return false;
        else if (usernameToCompare.compareTo(username) == 0) return true;
        else return false;
    }

}
