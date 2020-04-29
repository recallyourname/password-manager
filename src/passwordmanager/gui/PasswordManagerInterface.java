package passwordmanager.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PasswordManagerInterface extends Application {
    Stage window;
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Password Manager");
        Label label = new Label("Logged in!");
        VBox layout = new VBox();
        layout.getChildren().add(label);
        scene = new Scene(layout, 500, 500);
        window.setScene(scene);
        window.show();
    }
}
