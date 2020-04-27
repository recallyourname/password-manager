package sample.gui;

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
import sample.data.workWithIni;
import sample.validation.loginValidation;

public class Main extends Application {
    Stage window;
    Scene scene1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Login");
        window.setOnCloseRequest(e->{
            e.consume();
            onClose();
        });
        //Login
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username");
        GridPane.setConstraints(usernameLabel, 0,0);
        TextField usernameInput = new TextField("Default Text");
        GridPane.setConstraints(usernameInput,1, 0);

        Label passwordLabel = new Label("Password");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (loginValidation.accountExists(usernameInput, passwordInput)) {
                if(ConfirmBox.display("Confirm", "You want to login with "+usernameInput.getText() + "?")){
                    AlertBox.display("Congratulations!", "You logged in!");
                    workWithIni.writeAccountDataToIni(usernameInput.getText(), passwordInput.getText());
                    PasswordManagerInterface PMI = new PasswordManagerInterface();
                    try {
                        PMI.start(window);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        GridPane.setConstraints(loginButton, 1,2 );

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
        grid.setAlignment(Pos.CENTER);

        scene1 = new Scene(grid, 500,500);
        window.setScene(scene1);

        window.show();
    }

    private void onClose(){
        if(ConfirmBox.display("Confirm", "Are you sure you want to close?")){
            window.close();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
