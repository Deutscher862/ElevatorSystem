package ElevatorSystem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ElevatorQueue {
    private final PriorityQueue<Integer> reservedFloor;
    private final Direction direction;
    private static int numberOfFloors;

    ElevatorQueue(int numberOfFloors, Direction direction){
        ElevatorQueue.numberOfFloors = numberOfFloors;
        this.direction = direction;
        this.reservedFloor = new NoDuplicates<>(
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

    public void reserveFloor(int floor){
        if(floor >= 0 && floor < ElevatorQueue.numberOfFloors)
            this.reservedFloor.add(floor);
    }

    public int getNextFloor(int currentFloor){
        if(!this.reservedFloor.isEmpty())
            for(Integer el : this.reservedFloor){
                if(this.direction == Direction.UP && el > currentFloor)
                    return el;
                else if(this.direction == Direction.DOWN && el < currentFloor)
                    return el;
            }
        return -1;
    }

    public void removeFloor(int floor){
        this.reservedFloor.remove(floor);
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