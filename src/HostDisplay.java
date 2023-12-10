import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This scene is designed to set up a new server with and make this player the host.
 * <p>
 * The number of players the host chooses will default to 2.
 */
public class HostDisplay {
    private final Scene hostScene;
    private final Stage primaryStage;

    private final Host host;

    private final GameLabel gameLabel = new GameLabel();
    private final ComboBox<Object> numberPlayersComboBox;
    private final Button createButton;
    private final Label messageLabel = new Label("Test");

    private final VBox frameVBox;

    private final double frameWidth;
    private final double frameHeight;
    private int numberPlayers = 2;

    /**
     * Will create and host and a game screen for this player.
     *
     * @param width  the desired width of the window for the game
     * @param height the desired height of the window for the game
     */
    public HostDisplay(final double width, final double height, final Stage primaryStage) {
        frameWidth = width;
        frameHeight = height;

        this.primaryStage = primaryStage;

        numberPlayersComboBox = establishNumberOfPlayersComboBox();

        createButton = establishCreateButton();

        frameVBox = establishFrameVBox();

        host = new Host(width,height,primaryStage,messageLabel);
        StackPane framePane = establishFramePane();
        hostScene = new Scene(framePane);
    }

    // Establishes a combo box to select the number of players
    private ComboBox<Object> establishNumberOfPlayersComboBox() {
        ComboBox<Object> comboBox = new ComboBox<>();

        // Adding Contents
        comboBox.setValue("Number of Players");
        comboBox.getItems().addAll(2, 3, 4, 5);

        // Setting action handler to set number of players
        comboBox.setOnAction(settingNumberOfPlayers -> {
            if (comboBox.getValue() instanceof Integer) {
                numberPlayers = (Integer) comboBox.getValue();
            }
        });

        return comboBox;
    }

    // Establishes the Create Host button to create a new server for game play
    private Button establishCreateButton() {
        Button button = new Button("Add Player");

        // Setting the action
        button.setOnAction(createHostEvent -> {
            host.createPlayer(numberPlayers);
        });

        return button;
    }

    // Establishes the frame VBox that everything will be contained within
    private VBox establishFrameVBox() {
        VBox vBox = new VBox();

        // Adding Children
        vBox.getChildren().add(gameLabel.getGameLabel());
        vBox.getChildren().add(numberPlayersComboBox);
        vBox.getChildren().add(createButton);
        vBox.getChildren().add(messageLabel);

        // Setting UI Visuals
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        return vBox;
    }

    // Establishes the frame pane for this window
    private StackPane establishFramePane() {
        StackPane stackPane = new StackPane();

        // Adding Children
        stackPane.getChildren().add(frameVBox);

        // Setting UI Visuals
        stackPane.setPrefSize(frameWidth, frameHeight);

        return stackPane;
    }

    /**
     * Creates a host scene that establishes all the variables for creating the server
     *
     * @return the host scene
     */
    public Scene getHostScene() {
        return hostScene;
    }
}
