import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class starts the application and sets up the window.
 * @author Danny Metcalfe
 */
public class Main extends Application {
    private Game game;

    /**
     * Starts the application.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize and start the MultiplayerServer on a separate thread.
        MultiplayerServer multiplayerServer = new MultiplayerServer();
        Thread serverThread = new Thread(multiplayerServer);
        serverThread.start();

        primaryStage.setTitle("Tetris");
        BorderPane gameLayout = new BorderPane();
        Scene gameScene = new Scene(gameLayout);
        primaryStage.setScene(gameScene);
        double screenSize = 1000;
        primaryStage.setHeight(screenSize);
        primaryStage.setWidth(screenSize);

        // Set up the game and UI components.
        game = new Game(gameLayout, gameScene);
        Button newGame = new Button("New game");
        newGame.setAlignment(Pos.CENTER);
        newGame.setTranslateX(50);
        newGame.setTranslateY(500);
        gameLayout.setLeft(newGame);
        newGame.setFocusTraversable(false);
        newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game = new Game(gameLayout, gameScene);
            }
        });

        // Show the primary stage.
        primaryStage.show();

        // Handle application close request.
        primaryStage.setOnCloseRequest(event -> {
            serverThread.interrupt(); // Interrupt the server thread to stop it.
        });
    }


}
