package Application;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Slider {
    private final Stage window;

    public Slider(Stage window) {
        this.window = window;
    }
    
    public  void customization(Scene main){
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        
        cancel.setOnAction(e -> window.setScene(main));
        
        HBox bLayout = new HBox();
        bLayout.getChildren().addAll(ok,cancel);
        
        AnchorPane mainLayout = new AnchorPane();
        
        
    }
    
}
