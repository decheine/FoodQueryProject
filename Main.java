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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
    Stage window;
    Scene scene;
    ListView<HBox> foodListView2;
    ListView<HBox> mealListView2;
    
    public VBox makeLeftPane() {
        VBox leftPaneOuter = new VBox(2);
        leftPaneOuter.setPadding(new Insets(10));
        leftPaneOuter.setSpacing(8);

        // create vbox with buttons on top

        VBox topContainer = new VBox();
        // this creates the buttons with the respective labels.
        Button loadList = new Button("Load New Food List");
        Button saveList = new Button("Save Food List");
        Button viewPlan = new Button("View Meal Plan");
        // add buttons to the top vbox
        topContainer.getChildren().addAll(loadList, saveList, viewPlan);

        VBox botContainer = new VBox(); // bottom container

        Text queryLabel = new Text("Food Query");
        Label filterLabel = new Label("Filter Name: ");
        TextField filterNameField = new TextField();
        Label nutrientFilterLabel = new Label("Nutrient Filter: ");
        TextField filterNutrientField = new TextField();
        Button applyFilter = new Button("Apply Filters");

        botContainer.getChildren().addAll(queryLabel, filterLabel, filterNameField, nutrientFilterLabel,
                filterNutrientField, applyFilter);

        leftPaneOuter.getChildren().addAll(
                topContainer,
                botContainer
                );

        for (int i = 0; i < 3; i++) {
            // leftPaneOuter.setMargin(, new Insets(0, 0, 0, 8));
            // leftPaneOuter.getChildren().add();
        }

        return leftPaneOuter;
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            window = primaryStage;
            window.setTitle("Food Query and Meal Analysis");

            foodListView2 = new ListView<HBox>();

            HBox hbox;
            VBox vbox;
            CheckBox cb1;
            Label foodName = new Label();
            Label calories = new Label();
            Label fat = new Label();
            Label carbs = new Label();
            Label fiber = new Label();
            Label protein = new Label();
            Label id = new Label();
            
            for(int i = 0; i < 8; ++i) {
                hbox = new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                
                vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                
                cb1 = new CheckBox();
                cb1.setText("Add to Meal");
                
                vbox.getChildren().add(cb1);
                
                switch(i) {
                    case 0 : 
                        foodName = new Label("Name: Burger");
                        calories = new Label("Calories: 1000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000000");
                        break;
                    case 1 : 
                        foodName = new Label("Name: Soda");
                        calories = new Label("Calories: 100");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000001");
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
                        foodName = new Label("Name: Cigarette");
                        calories = new Label("Calories: -80");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000003");
                        break;
                        
                    case 4 : 
                        foodName = new Label("Name: Banana");
                        calories = new Label("Calories: 2");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000004");
                        break;
                    case 5 : 
                        foodName = new Label("Name: Tomato");
                        calories = new Label("Calories: 7");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000005");
                        break;
                    case 6 : 
                        foodName = new Label("Name: Steak");
                        calories = new Label("Calories: 2000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000006");
                        break;
                    case 7 : 
                        foodName = new Label("Name: Rubber Ball");
                        calories = new Label("Calories: 0");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000007");
                        break;
                }
                
                vbox.getChildren().addAll(foodName, calories, fat, carbs, fiber, protein, id);
                
                hbox.getChildren().addAll(vbox, cb1);
                
                foodListView2.getItems().add(hbox);
            }
            
            mealListView2 = new ListView<HBox>();
            
            for(int i = 1; i < 3; ++i) {
                hbox = new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                
                vbox = new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                
                cb1 = new CheckBox();
                cb1.setText("Remove From Meal");
                
                vbox.getChildren().add(cb1);
                
                switch(i) {
                    case 0 : 
                        foodName = new Label("Name: Burger");
                        calories = new Label("Calories: 1000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000000");
                        break;
                    case 1 : 
                        foodName = new Label("Name: Soda");
                        calories = new Label("Calories: 100");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000001");
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
                        foodName = new Label("Name: Cigarette");
                        calories = new Label("Calories: -80");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000003");
                        break;
                        
                    case 4 : 
                        foodName = new Label("Name: Banana");
                        calories = new Label("Calories: 2");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000004");
                        break;
                    case 5 : 
                        foodName = new Label("Name: Tomato");
                        calories = new Label("Calories: 7");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000005");
                        break;
                    case 6 : 
                        foodName = new Label("Name: Steak");
                        calories = new Label("Calories: 2000");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000006");
                        break;
                    case 7 : 
                        foodName = new Label("Name: Rubber Ball");
                        calories = new Label("Calories: 0");
                        fat = new Label("Fat: 5g");
                        carbs = new Label("Carbs: 5g");
                        fiber = new Label("Fiber: 5g");
                        protein = new Label("Protein: 5g");
                        id = new Label("ID: 000000007");
                        break;
                }
                
                vbox.getChildren().addAll(foodName, calories, fat, carbs, fiber, protein, id);
                
                hbox.getChildren().addAll(vbox, cb1);
                
                mealListView2.getItems().add(hbox);
            }

            GridPane root = new GridPane();

            root.setPadding(new Insets(10, 10, 10, 10));
            root.setHgap(10);
            root.setVgap(10);

            Label labelTitle = new Label("Button Stuff");
            Label foodListTitle = new Label("Food List");
            Label mealListTitle = new Label("Meal List");

            Button addToMealButt = new Button("Add Selected To Meal");
            Button analyzeMealButt = new Button("Analyze Meal");
            Button removeFromMealButt = new Button("Remove Selected From Meal");

            root.add(labelTitle, 0, 0);

            VBox leftBar = makeLeftPane();
            root.add(leftBar, 0, 1);

            root.add(foodListTitle, 1, 0, 1, 1);
            root.add(foodListView2, 1, 1, 2, 1);
            root.add(addToMealButt, 2, 0);

            root.add(mealListTitle, 3, 0, 1, 1);
            root.add(mealListView2, 3, 1, 3, 1);
            root.add(analyzeMealButt, 4, 0, 1, 1);
            root.add(removeFromMealButt, 5, 0);

            scene = new Scene(root, 800, 500);
            window.setScene(scene);
            window.show();  
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
