
import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Modality;
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
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;

/**
 * Creates the basic GUI for Food Query and Meal Analysis
 * 
 * @author Samuel Locke
 * @author Nick Decheines
 *
 */
public class Main extends Application {
    private Stage window; // the GUI stage
    private Scene scene; // the main GUI scene
    private FoodListView foodListView; // visible list of food items
    private FoodListView mealListView; // visible list of food items added to meal
    private FoodData foodData;
    Label counterLabel; //added
    int counter; 
    
    public VBox getLeftPane() {
        // left side of the GUI window
        VBox leftPaneOuter = new VBox(20);

        // top of left side, will contain menu buttons
        VBox topContainer = new VBox(5);
        
        // Vbox containing a label, button, and text field related to file input
        VBox fileInputBox = new VBox(5);
        // BEGIN EDITS
        Button loadList = new Button("Load New Food List");
      
        //loadList event handler
        TextField csvFileInput = new TextField();
        csvFileInput.setPromptText("<file name>.csv");
        
        EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                foodData.loadFoodItems(csvFileInput.getText()); // TODO: change this
                for( FoodItem item : foodData.getAllFoodItems()) {
                	//System.out.println("ID               " + item.getID());
                	foodListView.addItem(new FoodItemView(item));
                }
            } 
        }; 
        loadList.setOnAction(loadEvent);
        
        Label inputFileLabel = new Label("Input Food List File:");
        
        fileInputBox.getChildren().addAll(inputFileLabel, csvFileInput, loadList);
        
        // menu buttons for functionality
        Button saveList = new Button("Save Food List");
        
        
        EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
               SaveFile fileWindow = new SaveFile(window, foodData);
            } 
        }; 
        // END EDITS
        
        loadList.setOnAction(loadEvent);
        saveList.setOnAction(saveEvent);
        
        Button addFoodItem = new Button("Add New Food Item");
        EventHandler<ActionEvent> addFoodItemEvent = new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent e) {
                Button submitButt = new Button("Submit");
                Button cancelButt = new Button("Cancel");
                AddFoodPopUp addFood = new AddFoodPopUp(window, submitButt, cancelButt);
                
                EventHandler<ActionEvent> submitEvent = new EventHandler<ActionEvent>() { 
                    @Override
                    public void handle(ActionEvent e2) {
                        // added this section from SamsMain
                        FoodItem item;
                        try {
                            item = addFood.getNewItem();

                            foodData.addFoodItem(item);
                            foodListView.addItem(new FoodItemView(item));
                            counterLabel.setText("Number of Items: " + (++counter));
                        }
                        catch(NumberFormatException e) {
                            // TODO: add something?
                        }
                    } 
                }; 
                
                submitButt.setOnAction(submitEvent);
                
                EventHandler<ActionEvent> cancelEvent = new EventHandler<ActionEvent>() { 
                    @Override
                    public void handle(ActionEvent e3) {

                        addFood.closeWindow();
                    } 
                }; 
                
                cancelButt.setOnAction(cancelEvent);
            } 
        }; 
        
             
        Button showAllFoodItems = new Button("Show All Food Items (Reset Filters)");
        EventHandler<ActionEvent> showAllFoodItemsEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                
              foodListView.newList(foodData.getAllFoodItems());   
            } 
        }; 
        
        addFoodItem.setOnAction(addFoodItemEvent);
        showAllFoodItems.setOnAction(showAllFoodItemsEvent);
        
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
        EventHandler<ActionEvent> applyQueryEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                String name = filterNameField.getAccessibleText();
            	String temp = filterNutrientField.getText();
            	String [] f2 = temp.split("\n");
            	
                foodListView.newList(foodData.filter(name, Arrays.asList(f2)));
               
            } 
        }; 
        applyQueryButt.setOnAction(applyQueryEvent);
        
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
            
            foodData = new FoodData();

            foodListView = new FoodListView();
            mealListView = new FoodListView();

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
            counterLabel = new Label("Number of Items: " + counter); //changed
            
            Button addToMealButt = new Button("Add Selected To Meal");
             //unsure on how to add multiple checked food Items              
            EventHandler<ActionEvent> addToMealEvent = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    foodListView.addCheckedItemsToMeal(mealListView);
                } 
            }; 
            addToMealButt.setOnAction(addToMealEvent);
                        
            // meal list labels and buttons
            Label mealListTitle = new Label("Meal List");
            mealListTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            Label counter2 = new Label("Number of Items: 2");

            Button analyzeMealButt = new Button("Analyze Meal");
            EventHandler<ActionEvent>analyzeMealEvent = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    AnalyzeMealPopUp analyzeMeal = new AnalyzeMealPopUp(window, mealListView.analyzeList());
                    // implemented
                } 
            }; 
            analyzeMealButt.setOnAction(analyzeMealEvent);
            
            
            
            Button removeFromMealButt = new Button("Remove Selected From Meal");

            EventHandler<ActionEvent> removeFromMealEvent = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                 mealListView.removeCheckedItems();
                 
                } 
            }; 
            removeFromMealButt.setOnAction(removeFromMealEvent);
            
            VBox leftPaneOuter = getLeftPane();
            root.add(labelTitle, 0, 0);
            root.add(leftPaneOuter, 0, 1);

            // adding food list, its labels, and button to the center of the window
            root.add(foodListTitle, 1, 0, 1, 1);
            root.add(foodListView.getList(), 1, 1, 2, 1);
            root.add(addToMealButt, 2, 0);
            root.add(counterLabel, 1, 2, 2, 1);
            
            // adding meal list, its labels, and buttons to the right of the window
            root.add(mealListTitle, 3, 0, 1, 1);
            root.add(mealListView.getList(), 3, 1, 3, 1);
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