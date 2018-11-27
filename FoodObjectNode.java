package application;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class FoodObjectNode {
    private ToggleButton InMeal = new ToggleButton("Add to meal?");
    private Label FoodInfo; // label holding food information as a string
    private HBox TheBox = new HBox();
    
    FoodObjectNode(String theString) { // will be FoodItem and need to format in FoodInfo;
        FoodInfo = new Label(theString); // will need to format string
        TheBox.getChildren().addAll(FoodInfo, InMeal);
    }
    
    public HBox getHbox() {
        return TheBox;
    }
}
