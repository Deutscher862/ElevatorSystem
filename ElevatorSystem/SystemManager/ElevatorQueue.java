package ElevatorSystem.SystemManager;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ElevatorQueue {
    private int currentFloor;
    private final PriorityQueue<Integer> reservedBeforeCurrentFloor;
    private final PriorityQueue<Integer> reservedAfterCurrentFloor;
    private final Direction direction;
    private static int numberOfFloors;

    ElevatorQueue(int numberOfFloors, Direction direction){
        ElevatorQueue.numberOfFloors = numberOfFloors;
        this.currentFloor = 0;
        this.direction = direction;
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

    public void reserveFloor(int floor){
        if(floor >= 0 && floor < ElevatorQueue.numberOfFloors)
        {
            if(this.direction == Direction.UP){
                if(floor < currentFloor)
                    this.reservedBeforeCurrentFloor.add(floor);
                else this.reservedAfterCurrentFloor.add(floor);
            }
            else{
                if(floor < currentFloor)
                    this.reservedAfterCurrentFloor.add(floor);
                else this.reservedBeforeCurrentFloor.add(floor);
            }
        }
    }

    public int getNextFloor(){
        if(!this.reservedAfterCurrentFloor.isEmpty())
            return this.reservedAfterCurrentFloor.peek();
        else{
            this.reservedAfterCurrentFloor.addAll(this.reservedBeforeCurrentFloor);
            this.reservedBeforeCurrentFloor.clear();
            return -1;
        }
    }

    public void removeFloor(){
        this.reservedAfterCurrentFloor.poll();
    }

    static class NoDuplicates<E> extends PriorityQueue<E> {
        public NoDuplicates(Comparator<E> integerComparator) {
        }
        public boolean add(E e) {
            boolean isAdded = false;
            if (!super.contains(e)) {
                isAdded = super.add(e);
            }
            return isAdded;
        }
    }
}