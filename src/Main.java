import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * The Main class constructs the java elements for the board and then creates the game class that hosts the majority
 * of the game.
 *
 * This class will also hold the opponent elements that show how your opponent is doing within the game.
 *
 * THIS IS WHAT IS LAUNCHED TO RUN THE GAME
 */
public class Main extends Application {
    private Game game;

    /**
     * The start method launches the GUI for the game holding the primary stage the borderpane and the current scene
     * that we are in.
     * @param primaryStage can be ignored
     */
    @Override
    public void start(Stage primaryStage) {
        // Establishing Variables
        BorderPane gameLayout = new BorderPane();
        Scene gameScene = new Scene(gameLayout);
        Button newGame = new Button("New game");

        //Setting up oppenent boxes for presentation
        HBox topBox = new HBox();
        VBox opponent1and3 = new VBox();
        VBox opponent2and4 = new VBox();
        Opponent opponent1 = new Opponent();
        Opponent opponent2 = new Opponent();
        Opponent opponent3 = new Opponent();
        Opponent opponent4 = new Opponent();

        double screenSize = 1000;

        // Setting up the primary stage
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(gameScene);
        primaryStage.setHeight(screenSize);
        primaryStage.setWidth(screenSize);
        primaryStage.show();

        // Setting up the Game
        game = new Game(gameLayout, gameScene);
        newGame.setFont(Font.font("Arial", FontWeight.BOLD, 11)); // Set font to Arial, Bold, Size 16

        //topBox.getChildren().add(newGame);
        opponent1and3.setSpacing(10);
        opponent2and4.setSpacing(10);
        opponent1and3.getChildren().add(opponent1.getPlayerHBox());
        opponent2and4.getChildren().add(opponent2.getPlayerHBox());
        opponent1and3.getChildren().add(opponent3.getPlayerHBox());
        opponent2and4.getChildren().add(opponent4.getPlayerHBox());

        topBox.getChildren().add(opponent1and3);
        topBox.getChildren().add(opponent2and4);
        topBox.setAlignment(Pos.TOP_LEFT);

        gameLayout.setLeft(topBox); // Add the HBox to the top of the BorderPane

        newGame.setFocusTraversable(false);

        // Setting up the new Game button
        newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game = new Game(gameLayout, gameScene);
            }
        });
    }
}
