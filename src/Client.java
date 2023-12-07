import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedreader;
    private BufferedWriter bufferedwriter;
    private String username;

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

        public void sendMessage() {
            try {
                bufferedwriter.write(username);
                bufferedwriter.newLine();
                bufferedwriter.flush();

                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()) {
                    String messageToSend = scanner.nextLine();
                    bufferedwriter.write(username + ": " + messageToSend);
                    bufferedwriter.newLine();
                    bufferedwriter.flush();
                }
                scanner.close();
            } catch (IOException e) {
                closeEverything(socket, bufferedreader, bufferedwriter);
            }
        }

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

        public static void main(String[] args) throws IOException{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            //Socket socket = new Socket("LocalHost", 1234);
            Socket socket = new Socket("Laptop5783699", 1234);
            Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();

       }

}
