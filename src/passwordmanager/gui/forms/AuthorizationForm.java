package passwordmanager.gui.forms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import passwordmanager.gui.notifications.AlertBox;
import passwordmanager.gui.notifications.ConfirmBox;
import passwordmanager.gui.PasswordManagerUI;
import passwordmanager.validation.LoginValidation;

import java.io.IOException;


public class AuthorizationForm extends Application {
    Stage window;
    Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        //set layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        //init username input field
        Label usernameLabel = new Label("Username");
        GridPane.setConstraints(usernameLabel, 0,0);
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        GridPane.setConstraints(usernameInput,1, 0);
        //init password input field
        Label passwordLabel = new Label("Password");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput, 1, 1);
        //submit form button
        Button loginButton = new Button("Login");
        //action of submit button
        loginButton.setOnAction(e -> {
            try {
                loginFormSubmit(usernameInput, passwordInput);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        GridPane.setConstraints(loginButton, 1,2 );

        Button registrationButton = new Button("Sign Up");
        registrationButton.setOnAction(e->{
            RegistrationForm reg = new RegistrationForm();
            try {
                reg.start(window);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
        GridPane.setConstraints(registrationButton, 1,3);

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, registrationButton);
        grid.setAlignment(Pos.CENTER);

        scene = new Scene(grid, 500,500);
        window.setScene(scene);
    }

    private void loginFormSubmit(TextField usernameInput, TextField passwordInput) throws IOException {
        if (LoginValidation.checkForLoginAndPassword(usernameInput, passwordInput)) {
            if(ConfirmBox.display("Confirm", "You want to login with "+usernameInput.getText() + "?")){
                AlertBox.display("Congratulations!", "You logged in!");
                PasswordManagerUI PMI = new PasswordManagerUI();
                try {
                    PMI.setAccount(usernameInput.getText());
                    PMI.start(window);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        else{
            AlertBox.display("Can't Login", "Account Doesn't exist");
        }
    }

}
