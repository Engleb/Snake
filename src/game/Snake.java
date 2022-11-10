package game;

import com.cs.engine.cell.Color;
import com.cs.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    List<GameObject> snakeParts;
    private  static final  String HEAD_SIGN = "\uD83D\uDC7E";
    private  static final  String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    public Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        snakeParts = new ArrayList<>();
        snakeParts.add(new GameObject(x,y));
        snakeParts.add(new GameObject(x+1,y));
        snakeParts.add(new GameObject(x+2,y));
    }

    public void draw(Game game){
        Color colorSnake = Color.YELLOW;
        if (!isAlive) colorSnake=Color.RED;
        game.setCellValueEx(snakeParts.get(0).x,snakeParts.get(0).y,
                Color.NONE,HEAD_SIGN,Color.YELLOW,75);
        for (int i = 1; i < snakeParts.size(); i++) {
            game.setCellValueEx(snakeParts.get(i).x,snakeParts.get(i).y,
                    Color.NONE,BODY_SIGN,Color.YELLOW,75);
        }
    }

    public void setDirection(Direction direction){
        switch (this.direction){
            case LEFT:
            case RIGHT:
                if (snakeParts.get(0).x == snakeParts.get(1).x) return;
                break;
            case UP:
            case DOWN:
                if (snakeParts.get(0).y == snakeParts.get(1).y) return;
                break;
        }
        this.direction =direction;
    }
    public void move(Apple apple){
        GameObject head = createNewHead();
        if (head.x<0|| head.y<0||
                head.x>SnakeGame.WIDTH-1||
                head.y>SnakeGame.HEIGHT-1||
                chekCollision(head)
        ){
            isAlive=false;
        }else{
            snakeParts.add(0,head);
            if (head.x==apple.x&&head.y == apple.y)
                apple.isAlive=false;
            else {
            snakeParts.remove(snakeParts.size()-1);}
        }



        }


    private GameObject createNewHead() {
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;

        if (direction == Direction.LEFT)
            return new GameObject(headX - 1, headY);

        if (direction == Direction.RIGHT)
            return new GameObject(headX + 1, headY);

        if (direction == Direction.UP)
            return new GameObject(headX, headY - 1);

        if (direction == Direction.DOWN) {
            return new GameObject(headX, headY + 1);
        }
        return null;
    }

    public boolean chekCollision(GameObject gameObject) {
        for (GameObject snakePart : snakeParts) {
            if (snakePart.x == gameObject.x&&snakePart.y == gameObject.y){
                return true;
            }
        }
        return false;
    }
}
