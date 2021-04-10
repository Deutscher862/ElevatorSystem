package ElevatorSystem.SystemManager;

import ElevatorSystem.Vizualizer.Vizualizer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        this.vizualizer = new Vizualizer(this, numberOfElevators, numberOfFloors);

        stage.setTitle("ElevatorSim");
        stage.setScene(new Scene(vizualizer.getRoot(), 1000, 800, Color.BLACK));
        stage.show();
    }

    //Object[]
    public void  getElevatorsStatus(){
        for(Elevator elevator : this.elevatorArrayList){
            elevator.getStatus();
        }
    }

    public Elevator getElevatorAtId(int id){
        return this.elevatorArrayList.get(id);
    }

    public void doStep(){
        for(Elevator elevator : this.elevatorArrayList){
            elevator.move();
        }
    }
}
