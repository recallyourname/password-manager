package sample.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static Boolean answer;

    public static boolean display(String title, String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(3);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction(e->{
            answer = true;
            window.close();
        });
        noButton.setOnAction(e->{
            answer = false;
            window.close();
        });
        window.setOnCloseRequest(e->{
            answer = false;
            window.close();
        });
        Label label = new Label();
        label.setText(message);
        GridPane.setConstraints(label, 0,0);
        GridPane.setConstraints(yesButton, 0, 1);
        GridPane.setConstraints(noButton,1,1);
        grid.getChildren().addAll(label, yesButton, noButton);
        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid, 250, 100);
        window.setScene(scene);

        window.showAndWait();

        return answer;
    }
}
