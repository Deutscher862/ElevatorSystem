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

import java.util.Arrays;


//this class is responsible for the entire graphic design of the program
public class Vizualizer {
    private final Pane root;
    private final ElevatorManager manager;
    private final Tile[][] grid;
    private final int size;
    private final Text selectedElevator;
    private final Text pickupNotification;
    private Tile selectedTile;

    public Vizualizer(ElevatorManager manager, int numberOfElevators, int numberOfFloors){
        this.root = new Pane();
        this.root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        this.manager = manager;
        this.size = numberOfFloors;
        this.selectedTile = null;
        this.grid = new Tile[numberOfElevators][numberOfFloors];

        /*
        Creating grid that contains tiles with elevators and floors.
        Each row is a floor, and each column is a elevator shaft
         */
        for(int i = 0; i < numberOfElevators; i++) {
            for (int j = 0; j < numberOfFloors; j++) {
                Vector2D position = new Vector2D(i, j);
                if (j == numberOfFloors - 1)
                    this.grid[i][j] = new Tile(40, position, this, this.manager.getElevatorAtId(i));
                else this.grid[i][j] = new Tile(40, position, this, null);
                this.root.getChildren().add(this.grid[i][j]);
            }
        }

        //text that displays information about selected tile
        this.selectedElevator = new Text();
        this.selectedElevator.setWrappingWidth(200);
        this.selectedElevator.setTranslateX(700);
        this.selectedElevator.setTranslateY((50));
        this.selectedElevator.setFill(Color.WHITE);
        this.selectedElevator.setFont(Font.font("Verdana", 15));
        this.root.getChildren().add(this.selectedElevator);

        //tells about successful pickup booking
        this.pickupNotification = new Text();
        this.pickupNotification.setWrappingWidth(200);
        this.pickupNotification.setTranslateX(700);
        this.pickupNotification.setTranslateY((500));
        this.pickupNotification.setFill(Color.WHITE);
        this.pickupNotification.setFont(Font.font("Verdana", 15));
        this.root.getChildren().add(this.pickupNotification);

        //these two buttons below add pickup booking for selected tile
        Button addUpPickup = new Button("Add up Pickup");
        addUpPickup.setTranslateX(850);
        addUpPickup.setTranslateY(400);
        addUpPickup.setMinSize(100, 50);
        addUpPickup.setOnAction(event -> this.manager.addUpPickup());
        this.root.getChildren().add(addUpPickup);

        Button addDownPickup = new Button("Add down Pickup");
        addDownPickup.setTranslateX(700);
        addDownPickup.setTranslateY(400);
        addDownPickup.setMinSize(100, 50);
        addDownPickup.setOnAction(event -> this.manager.addDownPickup());
        this.root.getChildren().add(addDownPickup);

        //by those two buttons user can change simulation refreshment time
        Button decreaseRefreshTime = new Button("-100ms");
        decreaseRefreshTime.setTranslateX(850);
        decreaseRefreshTime.setTranslateY(300);
        decreaseRefreshTime.setMinSize(100, 50);
        decreaseRefreshTime.setOnAction(event -> this.manager.setRefreshTime(-100));
        this.root.getChildren().add(decreaseRefreshTime);

        Button increaseRefreshTime = new Button("+100ms");
        increaseRefreshTime.setTranslateX(700);
        increaseRefreshTime.setTranslateY(300);
        increaseRefreshTime.setMinSize(100, 50);
        increaseRefreshTime.setOnAction(event -> this.manager.setRefreshTime(100));
        this.root.getChildren().add(increaseRefreshTime);

        //button that stops simulation
        Button startStopButton = new Button("Start/Stop");
        startStopButton.setTranslateX(850);
        startStopButton.setTranslateY(600);
        startStopButton.setMinSize(100, 50);
        startStopButton.setOnAction(event -> this.manager.pause());
        this.root.getChildren().add(startStopButton);

        //button that ends program
        Button exit = new Button("Exit");
        exit.setTranslateX(700);
        exit.setTranslateY(600);
        exit.setMinSize(100, 50);
        exit.setOnAction(event -> this.manager.exit());
        this.root.getChildren().add(exit);
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

    //update tile elevator attribute
    public void updateTile(Vector2D oldPosition, Elevator elevator){
        this.grid[oldPosition.x][this.size - oldPosition.y - 1].setElevator(null);
        this.grid[elevator.getId()][this.size - elevator.getCurrentFloor() - 1].setElevator(elevator);
    }

    public void selectTile(Tile tile, Elevator elevator, Vector2D position) {
        if(this.selectedTile != null)
            this.selectedTile.setColor(Color.WHITE);
        this.selectedTile = tile;
        this.selectedTile.setColor(Color.GRAY);
        if(elevator != null){
            Object[] status = this.manager.getElevatorsStatus(position.x);
            this.selectedElevator.setText("Selected elevator status: " + Arrays.toString(status));
        }
        else this.selectedElevator.setText("Tile selected: \nelevator number " +position.x + "\nfloor " + position.y);
    }
}