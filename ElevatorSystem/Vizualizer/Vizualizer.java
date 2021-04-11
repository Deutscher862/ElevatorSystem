package ElevatorSystem.Vizualizer;

import ElevatorSystem.SystemManager.Elevator;
import ElevatorSystem.SystemManager.ElevatorManager;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public class Vizualizer {
    private final Pane root;
    private final ElevatorManager manager;
    private final Tile[][] grid;
    private final Text selectedElevator;

    public Vizualizer(Stage stage, ElevatorManager manager, int numberOfElevators, int numberOfFloors){
        this.root = new Pane();
        this.manager = manager;
        this.grid = new Tile[numberOfElevators][numberOfFloors];

        for(int i = 0; i < numberOfElevators; i++){
            for(int j = 0; j < numberOfFloors; j++){
                Vector2D position = new Vector2D(i, j);
                if(j == numberOfFloors-1)
                    this.grid[i][j] = new Tile(20, position, Color.YELLOW, this, this.manager.getElevatorAtId(i));
                else this.grid[i][j] = new Tile(20, position, Color.WHITE, this, null);
                this.root.getChildren().add(this.grid[i][j]);
            }
        }

        this.selectedElevator = new Text();
        this.selectedElevator.setWrappingWidth(200);
        this.selectedElevator.setTranslateX(820);
        this.selectedElevator.setTranslateY((30));
        this.selectedElevator.setFill(Color.WHITE);
        this.selectedElevator.setFont(Font.font("Verdana", 15));
        this.root.getChildren().add(this.selectedElevator);
    }

    public Pane getRoot() {
        return root;
    }

    public void elevatorSelected(Elevator elevator, Vector2D position) {
        if(elevator != null){
            Object[] status = this.manager.getElevatorsStatus(position.x);
            this.selectedElevator.setText("Selected elevator status: " + Arrays.toString(status));
        }
        else this.selectedElevator.setText("None elevator selected");
    }
}
