import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    public static final List<ClientHandler> clientHandlers = new ArrayList<>();
    private  Socket socket;
    private  BufferedReader bufferedreader;
    private BufferedWriter bufferedwriter;
    private  String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedreader.readLine();
            synchronized (clientHandlers) {
                clientHandlers.add(this);
            }
            broadcastMessage(clientUsername + " has joined the chat");
        } catch (IOException e) {
            closeEverything();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedreader.readLine();
                if (messageFromClient == null) {
                    break;
                }
                broadcastMessage(clientUsername + ": " + messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
        closeEverything();
    }

    public void broadcastMessage(String messageToSend) {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                try {
                    if (!clientHandler.clientUsername.equals(clientUsername)) {
                        clientHandler.bufferedwriter.write(messageToSend);
                        clientHandler.bufferedwriter.newLine();
                        clientHandler.bufferedwriter.flush();
                    }
                } catch (IOException e) {
                    closeEverything();
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeClientHandler() {
        synchronized (clientHandlers) {
            clientHandlers.remove(this);
        }
        broadcastMessage(clientUsername + " has left the chat");
    }

    public void closeEverything() {
        removeClientHandler();
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
}
