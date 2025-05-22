package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardMenu implements Screen {

    private Stage stage;
    private Table mainTable;
    private ArrayList<User> users = new ArrayList<>();

    private TextButton backButton;
    private TextButton sortByScoreButton;
    private TextButton sortByKillButton;
    private TextButton sortByUsernameButton;
    private TextButton sortByTimeSurvived;
    Table scoreTable = new Table();

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();
        }
        Gdx.input.setInputProcessor(stage);

        users.clear();

        users.addAll(GameManager.getUsers());
        users.sort(Comparator.comparingInt(User::getScore).reversed());


        createUI();
    }

    private void createUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        ScrollPane scrollPane = new ScrollPane(createScoreTable(), GameManager.getSkin());
        scrollPane.setScrollingDisabled(true, false);

        mainTable.add(scrollPane)
            .width(Gdx.graphics.getWidth() * 0.95f)
            .height(Gdx.graphics.getHeight() * 0.7f)
            .padBottom(20)
            .row();

        addSortingButtons();

        addBackButton();




    }

    private Table createScoreTable() {
        scoreTable.clear();

        scoreTable.add(createHeaderLabel("Rank")).width(80);
        scoreTable.add(createHeaderLabel("Username")).width(200);
        scoreTable.add(createHeaderLabel("Score")).width(120);
        scoreTable.add(createHeaderLabel("Kills")).width(120);
        scoreTable.add(createHeaderLabel("Time")).width(120);
        scoreTable.row();
        scoreTable.defaults().pad(8).center();

        int rank = 1;
        for (User user : users) {
            scoreTable.add(createDataLabel(String.valueOf(rank)));
            scoreTable.add(createDataLabel(user.getUsername()));
            scoreTable.add(createDataLabel(String.valueOf(user.getScore())));
            scoreTable.add(createDataLabel(String.valueOf(user.getKills())));
            scoreTable.add(createDataLabel(formatTime(user.getMostTimeSurvived())));
            scoreTable.row();
            rank++;
        }

        return scoreTable;
    }


    private Label createHeaderLabel(String text) {
        Label label = new Label(text, GameManager.getSkin());
        label.setFontScale(0.9f);
        label.setColor(Color.YELLOW);
        return label;
    }

    private Label createDataLabel(String text) {
        Label label = new Label(text, GameManager.getSkin());
        label.setFontScale(0.8f);
        return label;
    }

    private void addSortingButtons() {
        Table buttonTable = new Table();
        buttonTable.defaults().pad(5).width(150).height(45);

        sortByUsernameButton = new TextButton("Username", GameManager.getSkin());
        sortByScoreButton = new TextButton("Score", GameManager.getSkin());
        sortByKillButton = new TextButton("Kills", GameManager.getSkin());
        sortByTimeSurvived = new TextButton("Time", GameManager.getSkin());

        buttonTable.add(sortByUsernameButton);
        buttonTable.add(sortByScoreButton);
        buttonTable.add(sortByKillButton);
        buttonTable.add(sortByTimeSurvived);

        mainTable.add(buttonTable).padTop(10).row();

        addSortListeners();
    }

    private void addBackButton() {
        backButton = new TextButton("Back to Menu", GameManager.getSkin());
        backButton.getLabel().setFontScale(0.85f);
        mainTable.add(backButton).width(250).height(55).padTop(15);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
            }
        });
    }

    private void addSortListeners() {
        sortByUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                users.sort(Comparator.comparing(User::getUsername));
                refreshTable();
            }
        });

        sortByScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                users.sort(Comparator.comparingInt(User::getScore).reversed());
                refreshTable();
            }
        });

        sortByKillButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                users.sort(Comparator.comparingInt(User::getKills).reversed());
                refreshTable();
            }
        });

        sortByTimeSurvived.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                users.sort(Comparator.comparingInt(User::getMostTimeSurvived).reversed());
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        stage.clear();
        createUI();
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }
}
