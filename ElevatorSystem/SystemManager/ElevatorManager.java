package ElevatorSystem.SystemManager;

import ElevatorSystem.Vizualizer.Tile;
import ElevatorSystem.Vizualizer.Vector2D;
import ElevatorSystem.Vizualizer.Vizualizer;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ElevatorManager {
    private final ArrayList<Elevator> elevatorArrayList;
    private final Vizualizer vizualizer;
    //private final int numberOfFloors;

    ElevatorManager(Stage stage, int numberOfElevators, int numberOfFloors){
        this.elevatorArrayList = new ArrayList<>();
        //this.numberOfLevels = numberOfLevels;
        for(int i = 0; i < numberOfElevators; i++){
            Elevator el = new Elevator(i, numberOfFloors);
            this.elevatorArrayList.add(el);
        }
        this.vizualizer = new Vizualizer(stage,this, numberOfElevators, numberOfFloors);

        stage.setTitle("ElevatorSim");
        stage.setScene(new Scene(vizualizer.getRoot(), 1000, 800));
        stage.show();
    }

    public void run(){
        Elevator e = this.elevatorArrayList.get(0);
        System.out.println(Arrays.toString(e.getStatus()));
        for(int i = 0; i < 20; i++)
            doStep();
        System.out.println(Arrays.toString(e.getStatus()));
    }

    public Object[] getElevatorsStatus(int id){
        return this.elevatorArrayList.get(id).getStatus();
    }

    public Elevator getElevatorAtId(int id){
        return this.elevatorArrayList.get(id);
    }

    public void doStep(){
        for(Elevator elevator : this.elevatorArrayList){
            Vector2D oldPosition = elevator.move();

            if(oldPosition != null){
                this.vizualizer.updateTile(oldPosition, elevator);
            }
        }
    }

    public void addPickup(Direction direction){
        Vector2D position = this.vizualizer.getSelectedTilePosition();
        if(position != null){
            this.elevatorArrayList.get(position.x).pickUp(position.y, direction);
            this.vizualizer.setPickupNotification(direction.toString() + " pickup for:\n" +
                   position.x + " elevator\nat " + position.y + " floor\nadded successfully!");
        }
        else{
            this.vizualizer.setPickupNotification("No tile selected");
        }
    }

    public void addUpPickup() {
        addPickup(Direction.UP);
    }

    public void addDownPickup() {
        addPickup(Direction.DOWN);
    }
}
