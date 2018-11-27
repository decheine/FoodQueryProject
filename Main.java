package application;
	
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
    Stage window;
    Scene scene;
    ListView<String> foodListView;
    ListView<String> mealListView;
    
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

		    Button button1 = new Button("Button Number 1");
		    Button button2 = new Button("Button Number 2");
		    Button button3 = new Button("Button Number 3");

		    FlowPane flowPane = new FlowPane();

		    flowPane.setOrientation(Orientation.VERTICAL);
		    flowPane.setVgap(10);

		    flowPane.getChildren().add(button1);
		    flowPane.getChildren().add(button2);
		    flowPane.getChildren().add(button3);

		    HBox layout = new HBox(10);
		    layout.setPadding(new Insets(20, 20, 20, 20));
		    layout.getChildren().addAll(flowPane, foodListView, mealListView);

		    scene = new Scene(layout, 1000, 500);
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
