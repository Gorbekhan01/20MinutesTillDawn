package src.com.mygdx.game.Models;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import src.com.mygdx.game.Models.Enemies.Elder;
import src.com.mygdx.game.Models.Enemies.EyeBat;
import src.com.mygdx.game.Models.Enemies.TentacleMonster;
import src.com.mygdx.game.Models.Enemies.Tree;
import src.com.mygdx.game.Views.MainGameScreen;

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
    private int reloadKey = 46;
    private ArrayList<TentacleMonster> tentacleMonsters = new ArrayList<>();
    private ArrayList<EyeBat> eyeBats = new ArrayList<>();
    private ArrayList<Elder> elders = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();
    private String result;
    private int survivedTime = 0;
    public MainGameScreen savedGame = new MainGameScreen();
    public boolean wasPaused = false;
    private int playerXP = 0;
    private int kills = 0;

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

    public int getReloadKey() {
        return reloadKey;
    }

    public void setReloadKey(int reloadKey) {
        this.reloadKey = reloadKey;
    }

    public ArrayList<TentacleMonster> getTentacleMonsters() {
        return tentacleMonsters;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public int getSurvivedTime() {
        return survivedTime;
    }

    public void setSurvivedTime(int survivedTime) {
        this.survivedTime = survivedTime;
    }

    public Screen getSavedGame() {
        return savedGame;
    }

    public void setSavedGame(MainGameScreen savedGame) {
        this.savedGame = savedGame;
    }

    public boolean isWasPaused() {
        return wasPaused;
    }

    public void setWasPaused(boolean wasPaused) {
        this.wasPaused = wasPaused;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<EyeBat> getEyeBat() {
        return eyeBats;
    }

    public ArrayList<Elder> getElder() {
        return elders;
    }

    public int getPlayerXP() {
        return playerXP;
    }

    public void setPlayerXP(int playerXP) {
        this.playerXP = playerXP;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }


}
