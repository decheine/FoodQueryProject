/**
 * Filename:   SaveFile.java
 * Project:    Final Project
 * Authors:    Nick Decheine, Samuel Locke
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    002
 * 
 * Due Date:   Before 10pm on December 12, 2018
 * Version:    1.0
 * 
 * Credits:    None
 * 
 * Bugs:       None
 */

package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Creates a new window for saving a list of food items to a given file name
 * 
 * @author Nick Decheine, Samuel Locke
 */
public class SaveFile {
    private final Stage dialog; // stage for this window
    private VBox windowBox; // contains all the input fields
    
    /**
     * Public constructor
     * 
     * @param primaryStage - the primary stage the program is running on
     */
    public SaveFile(Stage primaryStage, FoodDataADT<FoodItem> foodData) {
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
