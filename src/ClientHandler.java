import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This ClientHandler class is responsible for managing individual client connections in a server.
 * It handles reading messages from a client and broadcasting them to other clients.
 * Each instance of this class is dedicated to handling one client socket.
 */
public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedreader;
    private BufferedWriter bufferedwriter;

    private String clientUsername;

    /**
     * Constructor for the ClientHandler class.
     * Sets up the necessary I/O streams and adds the new client handler to the list of handlers.
     *
     * @param socket The socket that connects to the client.
     */
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedreader.readLine();
            clientHandlers.add(this);
            //broadcastMessage("Server", clientUsername + " has joined the chat");
        } catch(IOException e) {
            closeEverything(socket, bufferedreader, bufferedwriter);
        }
    }

    /**
     * The run method for the thread.
     * Listens for messages from the connected client and broadcasts them to other clients.
     */
    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()){
            try{
                messageFromClient = bufferedreader.readLine();
                broadcastMessage(messageFromClient, clientUsername + " has left the chat");
            } catch (IOException e) {
                closeEverything(socket, bufferedreader, bufferedwriter);
                break;
            }
        }
    }

    /**
     * Broadcasts a message to all clients except the sender.
     *
     * @param messageToSend The message to send.
     * @param s             A string parameter, currently not used.
     */
    public void broadcastMessage(String messageToSend, String s){
        for (ClientHandler clientHandler : clientHandlers){
            try{
                if (!clientHandler.clientUsername.equals(this.clientUsername)){
                    clientHandler.bufferedwriter.write(messageToSend);
                    clientHandler.bufferedwriter.newLine();
                    clientHandler.bufferedwriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedreader, bufferedwriter);
            }
        }
    }

    /**
     * Removes this client handler from the list of active client handlers.
     */
    public void removeClientHandler(){
        clientHandlers.remove(this);
        //broadcastMessage("Server", clientUsername + " has left the chat");
    }

    /**
     * Closes all connections and streams for this client and removes the client handler from the list.
     *
     * @param socket         The socket to close.
     * @param bufferedreader The BufferedReader to close.
     * @param bufferedwriter The BufferedWriter to close.
     */
    public void closeEverything(Socket socket, BufferedReader bufferedreader, BufferedWriter bufferedwriter){
        removeClientHandler();
        try{
            if (socket != null){
                socket.close();
            }
            if (bufferedreader != null){
                bufferedreader.close();
            }
            if (bufferedwriter != null){
                bufferedwriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
