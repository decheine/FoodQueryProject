/**
 * Filename:   FoodItemView.java
 * Project:    Final Project
 * Authors:    Samuel Locke
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

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A GUI representation of a food item. Allows the user to tick a checkbox signifying their desire
 * to add the item to the meal. Has a VBox that lists its nutrient values and whether it has been
 * selected or not.
 * 
 * @author Samuel Locke
 */
public class FoodItemView implements Comparable<String>{
    private FoodItem foodItem; // backend implementation of the food item
    private VBox itemBox; // wraps all components vertically
    private VBox nutrientBox; // wraps all nutrients vertically
    private CheckBox inMealBox; // signifies if user desires to add the food item to the meal
    private Label id; // id of food item
    private Label name; // name of food item
    private Label cal; // calories of food item
    private Label fat; // fat of food item
    private Label carbs; // carbohydrates of food item
    private Label fiber; // fiber of food item
    private Label protein; // protein of food item
    
    /**
     * Public constructor
     */
    public FoodItemView() {
        foodItem = null;
        itemBox = new VBox();
        itemBox.setPadding(new Insets(10, 10, 10, 10));
        
        nutrientBox = new VBox();
        nutrientBox.setPadding(new Insets(10, 10, 10, 10));
        
        inMealBox = new CheckBox();
        inMealBox.setText("Select Item");
        
        setNutrients();
    }
    
    /**
     * Constructor that corresponds to a given food item
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
     * Creates labels for the nutrients of the food item
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
        
        id = new Label("ID: " + foodItem.getID());
        name = new Label("Name: " + foodItem.getName());
        cal = new Label("Calories: " + foodItem.getNutrientValue("calories"));
        fat = new Label("Fat: " + foodItem.getNutrientValue("fat"));
        carbs = new Label("Carbs: " + foodItem.getNutrientValue("carbohydrate"));
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
    
    /**
     * @return - the food item this FoodItemView is representing.
     */
    public FoodItem getFoodItem() {
        return foodItem;
    }
}
