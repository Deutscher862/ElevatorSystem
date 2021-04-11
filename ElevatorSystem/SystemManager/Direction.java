package ElevatorSystem.SystemManager;

public enum Direction {
    UP,
    DOWN;

    public int toInt(){
        return switch (this) {
            case UP -> 1;
            case DOWN -> -1;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case UP -> "UP";
            case DOWN -> "DOWN";
        };
    }
}
