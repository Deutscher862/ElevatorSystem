package ElevatorSystem.Vizualizer;

import ElevatorSystem.SystemManager.Elevator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    //pojedyncza płytka reprezentująca pojedyncze pole na mapie
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
        setColor();
        this.getChildren().add(rectangle);
        this.setTranslateX(this.position.x * size + 10);
        this.setTranslateY(this.position.y * size + 10);

        setOnMouseClicked(event -> vizualizer.elevatorSelected(this.elevator, this.position));
    }

    public void setColor() {
        if(this.elevator != null)
            this.rectangle.setFill(Color.YELLOW);
        else
            this.rectangle.setFill(Color.WHITE);
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
        setColor();
    }
}