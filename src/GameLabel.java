import javafx.scene.control.Label;

/**
 * Creates a common label for use in our opening scenes.
 */
public class GameLabel {
    private final Label gameLabel = new Label("TETRIS");

    /**
     * @return the Label object of our game title
     */
    public Label getGameLabel() {
        return gameLabel;
    }
}
