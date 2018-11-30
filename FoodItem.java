package application;

import java.util.HashMap;

/*
 * Class to hold the basic contents of a single food item in our project
 */

public class FoodItem {
    
    private String Name; // Food name
    private String ID; // Foods unique ID
    private HashMap<String, Double> Nutrients; // a hashmap containing nutrient value pairs
    private boolean InAMeal; // Boolean to tell if in a meal;

    FoodItem(String name, String id, HashMap<String, Double> nutrients, boolean inAMeal) {
        this.Name = name;
        this.ID = id;
        this.Nutrients = nutrients;
        this.InAMeal = inAMeal;
    }

    /*
     * Basic getter methods, pretty self explanatory
     */
    
    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public HashMap<String, Double> getNutrients() {
        return this.Nutrients;
    }

    public Double getNutrientValue(String name) {
        if (Nutrients.containsKey(name)) {
            return Nutrients.get(name);
        }
        return 0.0;
    }
    
    public boolean getInAMeal() {
        return InAMeal;
    }
    
    /*
     * a couple setter methods
     */

    public void addNutrient(String name, Double value) {
        this.Nutrients.put(name, value);
    }

    public void addInAMeal() {
        this.InAMeal = true;
    }

    public void removeInAMeal() {
        this.InAMeal = false;
    }
}
