package ElevatorSystem;

public enum Direction {
    UP,
    DOWN;

    public int toInt(){
        return switch (this) {
            case UP -> 1;
            case DOWN -> -1;
        };
    }

    public Direction getOpposite(){
        return switch (this) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
        };
    }
}
