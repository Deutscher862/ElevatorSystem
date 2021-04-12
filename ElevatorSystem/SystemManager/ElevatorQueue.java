package ElevatorSystem.SystemManager;

import java.util.Comparator;
import java.util.PriorityQueue;

/*
ElevatorQueue is a class that has 2 priority queues which describe the request queue in a specific direction,
before and after the current elevator floor. Each of queue has a comparator that ensures the correct positioning of
the reserved floors. In addition, the internal class NoDuplicates provides the inability to add an already existing booking.
Queues are ordered in ascending or descending order by floor, depending on the direction of the queue.

Pickup system:
If the elevator has already been on the floor requested to be visited in a specific direction, new request is added to
the reservedBeforeCurrentFloor queue. Otherwise, if the elevator is the same direction the queue, but the reported floor
has not yet been visited, reservation is added to the reservedAfterCurrentFloor queue. When the elevator has visited all
the floors in the afterQueue and this queue is empty, it means that the next time it will go again from the beginning of
that direction, so we can copy the queue of floors from the reservedBeforeCurrentFloor one to the reservedAfterCurrentFloor
and clear the first one.
 */
public class ElevatorQueue {
    private int currentFloor;
    private final PriorityQueue<Integer> reservedBeforeCurrentFloor;
    private final PriorityQueue<Integer> reservedAfterCurrentFloor;
    private final Direction direction;
    private static int numberOfFloors;

    ElevatorQueue(int numberOfFloors, Direction direction) {
        ElevatorQueue.numberOfFloors = numberOfFloors;
        this.currentFloor = 0;
        this.direction = direction;
        //creating queues with comparators
        this.reservedBeforeCurrentFloor = new NoDuplicates<>(
                (a, b) -> {
                    if (a < b) {
                        if (direction == Direction.UP)
                            return -1;
                        else return 1;
                    }
                    if (a > b) {
                        if (direction == Direction.UP)
                            return 1;
                        else return -1;
                    }
                    return 0;
                });
        this.reservedAfterCurrentFloor = new NoDuplicates<>(
                (a, b) -> {
                    if (a < b) {
                        if (direction == Direction.UP)
                            return -1;
                        else return 1;
                    }
                    if (a > b) {
                        if (direction == Direction.UP)
                            return 1;
                        else return -1;
                    }
                    return 0;
                });
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void reserveFloor(int floor) {
        if (floor >= 0 && floor < ElevatorQueue.numberOfFloors) {
            if (this.direction == Direction.UP) {
                if (floor < currentFloor)
                    this.reservedBeforeCurrentFloor.add(floor);
                else this.reservedAfterCurrentFloor.add(floor);
            } else {
                if (floor < currentFloor)
                    this.reservedAfterCurrentFloor.add(floor);
                else this.reservedBeforeCurrentFloor.add(floor);
            }
        }
    }

    //returns the next floor where the elevator is to come in the given direction
    //if reservedAfterCurrentFloor queue is empty we copy from reservedBeforeCurrentFloor queue to it
    public int getNextFloor() {
        if (!this.reservedAfterCurrentFloor.isEmpty())
            return this.reservedAfterCurrentFloor.peek();
        else {
            this.reservedAfterCurrentFloor.addAll(this.reservedBeforeCurrentFloor);
            this.reservedBeforeCurrentFloor.clear();
            return -1;
        }
    }

    public void removeFloor() {
        this.reservedAfterCurrentFloor.poll();
    }

    static class NoDuplicates<Integer> extends PriorityQueue<Integer> {
        public NoDuplicates(Comparator<Integer> integerComparator) {
        }

        public boolean add(Integer e) {
            boolean isAdded = false;
            if (!super.contains(e)) {
                isAdded = super.add(e);
            }
            return isAdded;
        }
    }
}