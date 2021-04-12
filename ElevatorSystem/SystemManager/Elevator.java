package ElevatorSystem.SystemManager;

import ElevatorSystem.Vizualizer.Vector2D;

/*
Each elevator object has its own id, currentFloor, current direction and the target floor the elevator tries to get to.
Also, there are two ElevatorQueue objects representing the reserved floors in both directions. The description of that
how they work is included in the ElevatorQueue class, but here is how the elevator uses them to set optimised target floor.

Using two ElevatorQueue objects to set a target floor:

 */
public class Elevator {
    private final int id;
    private static int numberOfFloors;
    private int currentFloor;
    private int targetFloor;
    private Direction direction;
    private final ElevatorQueue upQueue;
    private final ElevatorQueue downQueue;

    Elevator(int id, int numberOfFloors) {
        this.id = id;
        Elevator.numberOfFloors = numberOfFloors;
        this.currentFloor = 0;
        this.targetFloor = -1;
        this.direction = Direction.UP;
        this.upQueue = new ElevatorQueue(numberOfFloors, Direction.UP);
        this.downQueue = new ElevatorQueue(numberOfFloors, Direction.DOWN);
    }

    public void setCurrentFloor(int floor) {
        if (floor >= 0 && floor <= numberOfFloors)
            this.currentFloor = floor;
    }

    private void findTargetLevelInSelectedDirection(ElevatorQueue priorityDirectionQueue, ElevatorQueue oppositeDirectionQueue) {
        int priorityFloor = priorityDirectionQueue.getNextFloor();
        int secondaryFloor = oppositeDirectionQueue.getNextFloor();
        if (priorityFloor != -1) {
            this.targetFloor = priorityFloor;
            priorityDirectionQueue.removeFloor();
        } else {
            if (secondaryFloor != -1) {
                this.targetFloor = secondaryFloor;
                oppositeDirectionQueue.removeFloor();
            } else this.targetFloor = -1;
        }
    }

    private void findTargetLevel() {
        if (this.targetFloor == this.currentFloor) {
            if (this.direction == Direction.UP) {
                this.upQueue.removeFloor();
            } else this.downQueue.removeFloor();
        }

        if (this.direction == Direction.UP) {
            findTargetLevelInSelectedDirection(this.upQueue, this.downQueue);
        } else {
            findTargetLevelInSelectedDirection(this.downQueue, this.upQueue);
        }
        if (this.targetFloor == this.currentFloor)
            findTargetLevel();

        if (this.targetFloor > this.currentFloor)
            this.direction = Direction.UP;
        else this.direction = Direction.DOWN;
    }

    public Object[] getStatus() {
        return new Object[]{this.id, this.currentFloor, this.direction};
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Vector2D move() {
        if (this.currentFloor != this.targetFloor && this.targetFloor != -1) {
            Vector2D oldPosition = new Vector2D(this.id, this.currentFloor);
            this.setCurrentFloor(this.currentFloor + this.direction.toInt());
            this.upQueue.setCurrentFloor(this.currentFloor);
            this.downQueue.setCurrentFloor(this.currentFloor);
            return oldPosition;
        } else {
            findTargetLevel();
            if (this.targetFloor == -1) {
                return null;
            } else return move();
        }
    }

    public void pickUp(int floor, Direction direction) {
        if (direction == Direction.UP)
            this.upQueue.reserveFloor(floor);
        else this.downQueue.reserveFloor(floor);
    }
}