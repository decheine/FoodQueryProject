package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * GUI representation of the list of food in FoodData
 * 
 * @author samlo
 *
 */
public class FoodListView {
    ListView<VBox> list; // list of visible food items
    ArrayList<FoodItemView> itemList;
    
    /**
     * Constructor
     */
    public FoodListView() {
        list = new ListView<VBox>();
        itemList = new ArrayList<FoodItemView>();
    }
    
    /**
     * Adds a new visible food item to the list
     * 
     * @param item - item to be added
     */
    public void addItem(FoodItemView item) {
        // finds appropriate index for it to be inserted and maintain alphabetical order
        int index = Collections.binarySearch(itemList, item.getName());
        
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
        this.itemList = new ArrayList<FoodItemView>();
        
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
     * Returns a ListView of all FoodItemView objects whose check box is ticked. Unticks all ticked
     * objects after they are added to the ListView.
     * 
     * @return - ListView of checked FoodItemView objects
     */
    public void addCheckedItemsToMeal(FoodListView mealList) {
        for(FoodItemView item : itemList) {
            if(item.getInMeal()) {
                FoodItemView tempItem = new FoodItemView(item.getFoodItem());
                
                System.out.println("fired0");
                mealList.addItem(tempItem);
                item.setNotInMeal();
            }
        }
    }
    
    /**
     * Removes all checked items from the GUI representation of the list. (Used only for a meal).
     * 
     */
    public void removeCheckedItems() {
        for(int i = 0; i < itemList.size(); ++i) {
            FoodItemView item = itemList.get(i);
            if(item.getInMeal()) {
                itemList.remove(item);
                list.getItems().remove(item.getItemBox());
                item.setNotInMeal();
            }
        }
    }

    //public FoodItem
    // TODO: add more functionality
}
