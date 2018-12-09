package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FoodData implements FoodDataADT<FoodItem> {

    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;

    // A BPTree with food names as the key to sort foodItems
    private BPTree<String, FoodItem> nameSorted;

    /**
     * Public constructor will not really do anything however until a file is loaded
     */
    public FoodData() {}

    /**
     * A public method with a passed in file name that loads a csv file containing food data This
     * method will load data and save it into various private data structures.
     * 
     * @param file the file's name
     */

    @Override
    public void loadFoodItems(String fileName) {

        // Read the input stream from "fileName"
        FileInputStream fileString;
        try {
            fileString = new FileInputStream(fileName);
            Scanner scan = new Scanner(fileString);

            // Read each line of the file and place the split line array into an the wordList
            // arrayList
            while (scan.hasNextLine()) {
                String[] food = scan.nextLine().split(",");

                if (food.length == 12) { // check if correct number of args
                    HashMap<String, Double> nutrients = new HashMap<String, Double>(); // construct
                    nutrients.put(food[2], Double.parseDouble(food[3]));
                    nutrients.put(food[4], Double.parseDouble(food[5])); // a nutrient hashmap
                    nutrients.put(food[6], Double.parseDouble(food[7]));
                    nutrients.put(food[8], Double.parseDouble(food[9]));
                    nutrients.put(food[10], Double.parseDouble(food[11]));

                    FoodItem newFood = new FoodItem(food[0], food[1], nutrients, false); // new food
                                                                                         // item
                                                                                         // created
                    this.foodItemList.add(newFood); // add food item to the list
                    this.nameSorted.insert(newFood.getName().toLowerCase(), newFood); // add food
                                                                                      // item to
                                                                                      // BPTree

                    // add each food to each of the bp tree
                    this.indexes.get(food[2]).insert(Double.parseDouble(food[3]), newFood);
                    this.indexes.get(food[4]).insert(Double.parseDouble(food[5]), newFood);
                    this.indexes.get(food[6]).insert(Double.parseDouble(food[7]), newFood);
                    this.indexes.get(food[8]).insert(Double.parseDouble(food[9]), newFood);
                    this.indexes.get(food[10]).insert(Double.parseDouble(food[11]), newFood);
                }
            }
            // Close the Scanner and InputStream
            scan.close();
            fileString.close();
        } catch (FileNotFoundException e) {
            System.out.print("could not load food CSV file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("could not close food CSV file");
            e.printStackTrace();
        }
    }

    /**
     * A public method with a passed in file name that saves a csv file containing food data. This
     * method will save the data stored in our total food list
     * 
     * @param file the file's name
     */

    @Override
    public void saveFoodItems(String file) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (FoodItem foodItem : this.nameSorted.rangeSearch("0", ">=")) {
                String formatCSV = foodItem.getID() + "," + foodItem.getName() + "," + "calories"
                    + "," + foodItem.getNutrientValue("calories") + "," + "fat" + ","
                    + foodItem.getNutrientValue("fat") + "," + "carbohydrate" + ","
                    + foodItem.getNutrientValue("carbohydrate") + "," + "fiber" + ","
                    + foodItem.getNutrientValue("fiber") + "," + "protein" + ","
                    + foodItem.getNutrientValue("protein");
                out.println(formatCSV);
            }
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        } catch (IOException ex) {
            System.out.println("Error reading fiel");
        }
    }

    /**
     * A public method that takes the entire food list and filters it down to a list of food items
     * with matching names or querry arguments. This method just checks if the user wants to filter
     * by name or nutrients or both based off their current text field inputs
     * 
     * @param name the food's name (if empty, no input name filters)
     * @param a list of the querry filters (if length == 0 then no input querry args)
     * @return List a list of food with filtered foods
     */

    public List<FoodItem> filter(String name, List<String> filters) {
        if (name.equals("")) {
            return filterByNutrients(filters);
        }
        if (filters.size() == 0) {
            return filterByName(name);
        }
        return intersection(filterByNutrients(filters), filterByName(name));
    }

    /**
     * A public method that takes the entire food list and filters it down to a list of food items
     * with matching names
     * 
     * @param name the food's name
     * @return List a list of food with the filtered name
     */

    @Override
    public List<FoodItem> filterByName(String name) {
        List<FoodItem> matching = new ArrayList<FoodItem>();
        for (FoodItem fooditem : this.foodItemList) {
            if (fooditem.getName().toLowerCase().contains(name.toLowerCase())) {
                matching.add(fooditem);
            }
        }
        return matching;
    }

    /**
     * A public method that takes the entire food list and filters it down to a list of food items
     * that meet the input filter's requirements. This filters must first be decoded and then
     * applied to the main food list and then each output will be intersected with each other
     * leading to an overall list of FoodItem that meet all input arguments.
     * 
     * @param filters a list of filters
     * @return List a list of food with the filter requirements met
     */

    public List<FoodItem> filterByNutrients(List<String> filters) {
        List<FoodItem> currSet = this.foodItemList;
        for (String argument : filters) {
            if (valid(argument)) {
                String[] args = argument.split(" ");
                List<FoodItem> tempSet =
                    indexes.get(args[0]).rangeSearch(Double.parseDouble(args[2]), args[1]);
                currSet = intersection(currSet, tempSet);
            } else {
                System.out.println("invalid command: " + argument);
            }
        }
        return currSet;
    }

    /**
     * A private helper method to find the set intersection of two food sets
     * 
     * @param ListA the sets to be intersected
     * @param ListB
     * @return tmp a list containing the intersect
     */

    private List<FoodItem> intersection(List<FoodItem> setA, List<FoodItem> setB) {
        List<FoodItem> tmp = new ArrayList<FoodItem>();
        for (FoodItem x : setA) // check if each element in one set is in the other
            if (setB.contains(x))
                tmp.add(x); // if in both sets, add to final set
        return tmp;
    }


    /**
     * A private helper method to check if a given input to the filterByNutrients is a valid
     * argument, it just checks formatting of the string. <nutrient> <comparator> <value>
     * 
     * @param argument
     * @return true if a valid argument
     */

    private boolean valid(String argument) {
        String[] theArgs = argument.split(" ");
        if (theArgs.length != 3) { // check if correct number of args
            return false;
        }
        if (theArgs[1] != ">" && theArgs[1] != ">=" && theArgs[1] != "<=" && theArgs[1] != "<") {
            return false; // check if valid comparator
        }
        if (indexes.containsKey(theArgs[0].toLowerCase())) { // check if a valid nutrient
            return false;
        }
        return true; // return true if all tests passed
    }


    @Override
    public void addFoodItem(FoodItem foodItem) {
        this.foodItemList.add(foodItem);

        this.nameSorted.insert(foodItem.getName().toLowerCase(), foodItem);
        HashMap<String, Double> newtable = foodItem.getNutrients();
        this.indexes.get("calories").insert(newtable.get("calories"), foodItem);
        this.indexes.get("fat").insert(newtable.get("fat"), foodItem);
        this.indexes.get("carbohydrate").insert(newtable.get("carbohydrate"), foodItem);
        this.indexes.get("fiber").insert(newtable.get("fiber"), foodItem);
        this.indexes.get("protein").insert(newtable.get("protein"), foodItem);
    }

    public List<FoodItem> getSortedList() {
        return this.nameSorted.rangeSearch("0", ">=");
    }


    @Override
    public List<FoodItem> getAllFoodItems() {
        return this.foodItemList;
    }
}
