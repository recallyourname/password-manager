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
import passwordmanager.data.WorkWithIni;
import passwordmanager.gui.notifications.AlertBox;
import passwordmanager.gui.notifications.ConfirmBox;
import passwordmanager.gui.Main;
import passwordmanager.gui.PasswordManagerUI;
import passwordmanager.validation.LoginValidation;

public class RegistrationForm extends Application {

    Stage window;
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username (A-Za-z0-9_-)");
        GridPane.setConstraints(usernameLabel, 0,0);
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        GridPane.setConstraints(usernameInput,1, 0);

        Label passwordLabel = new Label("Password (A-Za-z0-9+symbols)");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Your password");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button registrationButton = new Button("Sign Up");
        registrationButton.setOnAction(e->{
            try {
                registrationButtonSubmit(usernameInput, passwordInput);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });
        GridPane.setConstraints(registrationButton, 1,2);

        Button backToTheLoginButton = new Button("Back to login");

        backToTheLoginButton.setOnAction(e->{
            if(ConfirmBox.display("Go back?", "Go back to login page?")){
                window.close();
                Main main = new Main();
                try {
                    main.start(window);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        GridPane.setConstraints(backToTheLoginButton, 1, 3);

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, registrationButton, backToTheLoginButton);
        grid.setAlignment(Pos.CENTER);

        scene = new Scene(grid, 500,500);
        window.setScene(scene);

        window.show();
    }

    private void registrationButtonSubmit(TextField usernameInput, PasswordField passwordInput) throws Exception {
        if (LoginValidation.checkIfAccountExist(usernameInput)){
            AlertBox.display("Registration issue", "Account does exist");
        }
        else if ((LoginValidation.validateUsername(usernameInput)) && (LoginValidation.validatePassword(passwordInput))) {
            WorkWithIni.writeAccountDataToIni(usernameInput.getText(), passwordInput.getText());
            PasswordManagerUI PMI = new PasswordManagerUI();
            PMI.setAccount(usernameInput.getText());
            PMI.start(window);
        }
        else {
            AlertBox.display("Error", "Try another login/password");
        }
    }

}
