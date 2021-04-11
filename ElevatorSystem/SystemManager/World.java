package ElevatorSystem.SystemManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class World extends Application{
    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        /*
        Parameters params = getParameters();
        List<String> list = params.getRaw();
        System.out.println(list.size());
        for(String each : list){
            System.out.println(each);
        }
        */
        //max 16/18
        ElevatorManager manager = new ElevatorManager(stage,16, 18);
        manager.run();
    }
}
