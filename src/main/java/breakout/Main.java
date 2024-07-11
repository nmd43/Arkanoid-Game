package main.java.breakout;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.geometry.Bounds;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


//@Nikita Daga

public class Main extends Application {
    // Constants for the game window size and animation delay
    public static final int SIZE = 400;
    public static final int DELAY = 16;

    // Instances for game entities
    public Ball ball;
    public Paddle paddle;
    public Blocks blocks;
    private GamePlay breakout;
    public Timeline animation;
    public Stage mainStage;

    @Override
    public void start(Stage stage) {

        try {
            mainStage = stage;
            breakout = new GamePlay();

            // Create the initial game instructions scene
            Scene gamePlay = GamePlay.getInstance().createGamePlayScene(breakout.currentLevel);
            stage.setScene(breakout.getInstructions());
            stage.show();


            // Handle key press events for starting the game
            breakout.getInstructions().setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.SPACE) {
                    stage.setScene(gamePlay);
                    gamePlay.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
                    KeyFrame frame = new KeyFrame(Duration.millis(DELAY), event -> update());
                    Timeline animation = new Timeline();
                    animation.setCycleCount(Timeline.INDEFINITE);
                    animation.getKeyFrames().add(frame);
                    animation.play();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void handleKeyPress(KeyCode code) {
        breakout.cheatKeyPress(code);
        paddle.handleKeyPress(code);
        ball.startMoving();
    }

    // Update the game state
    private void update() {

        // Get instances of game entities from GamePlay
        GamePlay gamePlayInstance = GamePlay.getInstance();
        ball = gamePlayInstance.getBall();
        paddle = gamePlayInstance.getPaddle();
        blocks = gamePlayInstance.getBlocks();
        ball.move(paddle);
        blocks.checkCollisionBlock(ball);

        // If level is complete, move to the next level or show congrats if all levels are completed
        if (blocks.isLevelComplete()) {
            if (GamePlay.getInstance().currentLevel == 3) {
                Scene congratsScene = GamePlay.getInstance().congratsPage();
                mainStage.setScene(congratsScene);
            } else {
                Scene levelCompleteScene = GamePlay.getInstance().LevelComplete((GamePlay.getInstance().currentLevel)+1);
                mainStage.setScene(levelCompleteScene);

            }
        }
    }

    class GamePlay {
        public int lives;
        public Text livesText;
        public Text levelText;
        public Text scoreText;
        public static GamePlay instance;
        private final Scene instructions;
        public String filePath;
        public int currentLevel = 1;
        public int score;
        private boolean whiteMode = true;
        private boolean doubleScoreActive = false;

        // Method to toggle between color modes
        public void toggleColorMode() {
            whiteMode = !whiteMode;
            mainStage.getScene().setFill(whiteMode ? Color.WHITE : Color.BLACK);
        }

        // Method to activate double score power-up
        public void activateDoubleScore() {
            doubleScoreActive = true;
            updateScoreText();
            Timeline doubleScoreTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(15), event -> deactivateDoubleScore())
            );
            doubleScoreTimeline.setCycleCount(1); // Run once
            doubleScoreTimeline.play();
        }

        // Method to deactivate double score power-up
        public void deactivateDoubleScore() {
            doubleScoreActive = false;
            updateScoreText();
        }

        public Scene createGamePlayScene(int currentLevel) {
            Group gamePlayRoot = new Group();

            // Initialize game elements: ball, paddle, and blocks
            ball = new Ball(Main.SIZE / 2, Main.SIZE - 30, 5, Color.RED);
            paddle = new Paddle(Main.SIZE / 2 - 50, Main.SIZE - 20, 100, 10, Color.PURPLE);
            blocks = new Blocks();

            // Load the specified level
            loadLevel(currentLevel);

            gamePlayRoot.getChildren().addAll(ball.getNode(), paddle.getNode(), blocks.getNode());
            gamePlayRoot.getChildren().addAll(instance.livesText, instance.levelText, instance.scoreText);

            return new Scene(gamePlayRoot, Main.SIZE, Main.SIZE, Color.WHITE);
        }

        public GamePlay() {
            // Initialize player attributes: lives, level, score
            Group root = new Group();
            this.lives = 3;
            this.livesText = new Text();
            livesText.setFill(Color.BLACK);
            livesText.setX(5);
            livesText.setY(12);

            this.levelText = new Text();
            levelText.setFill(Color.BLACK);
            levelText.setX(160);
            levelText.setY(12);

            this.scoreText = new Text();
            scoreText.setFill(Color.BLACK);
            scoreText.setX(300);
            scoreText.setY(12);

            instance = this;
            updateLivesText();
            updateLevelText();
            updateScoreText();
            instructions = createInstructionScene(root);
        }

        //Load the specified level
        private void loadLevel(int currentLevel) {
            // Construct the file path for the level blocks configuration
            filePath = "src/main/java/breakout/level" + currentLevel + "_blocks";
            // Create blocks based on the specified level file
            blocks.createBlocks(filePath);
            // Reset the player's lives to 3 when loading a new level
            lives = 3;
            updateLivesText();
            updateLevelText();
        }

        // Method to create the Level Complete scene
        public Scene LevelComplete(int currentLevel) {
            Group root = new Group();
            Text levelCompleteText = new Text("Level " + (currentLevel-1)+ " Complete!");
            levelCompleteText.setFill(Color.GREEN);
            levelCompleteText.setStyle("-fx-font: 26px 'Algerian';");
            levelCompleteText.setX(80);
            levelCompleteText.setY(60);

            Text instructionsText = new Text("Press the space bar to move to the next level.");
            instructionsText.setFill(Color.BLACK);
            instructionsText.setStyle("-fx-font: 16px 'TimesRoman';");
            instructionsText.setX(40);
            instructionsText.setY(200);


            root.getChildren().addAll(levelCompleteText, instructionsText);

            Scene levelCompleteScene = new Scene(root, Main.SIZE, Main.SIZE, Color.LAVENDERBLUSH);

            // Handle key press events for moving to the next level
            levelCompleteScene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.SPACE) {
                    // Move to the next level
                    GamePlay.getInstance().currentLevel++;
                    Scene gamePlay = GamePlay.getInstance().createGamePlayScene(currentLevel);
                    mainStage.setScene(gamePlay);

                    // Stop the current animation if exists
                    if (animation != null){
                        animation.stop();
                    }

                    // Set up the new animation for the next level
                    gamePlay.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
                    KeyFrame frame = new KeyFrame(Duration.millis(DELAY), event -> update());
                    animation = new Timeline(frame);
                    animation.setCycleCount(Timeline.INDEFINITE);
                    animation.play();
                }
            });
            return levelCompleteScene;
        }

        public Scene getInstructions() {return instructions;}

        public static GamePlay getInstance() {
            return instance;
        }

        private void updateLivesText() {
            livesText.setText("Lives: " + lives);
        }

        private void updateLevelText(){levelText.setText("Level: " + currentLevel);}

        private void updateScoreText() {scoreText.setText("Score: " + score);}

        public Ball getBall() {return ball;}

        public Paddle getPaddle(){return paddle;}

        public Blocks getBlocks(){return blocks;}

        // Method to update the score based on block points
        public void updateScore(int points) {
            if(doubleScoreActive) {
                score += (points * 2);
            }else{
                score += points;
            }
            updateScoreText();
        }

        // Method to handle the case when a life is lost
        public void loseLife() {
            lives--;
            updateLivesText();

            // Check if all lives are lost
            if (lives == 0) {
                Scene gameOver = GamePlay.getInstance().gameOverPage();
                mainStage.setScene(gameOver);
            }
        }

        // Method to handle cheat key presses
        public void cheatKeyPress(KeyCode code) {
            switch (code) {
                case L:
                    // Cheat: Extra Life
                    lives++;
                    updateLivesText();
                    break;
                case R:
                    // Cheat: Reset Ball and Paddle
                    ball.resetPositions();
                    break;
                case F:
                    // Cheat: Increase Paddle Speed
                    paddle.changeSpeed(2.5);
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(15), event -> paddle.changeSpeed(0.5))
                    );
                    timeline.setCycleCount(1);
                    timeline.play();
                    break;
                case DIGIT1:
                case DIGIT2:
                case DIGIT3:
                    // Cheat: Load specific level based on key 1-3
                    currentLevel = Integer.parseInt(code.getName());
                    Scene gamePlay = createGamePlayScene(currentLevel);
                    mainStage.setScene(gamePlay);
                    if (animation != null){
                        animation.stop();
                    }
                    gamePlay.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
                    KeyFrame frame = new KeyFrame(Duration.millis(DELAY), event -> update());
                    animation = new Timeline(frame);
                    animation.setCycleCount(Timeline.INDEFINITE);
                    animation.play();
                    break;
                case S:
                    // Cheat: Start Instruction Screen
                    Scene splashScreen = getInstructions();
                    mainStage.setScene(splashScreen);
                    break;
                case C:
                    // Cheat: Change game mode color
                    toggleColorMode();
                    break;
                default:
            }
        }

        // Creates the instruction scene with welcome text, game instructions, and a prompt to continue.
        private Scene createInstructionScene(Group root) {

            Text welcomeText = new Text("Welcome to Breakout!");
            welcomeText.setFill(Color.DARKMAGENTA);
            welcomeText.setStyle("-fx-font: 26px 'Algerian';");
            welcomeText.setX(50);
            welcomeText.setY(40);

            Text instructionsText = new Text(
                    """
                            1. Use the left and right arrow keys to control your paddle.\s

                            2. Prevent the ball from falling down.

                            3. Your goal is to break all colored blocks on your screen.

                            4. This game has three levels, and you must pass all to win.

                            5. At each level, you will have three lives.

                            6. Each block gives you a different number of points.

                            7. Some blocks take multiple hits to break, while others give you special power-ups.

                            Good luck!""");
            instructionsText.setFill(Color.BLACK);
            instructionsText.setStyle("-fx-font: 13px 'TimesRoman';");
            instructionsText.setX(20);
            instructionsText.setY(80);
            instructionsText.setWrappingWidth(Main.SIZE - 20);

            Text pressSpace = new Text("Press the space bar to continue.");
            pressSpace.setFill(Color.BLUEVIOLET);
            pressSpace.setStyle("-fx-font: 16px 'TimesRoman';");
            pressSpace.setX(70);
            pressSpace.setY(380);

            root.getChildren().addAll(welcomeText, instructionsText, pressSpace);
            return new Scene(root, Main.SIZE, Main.SIZE, Color.LAVENDERBLUSH);
        }

        // Displays a congratulatory message and the final score.
        public Scene congratsPage() {

            int finalScore = GamePlay.getInstance().score;

            Group root = new Group();
            Text congratsText = new Text("Congratulations! You have passed all levels!");
            congratsText.setFill(Color.PURPLE);
            congratsText.setStyle("-fx-font: 20px 'Times New Roman';");
            congratsText.setX(10);
            congratsText.setY(170);
            congratsText.setWrappingWidth(Main.SIZE - 20);
            congratsText.setTextAlignment(TextAlignment.CENTER);

            Text scoreText = new Text("Your Final Score: " + finalScore);
            scoreText.setFill(Color.BLACK);
            scoreText.setStyle("-fx-font: 20px 'Times New Roman';");
            scoreText.setX(105);
            scoreText.setY(250);

            root.getChildren().addAll(congratsText, scoreText);

            return new Scene(root, Main.SIZE, Main.SIZE, Color.LAVENDERBLUSH);
        }

        // Creates the game over scene displaying a game over message and the final score.
        public Scene gameOverPage() {
            int finalScore = GamePlay.getInstance().score;
            Group root = new Group();
            Text gameOverText = new Text("Game Over. Better Luck Next Time :(");
            gameOverText.setFill(Color.RED);
            gameOverText.setStyle("-fx-font: 20px 'Time New Roman';");
            gameOverText.setX(40);
            gameOverText.setY(100);
            gameOverText.setWrappingWidth(Main.SIZE - 20);

            Text scoreText = new Text("Your Final Score: " + finalScore);
            scoreText.setFill(Color.BLACK);
            scoreText.setStyle("-fx-font: 20px 'Times New Roman';");
            scoreText.setX(105);
            scoreText.setY(200);

            root.getChildren().addAll(gameOverText, scoreText);

            return new Scene(root, Main.SIZE, Main.SIZE, Color.LAVENDERBLUSH);
        }
    }

    class Ball {
        private final Circle node;

        // Speed components of ball
        public double speedX = 2;
        public double speedY = 2;

        // Initialize ball position
        private final double initialX;
        private final double initialY;

        private boolean shouldMove = false;

        public void startMoving() {
            shouldMove = true;
        }

        public Ball(double x, double y, double radius, Color color) {
            node = new Circle(x, y, radius, color);
            initialX = x;
            initialY = y;
        }

        public Circle getNode() {
            return node;
        }

        // Moves the ball, bouncing it off the edges and checking for collisions with the paddle.
        public void move(Paddle paddle) {
            if (shouldMove) {
                node.setCenterX(node.getCenterX() + speedX);
                node.setCenterY(node.getCenterY() + speedY);

                // Bounce off the edges
                if (node.getCenterX() <= 0 || node.getCenterX() >= Main.SIZE) {
                    speedX *= -1;
                }
                if (node.getCenterY() <= 20 || node.getCenterY() >= Main.SIZE) {
                    speedY *= -1;
                }
                checkCollisionPaddle(paddle);
                checkFallen();
            }
        }

        //Checks if the ball has fallen below the screen, resets its position, and decrements a life.
        private void checkFallen() {
            if (node.getCenterY() >= Main.SIZE) {
                resetPositions();
                GamePlay.getInstance().loseLife();
            }
        }

        //Resets the positions of the ball and paddle when a life is lost.
        private void resetPositions() {
            node.setCenterX(initialX);
            node.setCenterY(initialY);
            speedX = 2;
            speedY = 2;
            Paddle.getInstance().resetPosition();
            shouldMove = false;
        }

        //Checks for collision with the paddle and updates the ball's direction accordingly.
        public void checkCollisionPaddle(Paddle paddle) {

            Bounds ballBounds = node.getBoundsInParent();
            Bounds paddleBounds = paddle.getNode().getBoundsInParent();

            if (ballBounds.intersects(paddleBounds)) {
                double paddleWidth = paddleBounds.getWidth();
                double paddleCenterX = paddleBounds.getMinX() + paddleWidth / 2;
                double relativeIntersectX = ballBounds.getMinX() + ballBounds.getWidth() / 2 - paddleCenterX;

                double sectionWidth = paddleWidth / 6;

                // Adjust ball direction based on collision point
                if (Math.abs(relativeIntersectX) < sectionWidth) {
                    int randomDirectionX = new Random().nextBoolean() ? 1 : -1; //if it hits the middle, random direction
                    speedX = randomDirectionX*(Math.abs(speedX));
                } else if (relativeIntersectX < 0) {
                    speedX = -Math.abs(speedX); //if it hits the left, bounce left
                } else {
                    speedX = Math.abs(speedX); //if it hits the right, bounce right
                }
                speedY *= -1; // Reverse the vertical direction
            }
        }

        //Changes the size of the ball.
        public void changeSize(double scale) {
            double currentRadius = node.getRadius();
            double newRadius = currentRadius * scale; // Increase radius by 50%
            node.setRadius(newRadius);
        }

        //Changes the speed of the ball.
        public void changeSpeed(double scale) {
            speedX *= scale; // Decrease speed by 20%
            speedY *= scale;
        }
    }

    static class Paddle {
        private final Rectangle node;
        private double speed = 10;
        private final double initialX;
        private final double initialY;
        private static Paddle instance;

        public Paddle(double x, double y, double width, double height, Color color) {
            node = new Rectangle(x, y, width, height);
            node.setFill(color);
            this.initialX = x;
            this.initialY = y;
            instance = this;
        }

        //Changes the speed of the paddle.
        public void changeSpeed(double scale) {
            speed *= scale;
        }

        public Rectangle getNode() {
            return node;
        }

        public static Paddle getInstance() {
            return instance;
        }

        //Resets the position of the paddle to its initial coordinates.
        public void resetPosition() {
            node.setX(initialX);
            node.setY(initialY);
        }

        //Changes the width of the paddle.
        public void changeWidth(double increaseAmount) {
            double newWidth = node.getWidth() + increaseAmount;
            node.setWidth(newWidth);
        }

        // Handles key presses to move the paddle left or right and warps paddle to the other side when it reaches the end of the screen
        public void handleKeyPress(KeyCode code) {
            if (code == KeyCode.LEFT) {
                if (node.getX() < 0 - node.getWidth()) {
                    node.setX(Main.SIZE - node.getWidth()); // Move to the right edge
                } else {
                    node.setX(node.getX() - speed);
                }
            } else if (code == KeyCode.RIGHT) {

                if (node.getX() > (Main.SIZE)) {
                    node.setX(0); // Move to the left edge
                } else {
                    node.setX(node.getX() + speed);
                }
            }
        }
    }

    class Blocks {
        private final Group node;
        private boolean powerUpActive = false;

        public Blocks() {
            node = new Group();
        }

        public Group getNode() {
            return node;
        }

        //Creates blocks based on the specified file path.
        public void createBlocks(String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                int row = 1;

                int ROWS = 5;
                while ((line = reader.readLine()) != null && row < ROWS + 1) {
                    String[] values = line.split(" ");
                    int COLUMNS = 8;
                    for (int col = 0; col < COLUMNS; col++) {
                        int number = Integer.parseInt(values[col]);
                        int hitsNeeded = calculateHitsNeeded(number, GamePlay.getInstance().currentLevel);
                        Rectangle block = new Rectangle(col * Main.SIZE / COLUMNS, row * 20, Main.SIZE / COLUMNS, 20);
                        block.setFill(getColorForFill(number));
                        block.setStroke(Color.BLACK);
                        block.getProperties().put("hitsNeeded", hitsNeeded);
                        block.getProperties().put("points", calculatePoints(number));
                        if (number == 4) {
                            block.getProperties().put("number4", 4);
                        } else {
                            block.getProperties().put("number4", 0);
                        }
                        node.getChildren().add(block);
                    }
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Calculates the hits needed for a block based on its type and current level.
        private int calculateHitsNeeded(int number, int currentLevel) {
            if (currentLevel == 1) {
                return 1; // All blocks need 1 hit for level 1
            } else
                return switch (number) {
                    case 0, 1, 2, 4 -> 1;
                    case 3 -> 2;
                    default -> 3;
                };
        }

        //Gets the color for filling a block based on its type.
        private Color getColorForFill(int number) {
            return switch (number) {
                case 0 -> Color.LIGHTPINK;
                case 1 -> Color.LIGHTGOLDENRODYELLOW;
                case 2 -> Color.LAVENDER;
                case 3 -> Color.LIGHTSEAGREEN;
                case 4 -> Color.LIGHTSALMON;
                default -> Color.GRAY;
            };
        }

        //Calculates the points for breaking a block based on its type.
        private int calculatePoints(int number) {
            // Assign points based on block type
            return switch (number) {
                case 0, 4 -> 0;
                case 1 -> 100;
                case 2 -> 200;
                case 3 -> 300;
                default -> 400;
            };
        }

        //Checks for collisions between the ball and the blocks.
        //Updates the block properties and triggers power-ups if applicable.
        public void checkCollisionBlock(Ball ball) {
            for (Node myblock : node.getChildren()) {
                Rectangle block = (Rectangle) myblock;
                int hitsNeeded = (int) block.getProperties().get("hitsNeeded");
                int points = (int) block.getProperties().get("points");

                //Check if the ball's bounds intersect with the current block's bounds
                if (ball.getNode().getBoundsInParent().intersects(myblock.getBoundsInParent())) {
                    ball.speedY = Math.abs(ball.speedY);
                    if (hitsNeeded > 1) {
                        // If the block requires multiple hits, decrement hits needed
                        block.getProperties().put("hitsNeeded", hitsNeeded - 1);
                    } else {
                        // If the block is broken (hitsNeeded <= 1), remove it, update score, and check for power-up
                        Platform.runLater(() -> {
                            node.getChildren().remove(myblock);
                            GamePlay.getInstance().updateScore(points);

                            // Trigger power-up if applicable
                            int number4 = (int) block.getProperties().get("number4");
                            if (number4 == 4) {
                                applyPowerUp();
                            }
                        });
                    }
                    break;
                }
            }
        }

        //Checks if the level is complete by examining the hits needed for all blocks.
        public boolean isLevelComplete() {
            int totalHitsNeeded = 0;
            for (Node myblock : node.getChildren()) {
                Rectangle block = (Rectangle) myblock;
                int hitsNeeded = (int) block.getProperties().get("hitsNeeded");
                totalHitsNeeded += hitsNeeded;
            }
            return totalHitsNeeded == 0;
        }

        //Applies a power-up based on a random selection.
        //Activates the chosen power-up and sets a timeline for deactivation.
        private void applyPowerUp() {
            int randomPowerUp = new Random().nextInt(4); // Randomly choose a power-up
            if (!powerUpActive) {
                switch (randomPowerUp) {
                    case 0:
                        // Increase paddle width
                        GamePlay.getInstance().getPaddle().changeWidth(20);
                        break;
                    case 1:
                        // Decrease ball speed
                        GamePlay.getInstance().getBall().changeSpeed(0.8);
                        break;
                    case 2:
                        // Increase ball size
                        GamePlay.getInstance().getBall().changeSize(1.5);
                        break;
                    case 3:
                        // Activate Double Score power-up
                        GamePlay.getInstance().activateDoubleScore();
                        break;
                }
            }
            powerUpActive = true;
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> deactivatePowerUp(randomPowerUp)));
            timeline.setCycleCount(1); // Run once
            timeline.play();
        }

        //Deactivates a power-up based
        private void deactivatePowerUp(int randomPowerUp) {
            switch (randomPowerUp) {
                case 0:
                    // Decrease paddle width
                    GamePlay.getInstance().getPaddle().changeWidth(-20);
                    break;
                case 1:
                    // Increase ball speed
                    GamePlay.getInstance().getBall().changeSpeed(1.2);
                    break;
                case 2:
                    // Decrease ball size
                    GamePlay.getInstance().getBall().changeSize(0.667);
                    break;
                case 3:
                    // Stop doubling the score
                    GamePlay.getInstance().deactivateDoubleScore();
                    break;
            }
            powerUpActive = false;
        }
    }
}



