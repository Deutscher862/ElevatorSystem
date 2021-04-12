package ElevatorSystem.SystemManager;

import ElevatorSystem.Vizualizer.Vector2D;
import ElevatorSystem.Vizualizer.Vizualizer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

//This class is the core of the whole program, it manages the simulation status and elevator movement
public class ElevatorManager {
    private final ArrayList<Elevator> elevatorArrayList;
    private final Vizualizer vizualizer;
    private boolean paused;
    private boolean ended;
    private final Stage stage;
    private final int numberOfFloors;
    private int refreshTime;

    ElevatorManager(Stage stage, int numberOfElevators, int numberOfFloors) {
        this.elevatorArrayList = new ArrayList<>();
        this.stage = stage;
        this.numberOfFloors = numberOfFloors;
        this.refreshTime = 500;
        //manager has all elevators in elevatorArrayList
        for (int i = 0; i < numberOfElevators; i++) {
            Elevator el = new Elevator(i, numberOfFloors);
            this.elevatorArrayList.add(el);
        }
        this.vizualizer = new Vizualizer(this, numberOfElevators, numberOfFloors);

        stage.setTitle("ElevatorSim");
        stage.setScene(new Scene(vizualizer.getRoot(), 1000, 800));
        stage.show();
    }

    //the main method that makes the simulation run continuously
    public void run() {
        new Thread(() -> {
            while (!this.ended) {
                doStep();
                try {
                    Thread.sleep(this.refreshTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (this.paused)
                    Thread.onSpinWait();
            }
        }).start();
    }

    public Object[] getElevatorsStatus(int id) {
        return this.elevatorArrayList.get(id).getStatus();
    }

    public Elevator getElevatorAtId(int id) {
        return this.elevatorArrayList.get(id);
    }

    //makes each elevator make one move, this method also tells the vizualizer to update tile attributes
    // if any of elevator changes its position
    public void doStep() {
        for (Elevator elevator : this.elevatorArrayList) {
            Vector2D oldPosition = elevator.move();
            if (oldPosition != null) {
                this.vizualizer.updateTile(oldPosition, elevator);
            }
        }
    }

    //this method adds pickup reservation for chosen direction, elevator and floor
    public void addPickup(Direction direction) {
        Vector2D position = this.vizualizer.getSelectedTilePosition();
        if (position != null) {
            this.elevatorArrayList.get(position.x).pickUp(this.numberOfFloors - position.y - 1, direction);
            this.vizualizer.setPickupNotification(direction.toString() + " pickup for:\n" +
                    position.x + " elevator\nat " + position.y + " floor\nadded successfully!");
        } else {
            this.vizualizer.setPickupNotification("No tile selected");
        }
    }

    public void setRefreshTime(int refreshTime) {
        int newTime = this.refreshTime + refreshTime;
        if (newTime >= 100 && newTime <= 1000)
            this.refreshTime = newTime;
    }

    public void addUpPickup() {
        addPickup(Direction.UP);
    }

    public void addDownPickup() {
        addPickup(Direction.DOWN);
    }

    public void pause() {
        this.paused = !this.paused;
    }

    public void exit() {
        this.stage.close();
        this.ended = true;
    }
}