package passwordmanager.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import passwordmanager.gui.forms.AuthorizationForm;
import passwordmanager.gui.notifications.ConfirmBox;

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
        AuthorizationForm af = new AuthorizationForm();
        af.start(window);
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
