package ElevatorSystem.Vizualizer;

import ElevatorSystem.SystemManager.Elevator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {
    private static int size;
    private static Canvas canvas;
    private final GraphicsContext border;
    private final Vector2D position;

    public Tile(Canvas canvas, int size, Vector2D position, Elevator elevator) {
        Tile.canvas = canvas;
        Tile.size = size;
        this.position = position;
        this.getChildren().add(Tile.canvas);
        this.border = Tile.canvas.getGraphicsContext2D();
        setImage(elevator);
    }

    public void setImage(Elevator elevator){
        Image objectImage;
        if(elevator == null) objectImage = new Image("resources/null.png");
        else objectImage = new Image("resources/elevator.png");
        this.border.drawImage(objectImage, this.position.x * Tile.size, this.position.y*Tile.size);
    }
}
