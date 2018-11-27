package application;
    
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    ListView<String> foodListView;
    ListView<String> mealListView;
    
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

            foodListView = new ListView<>();
            foodListView.getItems().addAll("Banana", "Burger", "Lamb's Eye", "Pizza", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q");
            foodListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            mealListView = new ListView<>();
            mealListView.getItems().addAll("Burger");
            mealListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            /*Button button1 = new Button("Button Number 1");
            Button button2 = new Button("Button Number 2");
            Button button3 = new Button("Button Number 3");

            FlowPane flowPane = new FlowPane();

            flowPane.setOrientation(Orientation.VERTICAL);
            flowPane.setVgap(10);

            flowPane.getChildren().add(button1);
            flowPane.getChildren().add(button2);
            flowPane.getChildren().add(button3);*/


            GridPane root = new GridPane();

            root.setPadding(new Insets(20));
            root.setHgap(25);
            root.setVgap(15);

            Label labelTitle = new Label("Button Stuff");
            Label foodListTitle = new Label("Food List");
            Label mealListTitle = new Label("Meal List");

            Button addToMealButt = new Button("Add To Meal");
            Button analyzeMealButt = new Button("Analyze Meal");

            root.add(labelTitle, 0, 0);

            VBox leftBar = makeLeftPane();
            root.add(leftBar, 0, 1);

            root.add(foodListTitle, 1, 0, 1, 1);
            root.add(foodListView, 1, 1, 2, 1);
            root.add(addToMealButt, 2, 0);

            root.add(mealListTitle, 3, 0, 1, 1);
            root.add(mealListView, 3, 1, 2, 1);
            root.add(analyzeMealButt, 4, 0);

            scene = new Scene(root, 1000, 500);
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
