import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * This Client class represents a client in a client-server architecture.
 * It handles connecting to a server using a socket, sending and receiving messages,
 * and closing connections. It is designed to work with a server that communicates
 * through messages.
 */
public class Client {
    private Socket socket;
    private BufferedReader bufferedreader;
    private BufferedWriter bufferedwriter;
    private String username;



    /**
     * Constructor for Client class.
     * Initializes the client's connection to the server and sets up input and output streams.
     *
     * @param socket   The socket connecting to the server.
     * @param username The username of the client.
     */
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedreader, bufferedwriter);
        }
    }

    /**
     * Sends a message from the client to the server.
     * This method captures user input from the console and sends it over the socket connection.
     */
    public void sendMessage() {
        try {
            bufferedwriter.write(username);
            bufferedwriter.newLine();
            bufferedwriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedwriter.write(": " + messageToSend);
                bufferedwriter.newLine();
                bufferedwriter.flush();
            }
            scanner.close();
        } catch (IOException e) {
            closeEverything(socket, bufferedreader, bufferedwriter);
        }
    }

    /**
     * Sends a game board state from the client to the server.
     *
     * @param board The current state of the game board to be sent.
     */
    public void sendBoard(Board board){
        if(socket.isConnected()){
            try {


                bufferedwriter.write(this.username + " " + board.toString());
                bufferedwriter.newLine();
                bufferedwriter.flush();
            }catch(IOException e){
                closeEverything(socket, bufferedreader, bufferedwriter);
            }
        }
    }


    /**
     * Sends the current score and game level from the client to the server.
     *
     * @param scoreHandler An instance of ScoreHandler containing the current score and level.
     */
    public void sendScore(ScoreHandler scoreHandler){
        if(socket.isConnected()){
            try {


                bufferedwriter.write(this.username + " " + scoreHandler.getCurrentScore() + "x" +
                        scoreHandler.getCurrentLevel() + "x" + scoreHandler.getLineClears());
                bufferedwriter.newLine();
                bufferedwriter.flush();
            }catch(IOException e){
                closeEverything(socket, bufferedreader, bufferedwriter);
            }
        }
    }


    /**
     * Listens for messages from the server.
     * This method runs in its own thread, continuously reading messages from the server and printing them.
     */

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromServer;
                while (socket.isConnected()) {
                    try {
                        msgFromServer = bufferedreader.readLine();
                        System.out.println(msgFromServer);

                    } catch (IOException e) {
                        closeEverything(socket, bufferedreader, bufferedwriter);
                    }
                }
            }
        }).start();
    }

    /**
     * Listens for game-related messages from the server and updates the board accordingly.
     * This method runs in its own thread and handles updates to the game board based on server messages.
     *
     * @param board The game board to be updated based on server messages.
     */
    public void listenForMessage(Board board) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromServer;
                while (socket.isConnected()) {
                    try {
                        msgFromServer = bufferedreader.readLine();
                        String[] values = msgFromServer.split(" ");
                        if(values.length == 2 && (!values[1].contains("x"))) {
                            board.setPlayer(values[0], values[1]);
                        }
                        else if(values.length == 2){
                            board.setScore(values[0], values[1]);
                        }
                    } catch (IOException e) {
                        closeEverything(socket, bufferedreader, bufferedwriter);
                    }
                }
            }
        }).start();
    }


    /**
     * Closes all connections and streams associated with the client.
     * This method ensures proper closure of socket, BufferedReader, and BufferedWriter to prevent resource leaks.
     *
     * @param socket         The socket to close.
     * @param bufferedreader The BufferedReader to close.
     * @param bufferedwriter The BufferedWriter to close.
     */
    public void closeEverything(Socket socket, BufferedReader bufferedreader, BufferedWriter bufferedwriter) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedreader != null) {
                bufferedreader.close();
            }
            if (bufferedwriter != null) {
                bufferedwriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method for starting the client.
     * This method sets up the connection to the server and starts listening and sending messages.
     *
     * @param args Command line arg
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        System.out.println("Enter server IP address: ");
        String serverAddress = scanner.nextLine();

        System.out.println("Enter server port number: ");
        int port = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left-over

        Socket socket = new Socket(serverAddress, port);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }


}
