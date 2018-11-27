/* Nick Decheine
 * In progress work for left pane structure
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ScrollPaneDemo extends Application {

	/*
	 * Work on left pane Pane will consist of a VBox VBox contains a VBox on top
	 * with 3 buttons, and another node below
	 * 
	 */

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

	public ListView<FoodObjectNode> makeRightPane(){
		
		
		
		
		return null;
	}
	
	public void start(Stage primaryStage) {

		// BorderPane layout to put nodes
		BorderPane OuterPane = new BorderPane();
		GridPane pane = new GridPane();

		// Create a ScrollPane
		ScrollPane scrollPane = new ScrollPane();

		Button button = new Button("My Button");

		button.setPrefSize(400, 300);
		// Set content for ScrollPane
		scrollPane.setContent(button);

		// Always show vertical scroll bar
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		// Horizontal scroll bar is only displayed when needed
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		VBox leftPane = makeLeftPane();
		
		OuterPane.setLeft(leftPane);
		OuterPane.setCenter(scrollPane);
		Scene scene = new Scene(OuterPane, 800, 400);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
