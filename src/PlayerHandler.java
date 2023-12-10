import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is designed to handle the individuals players in the game from the server side transferring and
 * exchanging information.
 */
public class PlayerHandler implements Runnable{
    private static final ArrayList<PlayerHandler> playerHandlers = new ArrayList<>();

    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private String playerUsername;


    /**
     * Creates a new client on the server side that will hold and send information back and forth over the network.
     * @param socket should be 1234 for this project
     */
    public PlayerHandler(final Socket socket){
        this.socket = socket;

        // Attempting to create a new client from network connection
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            playerUsername = bufferedReader.readLine();
            playerHandlers.add(this);
                broadcastMessage("M" + playerUsername + " has joined the game.");
        } catch (IOException e) {
            System.out.println("Error: Failed to connect to client.");
        }
    }

    /**
     * Creates a new thread for this client that will continuously check and broadcast messages to all other clients.
     */
    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()){
           try {
               messageFromClient = bufferedReader.readLine();
               if (messageFromClient != null) {
                   broadcastMessage("M" + messageFromClient);
               } else {
                   closeEverything();
               }
           } catch (IOException e) {
               closeEverything();
               break;
           }
        }
    }

    // Sends a message to every player but the player that is current in progress
    public void broadcastMessage(String messageToSend) {
        for (PlayerHandler playerHandler: playerHandlers) {
            try {
                    playerHandler.bufferedWriter.write(messageToSend);
                    playerHandler.bufferedWriter.newLine();
                    playerHandler.bufferedWriter.flush();

                    System.out.println(messageToSend);
            } catch (IOException e) {
                closeEverything();
                System.out.println("Error: Was not able to establish connection with players.");
            }
        }
    }

    // Removes the player from the game
    private void removePlayerHandler() {
        playerHandlers.remove(this);
        broadcastMessage("M" + playerUsername + " has left the game.");
    }

    // Closes everything so you don't get the thread error
    private void closeEverything() {
        removePlayerHandler();

        try {
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error: There was an error when attempting to close the game");
        }
    }
}
