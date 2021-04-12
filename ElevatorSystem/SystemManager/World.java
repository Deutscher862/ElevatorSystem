package ElevatorSystem.SystemManager;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class World extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parameters params = getParameters();
        List<String> list = params.getRaw();
        int numberOfElevators;
        int numberOfFloors;
        //parameters validation
        if (list.size() != 0 && list.size() != 2)
            throw new IllegalArgumentException("You must enter 2 arguments: 0: numberOfElevators, 1:numberOfFloors");
        if (list.size() == 2) {
            numberOfElevators = Integer.parseInt(list.get(0));
            numberOfFloors = Integer.parseInt(list.get(1));
            if (numberOfElevators < 1 || numberOfElevators > 16)
                throw new IllegalArgumentException("numberOfElevators must be in the range from 1 to 16");
            if (numberOfFloors < 1 || numberOfFloors > 18)
                throw new IllegalArgumentException("numberOfElevators must be in the range from 1 to 18");
        } else {
            numberOfElevators = 16;
            numberOfFloors = 18;
        }

        //run simulation
        ElevatorManager manager = new ElevatorManager(stage, numberOfElevators, numberOfFloors);
        manager.run();
    }
}