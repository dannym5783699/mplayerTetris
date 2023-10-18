import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class starts the application and sets up the window.
 * @author Danny Metcalfe
 */
public class Main extends Application {

    /**
     * Starts the application.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetris");
        BorderPane gameLayout = new BorderPane();
        Scene gameScene = new Scene(gameLayout);
        primaryStage.setScene(gameScene);
        double screenSize = 1000;
        primaryStage.setHeight(screenSize);
        primaryStage.setWidth(screenSize);
        primaryStage.show();
        Game game = new Game(gameLayout, gameScene);




    }
}