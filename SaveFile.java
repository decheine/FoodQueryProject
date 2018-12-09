
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
 * @author samlo
 *
 */
public class SaveFile {
    final Stage dialog; // stage for this window
    VBox boxOfTexts; // contains all the input fields
    
    /**
     * Constructor
     * 
     * @param primaryStage - the primary stage the program is running on
     */
    public SaveFile(Stage primaryStage, FoodData foodData) {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        
        HBox buttHBox = new HBox(30);
        boxOfTexts = new VBox(10);
        boxOfTexts.setPadding(new Insets(10, 10, 10, 10));
        
        Button submitButt = new Button("Save"); // for submitting
        Button cancelButt = new Button("Cancel"); // for cancelling
      //  Node textField = saveBox.getChildren().get(0);
        
        HBox saveBox = new HBox();
        
        saveBox = new HBox(30);
        Label entryLabel = new Label("Save as: ");
        TextField entryField = new TextField();
        entryField.setPromptText("<name e.g. filename.csv>");
        saveBox.getChildren().addAll(entryLabel, entryField);
        
        //Label saveLabel = new Label("Save as: ");
       // textField.setPromptText("<name e.g. filename>");
        EventHandler<ActionEvent> saveButtonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	// here is the action of the save button
            	// call 
            	System.out.println("pressed");
                System.out.println(entryField.getText());
                save(entryField.getText()+".csv", foodData);
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

        
        
        
        buttHBox.getChildren().addAll( cancelButt, submitButt);
        HBox saveField = new HBox(5);
        //saveField.getChildren().addAll(saveLabel, textField);
        boxOfTexts.getChildren().addAll(saveBox, buttHBox);
        
        Scene dialogScene = new Scene(boxOfTexts, 300, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    /**
     * Creates a label / field pairing in an HBox
     * 
     * @param name - the text of the label
     * @param prompt - the prompt text in the field
     * @return - an HBox containing a label and a TextField
     */
    private void save(String filename, FoodData foodData) {
    	foodData.saveFoodItems(filename);
    }
    private HBox createEntryOption(String name, String prompt) {
        HBox entry = new HBox();
        
        entry = new HBox();
        Label entryLabel = new Label(name);
        TextField entryField = new TextField();
        entryField.setPromptText(prompt);
        entry.getChildren().addAll(entryLabel, entryField);
        
        return entry;
    }
}
