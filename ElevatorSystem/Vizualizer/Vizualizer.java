package ElevatorSystem.Vizualizer;

import ElevatorSystem.SystemManager.Elevator;
import ElevatorSystem.SystemManager.ElevatorManager;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
    private final int size;
    private final Text selectedElevator;
    private final Text pickupNotification;
    private final Button addUpPickup;
    private final Button addDownPickup;
    private Tile selectedTile;

    public Vizualizer(Stage stage, ElevatorManager manager, int numberOfElevators, int numberOfFloors){
        this.root = new Pane();
        this.root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        this.manager = manager;
        this.size = numberOfFloors;
        this.selectedTile = null;
        this.grid = new Tile[numberOfElevators][numberOfFloors];

        for(int i = 0; i < numberOfElevators; i++) {
            for (int j = 0; j < numberOfFloors; j++) {
                Vector2D position = new Vector2D(i, j);
                if (j == numberOfFloors - 1)
                    this.grid[i][j] = new Tile(40, position, this, this.manager.getElevatorAtId(i));
                else this.grid[i][j] = new Tile(40, position, this, null);
                this.root.getChildren().add(this.grid[i][j]);
            }
        }

        this.selectedElevator = new Text();
        this.selectedElevator.setWrappingWidth(200);
        this.selectedElevator.setTranslateX(700);
        this.selectedElevator.setTranslateY((50));
        this.selectedElevator.setFill(Color.WHITE);
        this.selectedElevator.setFont(Font.font("Verdana", 15));
        this.root.getChildren().add(this.selectedElevator);

        this.pickupNotification = new Text();
        this.pickupNotification.setWrappingWidth(200);
        this.pickupNotification.setTranslateX(700);
        this.pickupNotification.setTranslateY((500));
        this.pickupNotification.setFill(Color.WHITE);
        this.pickupNotification.setFont(Font.font("Verdana", 15));
        this.root.getChildren().add(this.pickupNotification);

        this.addUpPickup = new Button("Add up Pickup");
        this.addUpPickup.setTranslateX(850);
        this.addUpPickup.setTranslateY(400);
        this.addUpPickup.setMinSize(100, 50);
        this.addUpPickup.setOnAction(event -> manager.addUpPickup());
        this.root.getChildren().add(addUpPickup);

        this.addDownPickup = new Button("Add down Pickup");
        this.addDownPickup.setTranslateX(700);
        this.addDownPickup.setTranslateY(400);
        this.addDownPickup.setMinSize(100, 50);
        this.addDownPickup.setOnAction(event -> manager.addDownPickup());
        this.root.getChildren().add(addDownPickup);

    }

    public Pane getRoot() {
        return root;
    }

    public Vector2D getSelectedTilePosition() {
        if(selectedTile != null)
            return selectedTile.getPosition();
        else return null;
    }

    public void setPickupNotification(String text){
        this.pickupNotification.setText(text);
    }

    public void updateTile(Vector2D oldPosition, Elevator elevator){
        System.out.println(oldPosition + " " + Arrays.toString(elevator.getStatus()));
        this.grid[oldPosition.x][this.size - oldPosition.y - 1].setElevator(null);
        this.grid[elevator.getId()][this.size - elevator.getCurrentFloor() - 1].setElevator(elevator);
    }

    public void elevatorSelected(Tile tile, Elevator elevator, Vector2D position) {
        this.selectedTile = tile;
        if(elevator != null){
            Object[] status = this.manager.getElevatorsStatus(position.x);
            this.selectedElevator.setText("Selected elevator status: " + Arrays.toString(status));
        }
        else this.selectedElevator.setText("None elevator selected");
    }
}
