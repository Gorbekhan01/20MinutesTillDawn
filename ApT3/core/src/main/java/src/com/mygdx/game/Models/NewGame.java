package src.com.mygdx.game.Models;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import src.com.mygdx.game.Models.Enemies.*;
import src.com.mygdx.game.Views.MainGameScreen;

import java.util.ArrayList;

public class NewGame {
    private double time;
    private Player player;
    private Stage gameStage;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();
    private String result;
    private int survivedTime = 0;
    public MainGameScreen savedGame = new MainGameScreen();
    public boolean wasPaused = false;
    private boolean paused = false;
    private boolean isGroundLimited = false;
    private Bound bound;
    private boolean isElderAlive = false;

    public NewGame(double time, Player player) {
        this.time = time;
        this.player = player;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
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

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }


    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Bound getBound() {
        return bound;
    }

    public void setGroundLimited(Bound bound) {
        isGroundLimited = true;
        this.bound = bound;
    }

    public boolean isGroundLimited() {
        return isGroundLimited;
    }

    public void resetGroundLimited() {
        isGroundLimited = false;
    }

    public boolean isElderAlive() {
        return isElderAlive;
    }

    public void setElderAlive(boolean elderAlive) {
        isElderAlive = elderAlive;
    }





}
