package ElevatorSystem.SystemManager;

import ElevatorSystem.Vizualizer.Vector2D;

public class Elevator {
    private final int id;
    private static int numberOfFloors;
    private int currentFloor;
    private int targetFloor;
    private Direction direction;
    private final ElevatorQueue upQueue;
    private final ElevatorQueue downQueue;

    Elevator(int id, int numberOfFloors){
        this.id = id;
        Elevator.numberOfFloors = numberOfFloors;
        this.currentFloor = 0;
        this.targetFloor = 0;
        this.direction = Direction.UP;

        this.upQueue = new ElevatorQueue(numberOfFloors, Direction.UP);
        this.downQueue = new ElevatorQueue(numberOfFloors, Direction.DOWN);
    }

    public void setCurrentFloor(int floor) {
        if(floor >= 0 && floor <= numberOfFloors)
            this.currentFloor = floor;
    }

    public void findTargetLevel() {
        int upTarget = this.upQueue.getNextFloor(this.currentFloor);
        int downTarget = this.downQueue.getNextFloor(this.currentFloor);
        if(this.direction == Direction.UP){
            if(upTarget != -1){
                this.targetFloor = upTarget;
                this.upQueue.removeFloor(upTarget);
            }
            else{
                if(downTarget != -1){
                    this.direction = Direction.DOWN;
                    this.targetFloor = downTarget;
                    this.downQueue.removeFloor(downTarget);
                }
            }
        }
        else{
            if(downTarget != -1){
                this.targetFloor = downTarget;
                this.downQueue.removeFloor(downTarget);
            }
            else{
                if(upTarget != -1){
                    this.direction = Direction.UP;
                    this.targetFloor = upTarget;
                    this.upQueue.removeFloor(upTarget);
                }
            }
        }
    }

    public Object[] getStatus(){
        return new Object[]{this.id, this.currentFloor, this.direction};
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Vector2D move(){
        if(this.currentFloor != this.targetFloor){
            Vector2D oldPosition = new Vector2D(this.id, this.currentFloor);
            this.setCurrentFloor(this.currentFloor + this.direction.toInt());
            return oldPosition;
        }
        else{
            findTargetLevel();
            return null;
        }
    }

    public void pickUp(int floor, Direction direction){
        if(direction == Direction.UP)
            this.upQueue.reserveFloor(floor);
        else this.downQueue.reserveFloor(floor);
    }
}