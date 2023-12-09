TETRIS Game


How to Use


Movement: Use the arrow keys to move shapes left, right, or down.
Rotation: Press A to rotate left and D to rotate right.
Starting the Game: The game begins immediately upon starting the program. 
Once starting the game, the start screen displays with option to enter player name, server ip, and port number. 
Once these are correctly entered, it will take you to the main game display.
New Game: Click 'New Game' to reset the game and score.
Game Information: Score, level, and lines cleared are displayed on the right side of the screen.


Project Description
This Tetris game implementation offers both classic gameplay and networked multiplayer features. 
Players can enjoy the traditional Tetris experience with an added twist of competing against others through a networked environment. 

Key features include:

Line Clearing: Form complete rows with falling shapes to clear lines and score points.
Avoid Game Over: Prevent the shapes from reaching the top of the screen.
Shape Control: Strategically move and rotate shapes appearing at the top.
Block Placement: Once a shape lands, it becomes immovable.
Scoring System: Earn points by clearing lines, with increasing scores for clearing multiple lines simultaneously and advancing levels.
Level Progression: The game's pace increases with each level, requiring quicker decision-making and reflexes.
Networking and Server Features
Server-Client Architecture: The game uses a client-server model to manage multiplayer sessions.
Server.java: Manages connections and game state for multiple clients.
Client.java: Handles the individual player's game instance and communicates with the server.
ClientHandler.java: Acts as an intermediary between the server and each client, managing individual client threads.
Opponent.java: Manages the display and updating of opponent's game state in multiplayer mode.
ScoreHandler.java: Keeps track of scores in a networked environment, ensuring accurate and synchronized scorekeeping across clients.


Networking Gameplay
Multiplayer Interaction: Players can compete against each other in real-time, with each player's game progress visible to their opponent.
Synchronized Gameplay: The server ensures that all game states are synchronized across clients, providing a seamless multiplayer experience.
Real-Time Updates: Players receive real-time updates of their opponent's game state, adding a competitive edge to the classic Tetris gameplay.


Scoring
Line Clear Scoring: Scoring 200 points for clearing one line at level one, with points doubling for each additional cleared line in a single turn.
Level Bonus: Each new level adds 100 points to the score for line clears.
Speed: The initial fall speed is 980ms per row at level one, decreasing by 20ms with each subsequent level.


File Structure

Board.java: Serves as the central class for managing the game board in the Tetris game. 
It handles most of the board-related functions such as displaying the game grid, moving shapes, and managing the interaction with Tetris shapes.


Client.java: This class is responsible for managing the client-side operations in the networked Tetris game. 
It handles the connection to the server, communication, and real-time updates of the game state.


ClientHandler.java: This class is a crucial component in the server-side handling of client connections in the multiplayer network of your Tetris game. 
Each instance of this class is responsible for managing the interaction with a single client.


Game.java: This class is the core class responsible for running the Tetris game. 
It integrates the game logic, user interactions, and network communications to create a cohesive gameplay experience.


Main.java: This class is the entry point of the Tetris game application. 
It sets up the initial graphical user interface (GUI) using JavaFX and initiates the game logic.
Opponent.java: This class is a class designed to represent an opponent in the Tetris game, particularly in a multiplayer setting. 
It provides the functionality to display the opponent's game state and manage the opponent's information.


ScoreHandler.java: This class is dedicated to managing the scoring system and game levels in the Tetris game. 
It calculates scores based on line clears and adjusts the game's difficulty level and timing.


Server.java: This class is the core class for managing the server-side operations in the Tetris game's networked multiplayer mode.
It handles the creation, maintenance, and closure of the server socket, and manages client connections.


StartScreen.java: This class is responsible for creating the initial user interface for the Tetris game. 
It constructs the opening scene where players can enter their details and connect to the game server.