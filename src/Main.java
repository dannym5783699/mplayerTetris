import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox; // Import HBox for horizontal box layout
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetris");
        BorderPane gameLayout = new BorderPane();
        Scene gameScene = new Scene(gameLayout);
        primaryStage.setScene(gameScene);
        double screenSize = 1000;
        primaryStage.setHeight(screenSize);
        primaryStage.setWidth(screenSize);
        primaryStage.show();
        game = new Game(gameLayout, gameScene);

        Button newGame = new Button("New game");
        newGame.setFont(Font.font("Arial", FontWeight.BOLD, 11)); // Set font to Arial, Bold, Size 16

        // Create an HBox for placing the button at the top left
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.TOP_LEFT);

        //Setting up oppenent boxes for presentation
        VBox opponent1and3 = new VBox();
        VBox opponent2and4 = new VBox();
        Opponent opponent1 = new Opponent();
        Opponent opponent2 = new Opponent();
        Opponent opponent3 = new Opponent();
        Opponent opponent4 = new Opponent();

        //topBox.getChildren().add(newGame);
        opponent1and3.setSpacing(10);
        opponent2and4.setSpacing(10);
        opponent1and3.getChildren().add(opponent1.getPlayerHBox());
        opponent2and4.getChildren().add(opponent2.getPlayerHBox());
        opponent1and3.getChildren().add(opponent3.getPlayerHBox());
        opponent2and4.getChildren().add(opponent4.getPlayerHBox());

        topBox.getChildren().add(opponent1and3);
        topBox.getChildren().add(opponent2and4);

        gameLayout.setLeft(topBox); // Add the HBox to the top of the BorderPane

        newGame.setFocusTraversable(false);
        newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game = new Game(gameLayout, gameScene);
            }
        });
    }
}
