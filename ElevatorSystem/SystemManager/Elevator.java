package ElevatorSystem.SystemManager;

import ElevatorSystem.Vizualizer.Vector2D;

/*
Each elevator object has its own id, currentFloor, current direction and the target floor the elevator tries to get to.
Also, there are two ElevatorQueue objects : upQueue and downQueue, representing the reserved floors in both directions.
upQueue is ordered in ascending and down queue is order in descending. When user wants to reserve the floor in specified
direction, new floor is added to the one of these queue, depending of the reservation direction.
The description of that how these queues work is included in the ElevatorQueue class, but here is how the elevator uses them to
set optimised target floor.

Using two ElevatorQueue objects to set a target floor:
To find next floor the elevator must go to, this class uses two methods: findTargetLevel() and findTargetLevelInSelectedDirection().
Firstly we use the first one to check if elevator is already at its target floor. If it is, remove it from the queue
corresponding to the current direction of the elevator. Then we specify with queue takes precedence in selecting next floor.
It depends on the current direction of the elevator: if it is Direction.UP, then upQueue is first and downQueue is second,
otherwise we swap their order. Then we pass them in the right order to the findTargetLevelInSelectedDirection method.
In it we check the next floor for the priority queue, and if it returns nothing, then we check the secondary queue.
In the end we set the elevator in the direction of the target floor.

This system provides the most efficient movement of the elevator between floors  so that we do not skip any floors and
do not make unnecessary movements.
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

    //if elevator has its target floor set, we make one move between floors, otherwise
    //we seek for a new target floor and then try to move again
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

    //adds reservation to the right queue
    public void pickUp(int floor, Direction direction) {
        if (direction == Direction.UP)
            this.upQueue.reserveFloor(floor);
        else this.downQueue.reserveFloor(floor);
    }
}