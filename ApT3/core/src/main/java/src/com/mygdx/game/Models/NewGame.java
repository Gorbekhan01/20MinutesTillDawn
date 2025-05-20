package src.com.mygdx.game.Models;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class NewGame {
    private double time;
    private Player player;
    private Stage gameStage;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int rightKey = 32;
    private int leftKey = 29;
    private int upKey = 51;
    private int downKey = 47;
    private int shootKey = 62;

    public NewGame(double time, Player player) {
        this.time = time;
        this.player = player;
    }

    public double getTime() {
        return time;
    }
    public Player getPlayer() {
        return player;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public int getUpKey() {
        return upKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public int getShootKey() {
        return shootKey;
    }

    public void setShootKey(int shootKey) {
        this.shootKey = shootKey;
    }
}
