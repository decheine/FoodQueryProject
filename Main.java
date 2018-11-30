package application;
    
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Creates the basic GUI for Food Query and Meal Analysis
 * 
 * @author Samuel Locke
 *
 */
public class Main extends Application {
    Stage window; // the GUI stage
    Scene scene; // the main GUI scene
    ListView<HBox> foodListView; // visible list of food items
    ListView<HBox> mealListView; // visible list of food items added to meal
    
    /**
     * Creates the left portion of the GUI window.
     * 
     * @return - VBox representing left portion
     */
    public VBox makeLeftPane() {
        // left side of the GUI window
        VBox leftPaneOuter = new VBox(20);

        // top of left side, will contain menu buttons
        VBox topContainer = new VBox(5);
        
        // Vbox containing a label, button, and text field related to file input
        VBox fileInputBox = new VBox(5);
        
        Button loadList = new Button("Load New Food List");
        
        TextField csvFileInput = new TextField();
        csvFileInput.setPromptText("<file name>.csv");
        
        Label inputFileLabel = new Label("Input Food List File:");
        
        fileInputBox.getChildren().addAll(inputFileLabel, csvFileInput, loadList);
        
        // menu buttons for functionality
        Button saveList = new Button("Save Food List");
        Button addFoodItem = new Button("Add New Food Item");
        Button showAllFoodItems = new Button("Show All Food Items (Reset Filters)");
        
        // for spacing out items
        Label spacer0 = new Label("");
        spacer0.setFont(Font.font("Arial", FontWeight.NORMAL, 5));
        
        // add items to the top vbox
        topContainer.getChildren().addAll(fileInputBox, spacer0, saveList, addFoodItem, showAllFoodItems);

        
        // bottom portion of the left side
        VBox botContainer = new VBox(5);

        Label queryLabel = new Label("Food Query");
        queryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        // name filter label and text field
        Label filterLabel = new Label("Name Filter: ");
        TextField filterNameField = new TextField();
        filterNameField.setPromptText("Character sequence e.g. abc");
        filterNameField.setPrefWidth(220);
        
        // nutrient filter label and text area, allowing for multiple filters
        Label nutrientFilterLabel = new Label("Nutrient Filter: ");
        TextArea filterNutrientField = new TextArea();
        filterNutrientField.setPromptText("<nutrient> <comparator> <value>");
        filterNutrientField.setPrefWidth(220);
        
        // applies both kinds of filters
        Button applyQueryButt = new Button("Apply Query");
        
        // for spacing out the different sections
        Label spacer = new Label("");
        spacer.setFont(Font.font("Arial", FontWeight.NORMAL, 5));

        // add items to bottom vbox
        botContainer.getChildren().addAll(queryLabel, filterLabel, filterNameField, spacer, nutrientFilterLabel, filterNutrientField, applyQueryButt);

        // complete left portion of window
        leftPaneOuter.getChildren().addAll(topContainer, botContainer);

        return leftPaneOuter;
    }

