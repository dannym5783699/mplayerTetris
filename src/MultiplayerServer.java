import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.net.InetAddress;

public class MultiplayerServer implements Runnable {
    private DatagramSocket socket;

    public MultiplayerServer() throws IOException {
        socket = new DatagramSocket(5000);
    }


    @Override
    public void run(){
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while(!Thread.currentThread().isInterrupted()){
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                String message = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Recived message from: " + address + ":" + port + ": " + message);

                byte[] responce = "Hello client".getBytes();
                DatagramPacket responcePacket = new DatagramPacket(responce, responce.length, address, port);
                socket.send(responcePacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {
        MultiplayerServer multiplayerServer = new MultiplayerServer();
        multiplayerServer.run();
    }


}
