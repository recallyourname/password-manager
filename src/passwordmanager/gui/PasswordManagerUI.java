package passwordmanager.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import passwordmanager.gui.notifications.AlertBox;
import passwordmanager.gui.notifications.ConfirmBox;

import java.io.IOException;

public class PasswordManagerUI extends Application {
    Stage window;
    Scene scene;
    BorderPane layout;
    TableView<Password> table;
    String currentAccount;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Password Manager");
        MenuBar menuBar = new MenuBar();
        //menu items
        Menu passwordsDatabase = new Menu("Passwords");

        MenuItem listPasswords = new MenuItem("List passwords");
        listPasswords.setOnAction(e-> {
            try {
                menuPasswords();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        passwordsDatabase.getItems().add(listPasswords);

        MenuItem addPassword = new MenuItem("Add password");
        addPassword.setOnAction(e->createPassword());
        passwordsDatabase.getItems().add(addPassword);

        MenuItem copySelected = new MenuItem("Copy Selected");
        copySelected.setOnAction(e->{
            copyPasswordToClipboard();
        });
        passwordsDatabase.getItems().add(copySelected);

        MenuItem removeSelected = new MenuItem("Remove Selected");
        removeSelected.setOnAction(e->{
            try {
                removeSelectedPassword();
            } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        passwordsDatabase.getItems().add(removeSelected);

        Menu accountSettings = new Menu("Account");
        MenuItem logout = new MenuItem("Logout");
        logout.setOnAction(e->{
            try {
                if(ConfirmBox.display("Logout", "You want to logout?")) {
                    Main main = new Main();
                    main.start(window);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        accountSettings.getItems().add(logout);
        accountSettings.getItems().add(new MenuItem ("Change username"));
        accountSettings.getItems().add(new MenuItem("Change password"));

        menuBar.getMenus().addAll(passwordsDatabase, accountSettings);

        Label label = new Label("Choose one of the menu buttons");
        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(label);

        scene = new Scene(layout, 500, 500);
        window.setScene(scene);
        window.show();
    }

    private void copyPasswordToClipboard(){
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        Password pass = (Password) table.getSelectionModel().getSelectedItem();
        if(pass != null) {
            content.putString(pass.getValue());
            clipboard.setContent(content);
            AlertBox.display("Password", "Is copied to your clipboard");
        }
    }

    private void removeSelectedPassword() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Password pass = (Password) table.getSelectionModel().getSelectedItem();
        if(pass != null) {
            if(ConfirmBox.display("Confirm", "You want to remove password")){
                WorkWithIni.removeSectionByName(pass.getSource(), currentAccount);
                AlertBox.display("Password", "Is successfully removed");
                menuPasswords();
            }
        }
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
        TextField lengthInput = new TextField();

        lengthInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    AlertBox.display("Length Input", "Should be decimal");
                    lengthInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        lengthInput.setPromptText("Length");
        Button generatePassword = new Button("Generate");
        generatePassword.setOnAction(e->{
            if(!lengthInput.getText().trim().isEmpty() && (box1.isSelected() || box2.isSelected() || box3.isSelected() || box4.isSelected())){
                String generatedPassword = Password.generatePassword(lengthInput.getText(), box1.isSelected(), box2.isSelected(), box3.isSelected(), box4.isSelected());
                content.putString(generatedPassword);
                clipboard.setContent(content);
                AlertBox.display("Password", "Is copied to your clipboard");
            }
        });
        GridPane.setConstraints(generatePasswordsRules, 2, 2);

        Button submitPassword = new Button("Submit");
        submitPassword.setOnAction(e->{
            if (!websiteInput.getText().trim().isEmpty() && !usernameInput.getText().trim().isEmpty() && !passwordInput.getText().trim().isEmpty()){
                WorkWithIni.writePasswordToIni(currentAccount ,websiteInput.getText(), usernameInput.getText(), passwordInput.getText());
                if (ConfirmBox.display("Password Submitted", "Would you like to add another?")) {
                    createPassword();
                }
                else{
                    try {
                        menuPasswords();
                    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else {
                AlertBox.display("Error", "Can't submit");
            }
        });
        GridPane.setConstraints(submitPassword, 0, 3);

        generatePasswordsRules.getChildren().addAll(box1,box2,box3,box4,lengthInput,generatePassword, submitPassword);

        grid.getChildren().addAll(websiteLabel, websiteInput, usernameInput, usernameLabel, passwordInput, passwordLabel, generatePasswordsRules);
        grid.setAlignment(Pos.CENTER);
        layout.setCenter(grid);
    }

    private void menuPasswords() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
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

    public void setAccount(String accountName){
        currentAccount = accountName;
    }

    public ObservableList<Password> getPassword() {
        ObservableList<Password> passwords = FXCollections.observableArrayList();
        WorkWithIni.parseSectionsContainsAccount(passwords, currentAccount);

        return passwords;
    }
}