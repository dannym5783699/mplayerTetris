import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * This class is the host of the scene for the game itself. It contains everything needed for the GUI and backend of
 * the game. For use, you will need a button that will switch the scene when the game is over and the height and width
 * of your window that you would like to play the game within.
 */
public class GameDisplay {
    private final Scene gameScene;
    private final Button showResultsButton;
    private final HBox fullSceneHBox;

    private final double paneWidth;
    private final double paneHeight;

    /**
     * This creates a new game board that holds the game and all the mechanics within
     *
     * @param width       the width of the window you would like your game to be within
     * @param height      the height of the window you would like your game to be within
     */
    public GameDisplay(final double width, final double height) {
        // Passing in outside elements
        paneWidth = width;
        paneHeight = height;

        showResultsButton = new Button("Show Results");

        // Setting up Elements for the pane
        fullSceneHBox = establishSceneHBox();

        // Setting up the pane and the scene
        StackPane gamePane = establishStackPane();
        gameScene = new Scene(gamePane);
    }

    // Establishes a new HBox for the entire scene
    private HBox establishSceneHBox() {
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(showResultsButton);

        return hBox;
    }

    // Establishes a new stack pane willed with the game elements
    private StackPane establishStackPane() {
        StackPane stackPane = new StackPane();

        stackPane.setPrefSize(paneWidth, paneHeight);
        stackPane.getChildren().add(fullSceneHBox);

        return stackPane;
    }

    /**
     * This is the scene that holds all the elements for the game to be played.
     *
     * @return the scene for the game
     */
    public Scene getGameScene() {
        return gameScene;
    }
}
