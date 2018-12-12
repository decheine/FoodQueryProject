/**
 * Filename:   FoodListView.java
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Provies functionality for a GUI representation of the list of food items added to the program
 * 
 * @author Sam Locke
 *
 */
public class FoodListView {
    private ListView<VBox> list; // contains a VBox for each food item that lists its properties 
                                 // along with a checkbox for it to be selected and added to a meal
    
    private ArrayList<FoodItemView> itemList; // Backend functionality of the food item 
                                              // representation, allows for checking if a food item 
                                              // has been selected
    
    /**
     * Public constructor
     */
    public FoodListView() {
        list = new ListView<VBox>();
        itemList = new ArrayList<FoodItemView>();
    }
    
    /**
     * Adds a new visible food item to the list, along with its backend, functional representation
     * to the item list
     * 
     * @param item - item to be added
     */
    public void addItem(FoodItemView item) {
        // finds appropriate index for it to be inserted and maintain alphabetical order
        int index = Collections.binarySearch(itemList, item.getName());
        
        // Collections.binarySearch returns (-index - 1) when item is not in list and index is 
        // where the key would have been
        if(index < 0) { 
            index = ((-1 * index) - 1);
        }
        
        itemList.add(index, item);
        list.getItems().add(index, item.getItemBox());
    }
    
    /**
     * Changes the food list view to represent the new list of food items
     * 
     * @param newList - new list to represent
     */
    public void newList(List<FoodItem> newList) {
        if(newList == null) {
            return;
        }
        
        // this.itemList = new ArrayList<FoodItemView>();
        this.list.getItems().clear();
        this.itemList.clear();
        
        for(FoodItem item : newList) {
            FoodItemView itemView = new FoodItemView(item);
            addItem(itemView);
        }
    }
    
    /**
     * Returns a ListView of the visible food items
     * 
     * @return - ListView of visible food items
     */
    public ListView<VBox> getList() {
        return list;
    }
    
    /**
     * Adds all ticked FoodItemView objects in this FoodListView to a passed in FoodListView. 
     * Unticks all ticked objects after they are added to the passed in FoodListView.
     * 
     * @return - FoodListView of checked FoodItemView objects
     */
    public void addCheckedItemsToMeal(FoodListView mealList) {
        for(FoodItemView item : itemList) {
            if(item.getInMeal()) {
                FoodItemView tempItem = new FoodItemView(item.getFoodItem());
                mealList.addItem(tempItem);
                item.setNotInMeal();
            }
        }
    }
    
    /**
     * Removes all checked items from the GUI representation of the list
     */
    public void removeCheckedItems() {
        int size = itemList.size();

        for(int i = 0; i < size; ++i) {
            FoodItemView item = itemList.get(i);
            if(item.getInMeal()) {
                list.getItems().remove(item.getItemBox());
                itemList.remove(item);
                --size;
                --i;
            }
        }
    }

    /**
     * @return - a list of the combined nutrient values of the food items in this FoodListView
     */
    public List<Double> analyzeList() {
        // 0 = calories, 1 = fat, 2 = carbohydrates, 3 = fiber, 4 = protein
        ArrayList<Double> statList = new ArrayList<Double>();
        
        if(itemList == null || itemList.size() <= 0) {
            return statList;
        }
        
        for(FoodItemView itemView : itemList) {
            FoodItem tempItem = itemView.getFoodItem();
            
            if(statList.size() >= 5) {
                statList.set(0, (statList.get(0) + tempItem.getNutrientValue("calories")));
                statList.set(1, (statList.get(1) + tempItem.getNutrientValue("fat")));
                statList.set(2, (statList.get(2) + tempItem.getNutrientValue("carbohydrate")));
                statList.set(3, (statList.get(3) + tempItem.getNutrientValue("fiber")));
                statList.set(4, (statList.get(4) + tempItem.getNutrientValue("protein")));
            }
            else {
                statList.add(tempItem.getNutrientValue("calories"));
                statList.add(tempItem.getNutrientValue("fat"));
                statList.add(tempItem.getNutrientValue("carbohydrate"));
                statList.add(tempItem.getNutrientValue("fiber"));
                statList.add(tempItem.getNutrientValue("protein"));
            }
        }
        
        return statList;
    }
}
