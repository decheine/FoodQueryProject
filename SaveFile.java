package application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Creates a new window for allowing the input of a new food item's attributes.
 * 
 * @author Nick Decheine
 *
 */
public class SaveFile {
    final Stage dialog; // stage for this window
    VBox windowBox; // contains all the input fields
    
    /**
     * Constructor
     * 
     * @param primaryStage - the primary stage the program is running on
     */
    public SaveFile(Stage primaryStage, FoodData foodData) {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        
        HBox buttHBox = new HBox(10);
        windowBox = new VBox(10);
        windowBox.setPadding(new Insets(10, 10, 10, 10));
        
        Button submitButt = new Button("Save"); // for submitting
        Button cancelButt = new Button("Cancel"); // for canceling
        
        HBox saveBox = new HBox(10);
        
        Label entryLabel = new Label("Save File Name: ");
        
        TextField entryField = new TextField();
        entryField.setPromptText("<name e.g. filename.csv>");
        saveBox.getChildren().addAll(entryLabel, entryField);
        
        EventHandler<ActionEvent> saveButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // here is the action of the save button
                
                // save(entryField.getText()+".csv", foodData); // TODO: need to add on?
                foodData.saveFoodItems(entryField.getText());
                dialog.close();
            }
        };
        
        EventHandler<ActionEvent> cancelButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                dialog.close();
            }
        };
        
        submitButt.setOnAction(saveButtonEvent);
        cancelButt.setOnAction(cancelButtonEvent);

        buttHBox.getChildren().addAll(cancelButt, submitButt);

        windowBox.getChildren().addAll(saveBox, buttHBox);
        
        Scene dialogScene = new Scene(windowBox, 275, 75);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
