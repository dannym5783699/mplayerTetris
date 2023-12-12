import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This Server class represents a simple server in a client-server network architecture.
 * It listens for client connections on a specified port, creates a new ClientHandler for each client,
 * and handles incoming connections in separate threads.
 */
public class Server {
    private ServerSocket serverSocket;

    /**
     * Constructor for the Server class.
     * Initializes the server with a ServerSocket.
     *
     * @param serverSocket The ServerSocket to listen for incoming client connections.
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Starts the server to accept incoming client connections.
     * Continuously listens for new clients and creates a new thread for each connection.
     */
    public void startServer() {
        try{
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (IOException e) {
        }
    }
    /**
     * Closes the server socket and stops the server from accepting new connections.
     */
    public void closeServerSocket() {
        try{
            if (serverSocket != null) {
                serverSocket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method to start the server.
     * Creates a ServerSocket and starts the server.
     *
     * @param args Command line arguments, not used in this implementation.
     */
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        }catch (IOException e) {
        }
    }
}
