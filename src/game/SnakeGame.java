package game;

import com.cs.engine.cell.Color;
import com.cs.engine.cell.Game;
import com.cs.engine.cell.Key;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int Goal =7;
    private int turnDelay;
    private int score;
    private boolean isGameStopped;
    private Apple apple;
    private Snake snake;

    public void initialize(){
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }

    private void createGame() {
        score=0;
        setScore(score);
        Apple apple1 = new Apple(3,4);
        turnDelay = 300;

        snake = new Snake(WIDTH /2, HEIGHT/2);
        createNewApple();
        isGameStopped= false;
        drawScene();
        setTurnTimer(turnDelay);
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i,j,Color.DARKSEAGREEN," ");
            }
        }
        apple.draw(this);
        snake.draw(this);
        System.out.println(snake.direction);





    }
    @Override
public void onTurn(int step){
        super.onTurn(step);
        snake.move(apple);
        if (!apple.isAlive){
            turnDelay-=10;
            setTurnTimer(turnDelay);
            score+=5;
            setScore(score);
            createNewApple();
        }
        if (!snake.isAlive) gameOver();
        if (snake.snakeParts.size()>Goal) win();
        drawScene();
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.RED,"Game Over", Color.YELLOW,60);
    }
    private void win() {
        stopTurnTimer();
        showMessageDialog(Color.RED,"You WIN", Color.YELLOW,60);
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.UP) snake.setDirection(Direction.UP);
        if (key == Key.DOWN) snake.setDirection(Direction.DOWN);
        if (key == Key.LEFT) snake.setDirection(Direction.LEFT);
        if (key == Key.RIGHT) snake.setDirection(Direction.RIGHT);
        if (key == Key.SPACE) createGame();
    }
    private void createNewApple(){
        do{
            apple=new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));

        }while (snake.chekCollision(apple));
        apple.isAlive=true;
    }
}
