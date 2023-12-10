import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/**
 * This will set up a new player through the network and communicated that information with the game.
 */
public class Player implements Runnable{
    private final Socket socket;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private final String playerName;
    private final Label messageLabel;

    private final double width;
    private final double height;
    private final Stage primaryStage;

    /**
     * Sets up a new player with a buffered reader and writer.
     * @param playerName the name the player chose
     * @param socket the socket the server wants should be 1234
     */
    public Player(final String playerName, final Socket socket, final Label messageLabel, final double width,
                  final double height, final Stage primaryStage) {
        this.socket = socket;
        this.playerName = playerName;
        this.messageLabel = messageLabel;

        this.width = width;
        this.height = height;
        this.primaryStage = primaryStage;

        establishReaderWriter();

        System.out.println("You are playing as player " + playerName);
    }

    // Establishing buffered reader and writing connection
    private void establishReaderWriter() {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e) {
            System.out.println("Error: Cannot connect to server.");
        }
    }

    /**
     * Establishes the thread to get a message from the network and prints it to the terminal.
     */
    @Override
    public void run() {
        String messageFromServer;
        while(socket.isConnected()) {
            try {
                messageFromServer = bufferedReader.readLine();
                parseMessage(messageFromServer);
            } catch (IOException e) {
                System.out.println("Error: Could not connect to server.");
            }
        }
    }

    /**
     * Sends a message to the server, currently is what establishes a connection with the server.
     */
    public void sendMessage() {
        try {
            bufferedWriter.write(playerName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("ERROR: Could nto connect to server.");
        }
    }

    private void parseMessage(final String messageFromServer) {
        if(messageFromServer.charAt(0)  == 'M') {
            displayMessage(messageFromServer.substring(1));
        } else if(messageFromServer.charAt(0) == 'I') {
            launchGame();
        }
    }

    // Launches the game for the hosting player
    private void launchGame() {
        Platform.runLater(() -> {
            GameDisplay gameDisplay = new GameDisplay(width, height);
            primaryStage.setScene(gameDisplay.getGameScene());
        });
    }

    private void displayMessage(final String messageFromServer) {
        System.out.println(messageFromServer);
        String finalMessageFromServer = messageLabel.getText() + "\n" + messageFromServer;
        Platform.runLater(() -> {
            messageLabel.setText(finalMessageFromServer);
        });
    }
}
