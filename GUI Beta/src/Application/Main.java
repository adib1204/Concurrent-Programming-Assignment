package Application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Main extends Application {

    private final Sound s0 = new Sound();
    public Sound getIntro(){
        return s0;
    }

    @Override
    public void start(Stage primaryStage) {
        final Stage window = primaryStage;
        
        Button start = new Button("Start ");
        Button exit = new Button("Exit");
        
        start.setId("HomeButton");
        exit.setId("HomeButton");
        start.setOnAction(e -> {
            Sound startClip = new Sound("start");
            s0.fadeSound(s0);
            startClip.playSound(startClip);
            notification();
            s0.loudSound(s0);
        });
        exit.setOnAction(e -> {
            closeProgram(window);
        });
        
        VBox vLayout = new VBox();
        vLayout.getChildren().addAll(start, exit);
        vLayout.setSpacing(25.0);
        
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().add(vLayout);
        AnchorPane.setBottomAnchor(vLayout, 38.0);
        AnchorPane.setRightAnchor(vLayout, 5.0);

        Scene scene1 = new Scene(mainLayout, 800, 600);
        scene1.getStylesheets().add("Application/CSS/Application.css");

        window.getIcons().add(new Image("/Application/Image/icon.png"));
        window.isResizable();
        window.centerOnScreen();
        window.setScene(scene1);
        window.setTitle("Traffic Signal Controller");
        window.show();
    }

    public void notification() {
        Stage win = new Stage();
        win.initModality(Modality.APPLICATION_MODAL);

        Text info1 = new Text("THIS PROGRAM IS FOR  ");
        Text info2 = new Text("EXTRA MARKS ");
        info2.setStyle("-fx-strikethrough:true; -fx-font-style:italic");
        Text info3 = new Text("EDUCATIONAL PURPOSE ONLY!!!");
        TextFlow info = new TextFlow(info1, info2, info3);
        info.setStyle("-fx-text-alignment:center");
        String lo = info.toString();

        Button ok = new Button("OK");
        ok.setOnAction(e -> {
            win.close();
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(info, ok);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(8.0);
        Scene scene2 = new Scene(vbox, 300, 100);

        win.getIcons().add(new Image("Application/Image/WarningIcon.png"));
        win.setScene(scene2);
        win.setTitle("In Construction");
        win.showAndWait();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void closeProgram(Stage window) {
        Boolean answer = ConfirmationBox.display("Really!!", "Are you sure you want to quit?");
        if (answer) {
            Sound exitClip = new Sound("Exit");
            window.close();
        } else {
        }
    }

}
