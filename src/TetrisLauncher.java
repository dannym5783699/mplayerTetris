
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This will launch the game.
 * <p>
 * CONTAINS THE MAIN METHOD FOR CLIENT AND GAME
 */
public class TetrisLauncher extends Application {

    /**
     * Used to host and initialize all GUI elements, done through using buttons and scenes to switch game screens
     *
     * @param primaryStage initialized stage for game.
     */
    @Override
    public void start(Stage primaryStage) {
        // Dimensions of window you want you game played in
        double width = 1000;
        double height = 800;

        // Building the first display and setting to current scene
        StartDisplay startDisplay = new StartDisplay(width, height, primaryStage);

        // Setting for the stage
        primaryStage.setTitle("TETRIS");
        primaryStage.show();
    }

    /**
     * Is here to launch our application
     *
     * @param args no initializing arguments needed
     */
    public static void main(String[] args) {
        launch(args);
    }
}
