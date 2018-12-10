
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // list of all the food items.
    private List<FoodItem> foodItemList;

    // map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    // a BPTree with food names as the key to sort foodItems
    private BPTree<String, FoodItem> nameSorted;
    
    // branching factor of each of the BPTrees in indexes
    private static final int BRANCHINGFACTOR = 3;
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        nameSorted = new BPTree<String, FoodItem>(BRANCHINGFACTOR);
        indexes.put("calories", new BPTree<Double, FoodItem>(BRANCHINGFACTOR));
        indexes.put("fat", new BPTree<Double, FoodItem>(BRANCHINGFACTOR));
        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(BRANCHINGFACTOR));
        indexes.put("fiber", new BPTree<Double, FoodItem>(BRANCHINGFACTOR));
        indexes.put("protein", new BPTree<Double, FoodItem>(BRANCHINGFACTOR));
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
        // Read the input stream from "fileName"
        FileInputStream fileString;
        
        // clear the current food
        
        try {
            fileString = new FileInputStream(filePath);
        } catch (FileNotFoundException e1) {
        	//Path currentRelativePath = Paths.get("");
        	//String s = currentRelativePath.toAbsolutePath().toString();
        	//System.out.println("input path " + filePath);
        	//System.out.println(System.getProperty("user.dir"));
        	//Path path = FileSystems.getDefault().getPath(filePath);
        	//System.out.println("path: " + path.toString());
        	System.out.println("File not found.");
            return;
        }
        Path path = FileSystems.getDefault().getPath(filePath);
    	//System.out.println("path: " + path.toString());
        
        Scanner scan = new Scanner(fileString);

        // Read each line of the file and place the split line array into an the wordList arrayList
        while (scan.hasNextLine()) {
            String[] foodFileArray = scan.nextLine().split(",");
// TODO Implement a more rigorous check for the format of each line
            if (foodFileArray.length == 12) { // check if correct number of args
            	//System.out.println("correct args");
                FoodItem item = new FoodItem(foodFileArray[0], foodFileArray[1]);
                
                item.addNutrient(foodFileArray[2], Double.parseDouble(foodFileArray[3]));
                item.addNutrient(foodFileArray[4], Double.parseDouble(foodFileArray[5]));
                item.addNutrient(foodFileArray[6], Double.parseDouble(foodFileArray[7]));
                item.addNutrient(foodFileArray[8], Double.parseDouble(foodFileArray[9]));
                item.addNutrient(foodFileArray[10], Double.parseDouble(foodFileArray[11]));

                this.foodItemList.add(item); // add food item to the list
                
                // add each food to each of the bp tress
                this.indexes.get(foodFileArray[2]).insert(Double.parseDouble(foodFileArray[3]), item);
                this.indexes.get(foodFileArray[4]).insert(Double.parseDouble(foodFileArray[5]), item);
                this.indexes.get(foodFileArray[6]).insert(Double.parseDouble(foodFileArray[7]), item);
                this.indexes.get(foodFileArray[8]).insert(Double.parseDouble(foodFileArray[9]), item);
                this.indexes.get(foodFileArray[10]).insert(Double.parseDouble(foodFileArray[11]), item);
                //actually add the food item
                
            }
        }
        // Close the Scanner and InputStream
        scan.close();
        
        try {
            fileString.close();
        } catch (IOException e) {
            return;
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
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

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FoodItem> filterByNutrients(List<String> rules) {
        List<FoodItem> currSet = this.foodItemList;
        for (String argument : rules) {
            if (valid(argument)) {
                String[] args = argument.split(" ");
                List<FoodItem> tempSet = indexes.get(args[0]).rangeSearch(Double.parseDouble(args[2]), (String) args[1]);
                currSet = intersection(currSet, tempSet);
            } else {
                System.out.println("invalid command: " + argument);
            }
        }
        return currSet;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        this.foodItemList.add(foodItem); // add food item to the list

        this.nameSorted.insert(foodItem.getName().toLowerCase(), foodItem);
        
        // add each food to each of the bp tress
        this.indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
        this.indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
        this.indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
        this.indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
        this.indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }

    // "carbohydrate" must be changed to "carbohydrate"
    @Override
    public void saveFoodItems(String filename) {
        try {
        	File file = new File(filename);
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            System.out.println("fIL: "+foodItemList);
			if (!foodItemList.isEmpty()) {
				for (FoodItem foodItem : this.nameSorted.rangeSearch("0", ">=")) {
					String formatCSV = foodItem.getID() + "," + foodItem.getName() + "," + "calories" + ","
							+ foodItem.getNutrientValue("calories") + "," + "fat" + ","
							+ foodItem.getNutrientValue("fat") + "," + "carbohydrate" + ","
							+ foodItem.getNutrientValue("carbohydrate") + "," + "fiber" + ","
							+ foodItem.getNutrientValue("fiber") + "," + "protein" + ","
							+ foodItem.getNutrientValue("protein");
					out.println(formatCSV);
				}
				out.close();
			}
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }
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
     * @return - a list of food items sorted alphabetically by their name.
     */
    public List<FoodItem> getSortedList() {
        return this.nameSorted.rangeSearch("", ">="); // TODO: smallest ascii character?
    }
}
