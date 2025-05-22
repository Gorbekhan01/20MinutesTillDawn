package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;

public class GameOver implements Screen {
    private Stage stage;
    private Label resultLabel, scoreLabel, killLabel, survivedTimeLabel;
    private int survivedTime, kill, score;
    private Table mainTable;
    private TextButton backButton;

    @Override
    public void show() {
        mainTable = new Table();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        if (GameManager.getNewGame().getResult().equals("victory")) {
            resultLabel = new Label("Victory!", new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        } else if (GameManager.getNewGame().getResult().equals("dead")) {
            resultLabel = new Label("You Died", new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        }
        survivedTime = GameManager.getNewGame().getSurvivedTime();
        kill = GameManager.getNewGame().getPlayer().getKillCount();
        score = survivedTime * kill;
        // set in user account
        GameManager.getCurrentUser().setKills(kill);
        GameManager.getCurrentUser().setScore(score);
        GameManager.getCurrentUser().setMostTimeSurvived(survivedTime);

        resultLabel.setColor(Color.RED);
        mainTable.add(resultLabel).center();
        stage.addActor(mainTable);
        createGameOver();
    }

    public void createGameOver() {
        mainTable.clear();
        mainTable.setFillParent(true);
        mainTable.center();

        resultLabel.setColor(Color.RED);
        mainTable.add(resultLabel).padBottom(20).row();

        scoreLabel = new Label("Score: " + score, new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        mainTable.add(scoreLabel).padBottom(10).row();

        killLabel = new Label("Kill: " + kill, new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        mainTable.add(killLabel).padBottom(10).row();

        survivedTimeLabel = new Label("Survived time: " + (int) survivedTime / 60 + ":" + survivedTime % 60,
            new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        mainTable.add(survivedTimeLabel).padBottom(20).row();

        backButton = new TextButton("Back to main menu", GameManager.getSkin());
        mainTable.add(backButton).padTop(20).row();

        stage.addActor(mainTable);
        addListeners();
    }


    public void addListeners() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
//                GameManager.getNewGame().getSavedGame().dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
