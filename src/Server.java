import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    private Label messageLabel = new Label("No Message");

    @Override
    public void start(Stage primaryStage) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(800,800);
        Scene scene = new Scene(stackPane);
        HBox hBox = new HBox();
        Button button = new Button("Start Server");
        button.setOnAction(event -> {
            try {
                ServerSocket serverSocket = new ServerSocket(1234);
                startServer(serverSocket);
            } catch (IOException e) {
                System.out.println("Server not opperational");
            }
        });
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(messageLabel);
        hBox.getChildren().add(button);

        stackPane.getChildren().add(hBox);

        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startServer(ServerSocket serverSocket) {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                messageLabel.setText("ServerRunning");
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
