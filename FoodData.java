package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class FoodData {

    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    // private HashMap<String, BPTree<Double, FoodItem>> indexes;
    private HashMap<String, String> indexes; // delete after BPTree is made

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
                nutrients.put(food[4], Double.parseDouble(food[5])); // a nutrient hashmap
                nutrients.put(food[6], Double.parseDouble(food[7]));
                nutrients.put(food[8], Double.parseDouble(food[9]));
                nutrients.put(food[10], Double.parseDouble(food[11]));

                FoodItem newFood = new FoodItem(food[1], food[3], nutrients, false); // new food
                                                                                     // item created
                this.foodItemList.add(newFood);
                // FIXME The new FoodItem needs to be added to the data structures with b trees
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

    }

    /**
     * A public method that takes the entire food list and filters it down to a list of food items
     * with matching names
     * 
     * @param name the food's name
     * @return List a list of food with the filtered name
     */

    public List<FoodItem> filterByName(String name) {
        return null;
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
        Set<FoodItem> currSet = (Set<FoodItem>) this.foodItemList;
        for (String argument : filters) {
            if (valid(argument)) {
                // FIXME Set<FoodItem> tempSet = indexes.getQuerySet(argument);
                currSet = intersection(currSet, currSet); // change one to tempSet!!!!!!!!!!!!
            } else {
                System.out.println("invalid command: " + argument);
            }
        }
        return null;
    }

    /**
     * A private helper method to find the set intersection of two food sets
     * 
     * @param setA the sets to be intersected
     * @param setB
     * @return tmp a set containing the intersect
     */

    private Set<FoodItem> intersection(Set<FoodItem> setA, Set<FoodItem> setB) {
        Set<FoodItem> tmp = new TreeSet<FoodItem>();
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
