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
        this.targetFloor = -1;
        this.direction = Direction.UP;

        this.upQueue = new ElevatorQueue(numberOfFloors, Direction.UP);
        this.downQueue = new ElevatorQueue(numberOfFloors, Direction.DOWN);
    }

    public void setCurrentFloor(int floor) {
        if(floor >= 0 && floor <= numberOfFloors)
            this.currentFloor = floor;
    }

    private void findTargetLevelInDirection(int sameDirectionFloor, int oppositeDirectionFloor){
        if(sameDirectionFloor != -1){
            this.targetFloor = sameDirectionFloor;
            this.upQueue.removeFloor();
        }
        else{
            if(oppositeDirectionFloor != -1){
                this.targetFloor = oppositeDirectionFloor;
                this.downQueue.removeFloor();
            }
            else this.targetFloor = -1;
        }
    }

    private void findTargetLevel() {
        if(this.targetFloor == this.currentFloor){
            if(this.direction == Direction.UP){
                this.upQueue.removeFloor();
            }
            else this.downQueue.removeFloor();
        }

        int upTarget = this.upQueue.getNextFloor();
        int downTarget = this.downQueue.getNextFloor();
        if(this.direction == Direction.UP){
            findTargetLevelInDirection(upTarget, downTarget);
        }
        else{
            findTargetLevelInDirection(downTarget, upTarget);
        }
        if(this.targetFloor == this.currentFloor)
            findTargetLevel();

        if(this.targetFloor > this.currentFloor)
            this.direction = Direction.UP;
        else this.direction = Direction.DOWN;
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
        if(this.currentFloor != this.targetFloor && this.targetFloor != -1){
            Vector2D oldPosition = new Vector2D(this.id, this.currentFloor);
            this.setCurrentFloor(this.currentFloor + this.direction.toInt());
            this.upQueue.setCurrentFloor(this.currentFloor);
            this.downQueue.setCurrentFloor(this.currentFloor);
            return oldPosition;
        }
        else{
            findTargetLevel();
            if(this.targetFloor == -1){
                return null;
            }
            else return move();
        }
    }

    public void pickUp(int floor, Direction direction){
        if(direction == Direction.UP)
            this.upQueue.reserveFloor(floor);
        else this.downQueue.reserveFloor(floor);
    }
}