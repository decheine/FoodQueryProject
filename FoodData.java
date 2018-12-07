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
import java.util.Set;
import java.util.TreeSet;

public class FoodData {

    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;

    // The Querry list of food items
    private List<FoodItem> littleList;

    /**
     * Public constructor will not really do anything however until a file is loaded
     */
    public FoodData() {

    }


    /**
     * A public method with a passed in file name that loads a csv file containing food data This
     * method will load data and save it into various private data structures.
     * 
     * @param file the file's name
     */

    public void loadFoodItems(String fileName) throws IOException {

        // Read the input stream from "fileName"
        FileInputStream fileString = new FileInputStream(fileName);
        Scanner scan = new Scanner(fileString);

        // Read each line of the file and place the split line array into an the wordList arrayList
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
                                                                                     // item created
                this.foodItemList.add(newFood); // add food item to the list

                // add each food to each of the bp tress
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
    }

    /**
     * A public method with a passed in file name that saves a csv file containing food data. This
     * method will save the data stored in our total food list
     * 
     * @param file the file's name
     */

    public void saveFoodItems(String file) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (FoodItem foodItem : littleList) {
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
     * with matching names
     * 
     * @param name the food's name
     * @return List a list of food with the filtered name
     */

    public List<FoodItem> filterByName(String name) {
        List<FoodItem> matching = new ArrayList<FoodItem>();
        for (FoodItem fooditem : this.foodItemList) {
            if (fooditem.getName().equals(name)) {
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
        @SuppressWarnings("unchecked")
        List<FoodItem> currSet = this.foodItemList;
        for (String argument : filters) {
            if (valid(argument)) {
                String[] args = argument.split(" ");
                List<FoodItem> tempSet = indexes.get(args[0]).rangeSearch(args[2], args[1]);
                currSet = intersection(currSet, tempSet); // change one to tempSet!!!!!!!!!!!!
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

}
