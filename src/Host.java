import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Host allows for one connection with all other clients in order to distribute information about every
 * singular players game.
 */
public class Host {
    private int currentPlayers = 1;
    private final List<PlayerHandler> playerHandlerList = new ArrayList<>();

    private ServerSocket serverSocket;

    private final double frameWidth;
    private final double frameHeight;

    private final Stage primaryStage;
    private final Label messageLabel;

    /**
     * Establishes a new server then launches the game when all players have joined.
     *
     * @param width         the window with for the game
     * @param height        the window height for the game
     * @param primaryStage  teh primaryStage used to host the scenes of the game
     */
    public Host(final double width, final double height, final Stage primaryStage,
                final Label messageLabel) {

        this.frameWidth = width;
        this.frameHeight = height;

        this.primaryStage = primaryStage;
        this.messageLabel = messageLabel;

        try {
            serverSocket = new ServerSocket(1234);
            messageLabel.setText("Host has launched on port 1234");

        } catch (IOException e) {
            System.out.println("ERROR: Change port, server not operational.");
        }
    }

    // Creating a new player
    public void createPlayer(final int numberPlayers) {
        try {
            Socket clientSocket = serverSocket.accept();
            PlayerHandler playerHandler = new PlayerHandler(clientSocket);
            playerHandlerList.add(playerHandler);
            Thread playerThread = new Thread(playerHandler);
            playerThread.start();

            currentPlayers++;

            if(currentPlayers == numberPlayers) {
                launchGame();
            } else {
                messageLabel.setText(numberPlayers - currentPlayers + " are left to join the game.");
            }
        } catch (IOException e) {
            System.out.println("ERROR: Change port, server not operational.");
        }
    }

    // Launches the game for the hosting player
    private void launchGame() {
        GameDisplay gameDisplay = new GameDisplay(frameWidth, frameHeight);
        primaryStage.setScene(gameDisplay.getGameScene());

        playerHandlerList.get(0).broadcastMessage("IStart");
    }

}
