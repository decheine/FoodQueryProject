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
 * Creates a new window for allowing the input of a new food item's attributes.
 * 
 * @author samlo
 *
 */
public class AddFoodPopUp {
    private final Stage dialog; // stage for this window
    private VBox boxOfTexts; // contains all the input fields
    private FoodItem item; // the food item to be created
    private EventHandler<ActionEvent> e;
    
    /**
     * Constructor
     * 
     * @param primaryStage - the primary stage the program is running on
     */
    public AddFoodPopUp(Stage primaryStage, Button submitButt, Button cancelButt) {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        
        boxOfTexts = new VBox(10);
        boxOfTexts.setPadding(new Insets(10, 10, 10, 10));
        
        // adds all label / field pairs to the window
        boxOfTexts.getChildren().addAll(
            createEntryOption("Name: ", "<name e.g. banana>"),
            createEntryOption("Calories: ", "<calories e.g. 100.5>"),
            createEntryOption("Fat: ", "<fat e.g. 15.0>"),
            createEntryOption("Carbs: ", "<carbs e.g. 2.6>"),
            createEntryOption("Fiber: ", "<fiber e.g. 55.0>"),
            createEntryOption("Protein: ", "<protein e.g. 7.8>"),
            createEntryOption("ID: ", "<ID e.g. 0000000000>")
            );

        HBox buttBox = new HBox(10);
        buttBox.getChildren().addAll(cancelButt, submitButt);
        
        boxOfTexts.getChildren().add(buttBox);
        
        Scene dialogScene = new Scene(boxOfTexts, 250, 300);
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
    private HBox createEntryOption(String name, String prompt) {
        HBox entry = new HBox();
        
        entry = new HBox();
        Label entryLabel = new Label(name);
        TextField entryField = new TextField();
        entryField.setPromptText(prompt);
        entry.getChildren().addAll(entryLabel, entryField);
        
        return entry;
    }
    
    /**
     * Returns the new food item created by the text field inputs after submit button is clicked
     */
    public FoodItem getNewItem() {
        String name = ((TextField) ((HBox) boxOfTexts.getChildren().get(0)).getChildren().get(1)).getText();
        String id = ((TextField) ((HBox) boxOfTexts.getChildren().get(6)).getChildren().get(1)).getText();;
        
        Double cal = Double.parseDouble(((TextField) ((HBox) boxOfTexts.getChildren().get(1)).getChildren().get(1)).getText());
        Double fat = Double.parseDouble(((TextField) ((HBox) boxOfTexts.getChildren().get(2)).getChildren().get(1)).getText());
        Double carbs = Double.parseDouble(((TextField) ((HBox) boxOfTexts.getChildren().get(3)).getChildren().get(1)).getText());
        Double fiber = Double.parseDouble(((TextField) ((HBox) boxOfTexts.getChildren().get(4)).getChildren().get(1)).getText());
        Double protein = Double.parseDouble(((TextField) ((HBox) boxOfTexts.getChildren().get(5)).getChildren().get(1)).getText());
        
        item = new FoodItem(id, name);
        item.addNutrient("calories", cal);
        item.addNutrient("fat", fat);
        item.addNutrient("carbohydrates", carbs);
        item.addNutrient("fiber", fiber);
        item.addNutrient("protein", protein);
        
        closeWindow();
        return item;
    }
    
    /**
     * 
     */
    public void closeWindow() {
        dialog.close();
    }
}