    /**
     * Creates a GUI
     * 
     * @param primaryStage - stage for the GUI
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            window = primaryStage;
            window.setTitle("Food Query and Meal Analysis");

            foodListView = new ListView<HBox>();

            // The following is hard coded food items to be used for the development of the GUI
            // for milestone 2 and does not reflect the final product
            
            HBox hbox; // contains a food item's vbox of qualities and a check box for selection
            VBox vbox; // contains a food item's qualities
            CheckBox cb1; // check box marking a food item as selected
            Label foodName = new Label(); // the name of a food item
            Label calories = new Label(); // the number of calories a food item has
            Label fat = new Label(); // the amount of fat a food item has
            Label carbs = new Label(); // the amount of carbs a food item has
            Label fiber = new Label(); // the amount of fiber a food item has
            Label protein = new Label(); // the amount of protein a food item has
            Label id = new Label(); // the unique ID number of the food item
            
            // adds 6 food items to the food list
            for(int i = 0; i < 7; ++i) {
                hbox = new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                
                vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                
                cb1 = new CheckBox();
                cb1.setText("Add to Meal");

                // Hard coded switch statement for generating a list of food items
                switch(i) {
                    case 0 :
                        foodName = new Label("Name: Banana");
                        calories = new Label("Calories: 2");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000004");
                        break;
                    case 1 : 
                        foodName = new Label("Name: Burger");
                        calories = new Label("Calories: 1000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000000");
                        break;
                    case 2 : 
                        foodName = new Label("Name: Fries");
                        calories = new Label("Calories: 2000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000002");
                        break;
                    case 3 : 
                        foodName = new Label("Name: Red Pepper");
                        calories = new Label("Calories: 17");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000007");
                        break;
                    case 4 : 
                        foodName = new Label("Name: Soda");
                        calories = new Label("Calories: 10000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000003");
                        break;
                    case 5 : 
                        foodName = new Label("Name: Steak");
                        calories = new Label("Calories: 2000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000006");
                        break;
                    case 6 : 
                        foodName = new Label("Name: Tomato");
                        calories = new Label("Calories: 7");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000005");
                        break;
                }
                
                vbox.getChildren().addAll(foodName, calories, fat, carbs, fiber, protein, id);
                
                hbox.getChildren().addAll(vbox, cb1);
                
                foodListView.getItems().add(hbox);
            }
            
            // does the same thing for the meal list
            mealListView = new ListView<HBox>();
            
            for(int i = 0; i < 2; ++i) {
                hbox = new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                
                vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                
                cb1 = new CheckBox();
                cb1.setText("Remove From Meal");
                
                vbox.getChildren().add(cb1);
                
                switch(i) {
                    case 0 :
                        foodName = new Label("Name: Banana");
                        calories = new Label("Calories: 2");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000004");
                        break;
                    case 1 : 
                        foodName = new Label("Name: Burger");
                        calories = new Label("Calories: 1000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000000");
                        break;
                }
                
                vbox.getChildren().addAll(foodName, calories, fat, carbs, fiber, protein, id);
                
                hbox.getChildren().addAll(vbox, cb1);
                
                mealListView.getItems().add(hbox);
            }

            // layout of GUI
            GridPane root = new GridPane();

            root.setPadding(new Insets(10, 10, 10, 10));
            root.setHgap(10);
            root.setVgap(10);

            // menu label
            Label labelTitle = new Label("Menu");
            labelTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            
            // food list labels and button
            Label foodListTitle = new Label("Food List");
            foodListTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            Label counter = new Label("Number of Items: 6");
            
            Button addToMealButt = new Button("Add Selected To Meal");
            
            // meal list labels and buttons
            Label mealListTitle = new Label("Meal List");
            mealListTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            Label counter2 = new Label("Number of Items: 2");

            Button analyzeMealButt = new Button("Analyze Meal");
            Button removeFromMealButt = new Button("Remove Selected From Meal");

            
            root.add(labelTitle, 0, 0);

            VBox leftBar = makeLeftPane();
            root.add(leftBar, 0, 1);

            // adding food list, its labels, and button to the center of the window
            root.add(foodListTitle, 1, 0, 1, 1);
            root.add(foodListView, 1, 1, 2, 1);
            root.add(addToMealButt, 2, 0);
            root.add(counter, 1, 2, 2, 1);
            
            // adding meal list, its labels, and buttons to the right of the window
            root.add(mealListTitle, 3, 0, 1, 1);
            root.add(mealListView, 3, 1, 3, 1);
            root.add(analyzeMealButt, 4, 0, 1, 1);
            root.add(removeFromMealButt, 5, 0);
            root.add(counter2, 3, 2, 2, 1);

            scene = new Scene(root, 875, 500);
            window.setScene(scene);
            window.show();  
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Main method.
     * 
     * @param args - currently unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
