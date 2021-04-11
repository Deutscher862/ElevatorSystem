package ElevatorSystem.Vizualizer;

import ElevatorSystem.SystemManager.Elevator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    private final int size;
    private final Rectangle rectangle;
    private final Vector2D position;
    private Elevator elevator;

    public Tile(int size, Vector2D position, Vizualizer vizualizer, Elevator elevator) {
        this.size = size;
        this.position = position;
        this.elevator = elevator;
        this.rectangle = new Rectangle(size, size);
        this.rectangle.setStroke(Color.BLACK);
        setElevatorColor();
        this.getChildren().add(rectangle);
        this.setTranslateX(this.position.x * size + 40);
        this.setTranslateY(this.position.y * size + 40);

        setOnMouseClicked(event -> vizualizer.selectTile(this, this.elevator, this.position));
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setColor(Color color){
        if(this.elevator != null && color == Color.WHITE)
            this.rectangle.setFill(Color.YELLOW);
        else this.rectangle.setFill(color);
    }

    public void setElevatorColor() {
        if(this.elevator != null)
            setColor(Color.YELLOW);
        else
            setColor(Color.WHITE);
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
        setElevatorColor();
    }
}