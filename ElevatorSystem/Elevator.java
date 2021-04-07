package ElevatorSystem;

public class Elevator {
    private final int id;
    private static int maxLevel;
    private int currentLevel;
    private int targetLevel;
    private Direction direction;

    Elevator(int id, int maxLevel){
        this.id = id;
        Elevator.maxLevel = maxLevel;
        this.currentLevel = 0;
        this.direction = Direction.UP;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setCurrentLevel(int level) {
        if(level >= 0 && level <= maxLevel)
            this.currentLevel = level;
    }

    public void setTargetLevel(int level) {
        if(level >= 0 && level <= maxLevel)
            this.targetLevel = level;
    }

    public Object[] getStatus(){
        return new Object[]{this.id, this.currentLevel, this.direction};
    }

    public void move(){
        if(this.currentLevel != this.targetLevel){
            this.setCurrentLevel(this.currentLevel + this.direction.toInt());
        }
    }
}