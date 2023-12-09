import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class is designed to build the opening scene that will establish the player and the server client connection.
 * Use the getStartScene() method to incorporate this scene into the game.
 */
public class StartScreen {

    private final VBox paneVBox;
    private final Scene startScene;

    private final Label headLineLabel = new Label("TETRIS");
    private final Label nameLabel = new Label("Player Name");
    private final Label serverIPLabel = new Label("Server IP");
    private final Label portLabel = new Label("Port");
    private final Label waitingLabel = new Label();


    private final Button startButton;

    private final TextField nameField;
    private final TextField serverIPField;
    private final TextField portField;

    private final double paneWidth;
    private final double paneHeight;

    /**
     * Establishes a new start screen to the specified width and height that contains a button to change scenes.
     * @param width width of the game scene
     * @param height height of the game scene
     * @param startButton a button that transitions to the game scene
     */
    public StartScreen(final double width, final double height, final Button startButton) {

        Font customTitleFont = null;
        Font customFont = null;
        try {
            customTitleFont = Font.loadFont(new FileInputStream(new File("docs/Debrosee-ALPnL.ttf")), 50);
            customFont = Font.loadFont(new FileInputStream(new File("docs/Debrosee-ALPnL.ttf")), 20);
        } catch (FileNotFoundException e) {
            System.out.println("Font file not found: " + e.getMessage());
        }

        if (customFont != null && customTitleFont != null) {
            // Apply the font to your components
            headLineLabel.setFont(customTitleFont);
            nameLabel.setFont(customFont);
            serverIPLabel.setFont(customFont);
            portLabel.setFont(customFont);
            startButton.setFont(customFont);
        }


        paneWidth = width;
        paneHeight = height;

        nameField = establishTextField("Player");
        serverIPField = establishTextField("1.111.111.111");
        portField = establishTextField("1234");

        this.startButton = startButton;
        paneVBox = establishPaneVBox();

        StackPane openingPane = establishStackPane();
        startScene = new Scene(openingPane);
    }

    // Establishes the text fields
    private TextField establishTextField(final String fieldEntry) {
        TextField textField = new TextField(fieldEntry);

        textField.setMaxWidth(100);

        return textField;
    }

    // Established the VBox used for the entire pane
    private VBox establishPaneVBox() {
        VBox vBox = new VBox();

        // Setting positioning
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        // Adding in elements
        vBox.getChildren().add(headLineLabel);
        vBox.getChildren().add(nameLabel);
        vBox.getChildren().add(nameField);
        vBox.getChildren().add(serverIPLabel);
        vBox.getChildren().add(serverIPField);
        vBox.getChildren().add(portLabel);
        vBox.getChildren().add(portField);
        vBox.getChildren().add(startButton);

        headLineLabel.setStyle("-fx-text-fill: darkblue;");
        nameLabel.setStyle("-fx-text-fill: red;");
        serverIPLabel.setStyle("-fx-text-fill: green;");
        portLabel.setStyle("-fx-text-fill: purple;");


        return vBox;
    }

    // Establishes the master stack pane for the entire scene
    private StackPane establishStackPane() {
        StackPane newPane = new StackPane();

        newPane.getChildren().add(paneVBox);
        newPane.setPrefSize(paneWidth, paneHeight);
        newPane.setStyle("-fx-background-color: #97C4D8;");

        return newPane;
    }

    /**
     * This scene is built to establish the player and the server client connection.
     *
     * @return the opening start scene
     */
    public Scene getStartScene() {
        return startScene;
    }
}
