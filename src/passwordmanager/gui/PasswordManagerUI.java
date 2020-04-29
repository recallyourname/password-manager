package passwordmanager.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import passwordmanager.data.Password;
import passwordmanager.data.WorkWithIni;
import passwordmanager.data.WorkWithPassword;

public class PasswordManagerUI extends Application {
    Stage window;
    Scene scene;
    BorderPane layout;
    TableView<Password> table;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Password Manager");

        //menu items
        Menu passwordsDatabase = new Menu("Passwords");

        MenuItem listPasswords = new MenuItem("List passwords");
        listPasswords.setOnAction(e->menuPasswords());
        passwordsDatabase.getItems().add(listPasswords);

        MenuItem addPassword = new MenuItem("Add password");
        addPassword.setOnAction(e->createPassword());
        passwordsDatabase.getItems().add(addPassword);

        Menu accountSettings = new Menu("Account");
        accountSettings.getItems().add(new MenuItem ("Change username"));
        accountSettings.getItems().add(new MenuItem("Change password"));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(passwordsDatabase, accountSettings);

        Label label = new Label("Choose one of the menu buttons");
        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(label);

        scene = new Scene(layout, 500, 500);
        window.setScene(scene);
        window.show();
    }

    private void createPassword() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label websiteLabel = new Label("Website");
        GridPane.setConstraints(websiteLabel, 0,0);
        TextField websiteInput = new TextField();
        websiteInput.setPromptText("Website");
        GridPane.setConstraints(websiteInput,1, 0);

        Label usernameLabel = new Label("Username");
        GridPane.setConstraints(usernameLabel, 0,1);
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        GridPane.setConstraints(usernameInput,1, 1);

        Label passwordLabel = new Label("Password");
        GridPane.setConstraints(passwordLabel, 0,2);
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput,1, 2);

        VBox generatePasswordsRules = new VBox(5);
        CheckBox box1 = new CheckBox("Uppercase");
        CheckBox box2 = new CheckBox("Lowercase");
        CheckBox box3 = new CheckBox("Specsymbols");
        CheckBox box4 = new CheckBox("Numbers");
        Button generatePassword = new Button("Generate");
        generatePassword.setOnAction(e->{
            String generatedPassword = WorkWithPassword.generatePassword(12, box1.isSelected(), box2.isSelected(), box3.isSelected(), box4.isSelected());
            content.putString(generatedPassword);
            clipboard.setContent(content);
            AlertBox.display("Password", "Is copied to your clipboard");
        });
        GridPane.setConstraints(generatePasswordsRules, 2, 2);

        Button submitPassword = new Button("Submit");
        submitPassword.setOnAction(e->{
            if (websiteInput.getText() != null && usernameInput.getText() != null && passwordInput.getText() != null){
                WorkWithIni.writePasswordToIni(websiteInput.getText(), usernameInput.getText(), passwordInput.getText());
                if (ConfirmBox.display("Password Submitted", "Would you like to add another?")) {
                    createPassword();
                }
                else{
                    menuPasswords();
                }
            }
            else {
                AlertBox.display("Error", "Can't submit");
            }
        });
        GridPane.setConstraints(submitPassword, 0, 3);

        generatePasswordsRules.getChildren().addAll(box1,box2,box3,box4,generatePassword, submitPassword);

        grid.getChildren().addAll(websiteLabel, websiteInput, usernameInput, usernameLabel, passwordInput, passwordLabel, generatePasswordsRules);
        grid.setAlignment(Pos.CENTER);
        layout.setCenter(grid);
    }

    private void menuPasswords (){
        TableColumn<Password, String> source = new TableColumn<>("Source");
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        source.setMinWidth(190);

        TableColumn<Password, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        username.setMinWidth(150);

        TableColumn<Password, String> password = new TableColumn<>("Password");
        password.setCellValueFactory(new PropertyValueFactory<>("value"));
        password.setMinWidth(145);

        table = new TableView<>();
        table.setItems(getPassword());
        table.getColumns().addAll(source, username, password);
        layout.setCenter(table);
    }

    public ObservableList<Password> getPassword() {
        ObservableList<Password> passwords = FXCollections.observableArrayList();
        passwords.add(new Password("Github", "Adolf", "122321qqq"));
        passwords.add(new Password("Vk", "Adolf1488", "1221q??qq"));
        passwords.add(new Password("Discord", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Telegram", "Adolf", "122321qqq"));
        passwords.add(new Password("Youtube", "Adolf", "122321qqq"));
        passwords.add(new Password("Twitch", "Adolf", "122321qqq"));
        passwords.add(new Password("Gmail", "Adolf", "122321qqq"));
        passwords.add(new Password("Twitter", "Adolf", "122321qqq"));
        return passwords;
    }
}
