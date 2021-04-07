package ElevatorSystem;

import java.util.ArrayList;

public class ElevatorManager {
    private final ArrayList<Elevator> elevatorArrayList;
    private final int numberOfLevels;

    ElevatorManager(int numberOfElevators, int numberOfLevels){
        this.elevatorArrayList = new ArrayList<>();
        this.numberOfLevels = numberOfLevels;
        for(int i = 0; i < numberOfElevators; i++){
            Elevator el = new Elevator(i, numberOfLevels);
            this.elevatorArrayList.add(el);
        }
    }

    public Object[] getElevatorStatus(int id){
        return this.elevatorArrayList.get(id).getStatus();
    }
}
