package application;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A GUI representation of a food item. Allows the user to tick a checkbox signifying their desire
 * to add the item to the meal.
 * 
 * @author samlo
 *
 */
public class FoodItemView implements Comparable<String>{
    FoodItem foodItem; // backend implementation of the food item
    VBox itemBox; // wraps all components vertically
    VBox nutrientBox; // wraps all nutrients vertically
    CheckBox inMealBox; // signifies if user desires to add the food item to the meal
    Label id; // id of food item
    Label name; // name of food item
    Label cal; // calories of food item
    Label fat; // fat of food item
    Label carbs; // carbohydrates of food item
    Label fiber; // fiber of food item
    Label protein; // protein of food item
    
    /**
     * Constructor
     */
    public FoodItemView() {
        foodItem = null;
        itemBox = new VBox();
        itemBox.setPadding(new Insets(10, 10, 10, 10));
        
        nutrientBox = new VBox();
        nutrientBox.setPadding(new Insets(10, 10, 10, 10));
        
        inMealBox = new CheckBox();
        inMealBox.setText("Add to Meal");
        
        setNutrients();
    }
    
    /**
     * Constructor that corresponds to a given backend food item
     * @param foodItem - food item to be made into a visible representation
     */
    public FoodItemView(FoodItem foodItem) {
        this();
        
        this.foodItem = foodItem;
        
        setNutrients();
        constructNutBox();
        constructItemBox();
    }
    
    /**
     * Creates labels for the nutrients of the backend food item
     */
    private void setNutrients() {
        if(foodItem == null) {
            id = null;
            name = null;
            cal = null;
            fat = null;
            carbs = null;
            fiber = null;
            protein = null;
            
            return;
        }
        
        // TODO: find out what names will be in hashMap
        id = new Label("ID: " + foodItem.getID());
        name = new Label("Name: " + foodItem.getName());
        cal = new Label("Calories: " + foodItem.getNutrientValue("calories"));
        fat = new Label("Fat: " + foodItem.getNutrientValue("fat"));
        carbs = new Label("Carbs: " + foodItem.getNutrientValue("carbohydrates"));
        fiber = new Label("Fiber: " + foodItem.getNutrientValue("fiber"));
        protein = new Label("Protein: " + foodItem.getNutrientValue("protein"));
    }
    
    /**
     * Adds all labels to the nutrient box
     */
    private void constructNutBox() {
        nutrientBox.getChildren().addAll(name, cal, fat, carbs, fiber, protein, id);
    }
    
    /**
     * Adds the nutrient box and check box in one location
     */
    private void constructItemBox() {
        itemBox.getChildren().addAll(inMealBox, nutrientBox);
    }
    
    /**
     * Returns whether the food should be added to a meal or not
     * 
     * @return - true if box is ticked, false otherwise
     */
    public boolean getInMeal() {
        if(inMealBox.isSelected()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Sets if item is in a meal.
     * 
     * @param inMeal
     */
    public void setNotInMeal() {
        inMealBox.setSelected(false);
    }
    
    /**
     * Returns the visual representation of the food item
     * 
     * @return - itemBox containing all labels and boxes
     */
    public VBox getItemBox() {
        return itemBox;
    }
    
    /**
     * Returns the name of the food item
     * 
     * @return - name of food item
     */
    public String getName() {
        return this.name.getText();
    }

    /**
     * Returns the name compared to the passed in name
     */
    @Override
    public int compareTo(String name) {
        return this.name.getText().toLowerCase().compareTo(name.toLowerCase());
    }
    
    
    public FoodItem getFoodItem() {
        return foodItem;
    }
}