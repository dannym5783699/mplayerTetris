git addimport javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is designed to build the opening scene that will establish the player and the server client connection.
 * Use the getStartScene() method to incorporate this scene into the game.
 */
public class JoinDisplay {
    private final VBox paneVBox;
    private final Scene joinScene;

    private final GameLabel gameLabel = new GameLabel();
    private final Label nameLabel = new Label("Player Name");
    private final Label serverIPLabel = new Label("Host IP");
    private final Label portLabel = new Label("Port");
    private final Label noServerConnectionLabel = new Label("Error: Cannot connect to host.\nPlease check your " +
            "server IP Address and Port number are correct.");
    private final Label joinedLabel = new Label("Joined");
    private final Label messsageLabel = new Label();

    private final Button joinButton;

    private final TextField nameField;
    private final TextField serverIPField;
    private final TextField portField;

    private final Stage primaryStage;

    private final double paneWidth;
    private final double paneHeight;

    /**
     * Establishes a new start screen to the specified width and height that contains a button to change scenes.
     *
     * @param width       width of the game scene
     * @param height      height of the game scene
     */
    public JoinDisplay(final double width, final double height, final Stage primaryStage) {
        paneWidth = width;
        paneHeight = height;
        this.primaryStage = primaryStage;

        nameField = establishTextField("Player");
        serverIPField = establishTextField("10.0.0.194");
        portField = establishTextField("1234");

        joinButton = establishJoinButton();

        paneVBox = establishPaneVBox();

        StackPane framePane = establishStackPane();
        joinScene = new Scene(framePane);
    }

    // Establishes the text fields
    private TextField establishTextField(final String fieldEntry) {
        TextField textField = new TextField(fieldEntry);

        textField.setMaxWidth(100);

        return textField;
    }

    // Establishes the joinButton for to create a client with a running server
    private Button establishJoinButton() {
        Button button = new Button("Join Game");

        // Setting up Action Handler
        button.setOnAction(createClientEvent -> {
            try {
                Socket playerSocket = new Socket(getServerIPAddress(), getPort());
                Player player = new Player(getPlayerName(), playerSocket, messsageLabel, paneWidth, paneHeight,
                        primaryStage);
                Thread playerThread = new Thread(player);
                playerThread.start();
                player.sendMessage();

                //Adjusting presentation.
                paneVBox.getChildren().remove(button);
                paneVBox.getChildren().add(joinedLabel);
                paneVBox.getChildren().add(messsageLabel);

            } catch (IOException e) {
                setNoServerConnectionError();
            }
        });

        return button;
    }

    // Converts and returns the contents of the name field
    private String getPlayerName() {
        return nameField.getText();
    }

    // Converts and returns the contents of the Server IP Field
    private String getServerIPAddress() {
        return serverIPField.getText();
    }

    // Converts and returns the contents of the Port field
    private int getPort() {
        return Integer.parseInt(portField.getText());
    }

    // Adds the no server connection message to the start scene.
    private void setNoServerConnectionError() {
        paneVBox.getChildren().add(noServerConnectionLabel);
    }

    // Established the VBox used for the entire pane
    private VBox establishPaneVBox() {
        VBox vBox = new VBox();

        // Setting positioning
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        // Adding in elements
        vBox.getChildren().add(gameLabel.getGameLabel());
        vBox.getChildren().add(nameLabel);
        vBox.getChildren().add(nameField);
        vBox.getChildren().add(serverIPLabel);
        vBox.getChildren().add(serverIPField);
        vBox.getChildren().add(portLabel);
        vBox.getChildren().add(portField);
        vBox.getChildren().add(joinButton);

        return vBox;
    }

    // Establishes the master stack pane for the entire scene
    private StackPane establishStackPane() {
        StackPane newPane = new StackPane();

        newPane.getChildren().add(paneVBox);
        newPane.setPrefSize(paneWidth, paneHeight);

        return newPane;
    }

    /**
     * This scene is built to establish the player and the server client connection.
     *
     * @return the opening start scene
     */
    public Scene getJoinScene() {
        return joinScene;
    }
}
