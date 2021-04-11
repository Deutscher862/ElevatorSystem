package ElevatorSystem.Vizualizer;

public class Vector2D {
    public final int x;
    public final int y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
