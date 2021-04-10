package ElevatorSystem.Vizualizer;

import ElevatorSystem.SystemManager.ElevatorManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class Vizualizer {
    private final Pane root;
    private final ElevatorManager manager;
    private final Tile[][] grid;

    public Vizualizer(ElevatorManager manager, int numberOfElevators, int numberOfFloors){
        this.root = new Pane();
        this.manager = manager;
        Canvas canvas = new Canvas(600, 800);
        canvas.setTranslateX(30);
        canvas.setTranslateY(50);
        this.root.getChildren().add(canvas);

        this.grid = new Tile[numberOfElevators][numberOfFloors];

        for(int i = 0; i < numberOfElevators; i++){
            for(int j = 0; j < numberOfFloors; j++){
                Vector2D position = new Vector2D(i, j);
                if(i == 0)
                    this.grid[i][j] = new Tile(canvas, 20, position, this.manager.getElevatorAtId(i));
                else this.grid[i][j] = new Tile(canvas, 20, position, null);
                this.root.getChildren().add(this.grid[i][j]);
            }
        }
    }

    public Pane getRoot() {
        return root;
    }
}
