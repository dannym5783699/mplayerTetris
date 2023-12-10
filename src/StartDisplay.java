import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This creates a scene where the player can choose to host a game or join a game. It will create the next scene
 * based off their choice.
 */
public class StartDisplay {
    private final Stage primaryStage;

    private final Button hostButton;
    private final Button joinButton;

    private final GameLabel gamelabel = new GameLabel();

    private final HBox buttonHBox;
    private final VBox frameVBox;

    private final double width;
    private final double height;

    /**
     * Builds the start display with two buttons one indicating hosting and the other indicating you wish to join a
     * game.
     *
     * @param width        the desired with for the game window
     * @param height       the desired height for the game window
     * @param primaryStage the stage for the application
     */
    public StartDisplay(final double width, final double height, final Stage primaryStage) {
        this.width = width;
        this.height = height;

        this.primaryStage = primaryStage;

        hostButton = establishHostButton();
        joinButton = establishJoinButton();

        buttonHBox = establishButtonHBox();
        frameVBox = establishFrameVBox();

        StackPane openingPane = establishFramePane();
        Scene startScene = new Scene(openingPane);

        primaryStage.setScene(startScene);
    }

    // Establish the Host Button and have the action change the scene to host scene
    private Button establishHostButton() {
        Button button = new Button("Host Game");

        // Setting the action
        button.setOnAction(sceneChangeEvent -> {
            HostDisplay hostDisplay = new HostDisplay(width, height, primaryStage);
            primaryStage.setScene(hostDisplay.getHostScene());
        });

        return button;
    }

    // Establish the Join Button and have the action change the scene to join scene
    private Button establishJoinButton() {
        Button button = new Button("Join Game");

        // Setting the action
        button.setOnAction(sceneChangeEvent -> {
            JoinDisplay joinDisplay = new JoinDisplay(width, height, primaryStage);
            primaryStage.setScene(joinDisplay.getJoinScene());
        });

        return button;
    }

    // Establish the HBox for the Buttons
    private HBox establishButtonHBox() {
        HBox hBox = new HBox();

        // Adding Children
        hBox.getChildren().add(hostButton);
        hBox.getChildren().add(joinButton);

        // Setting UI visuals
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);

        return hBox;
    }

    // Establish the VBox for the frame
    private VBox establishFrameVBox() {
        VBox vBox = new VBox();

        // Adding Children
        vBox.getChildren().add(gamelabel.getGameLabel());
        vBox.getChildren().add(buttonHBox);

        // Setting UI Visuals
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        return vBox;
    }

    // Establishes the frame pane with our elements
    private StackPane establishFramePane() {
        StackPane stackPane = new StackPane();

        // Adding Children
        stackPane.getChildren().add(frameVBox);

        // Setting UI Visuals
        stackPane.setPrefSize(width, height);

        return stackPane;
    }
}
