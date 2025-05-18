package src.com.mygdx.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import src.com.mygdx.game.Models.GameManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import src.com.mygdx.game.Controllers.SignUpController;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.User;

public class MainMenu implements Screen {
    TextButton settingButton, profileButton, preGameButton, scoreBoardButton, hintButton, continueSavedGameButton,
        exitButton;
    private Skin skin = GameManager.getSkin();
    private Stage stage;
    private Table mainTable;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        createMainMenu();
    }

    public void createMainMenu() {

        mainTable.center();
        Label welcome = new Label("WELCOME " + GameManager.getCurrentUser().getUsername(), skin);
        mainTable.add(welcome).center().padTop(-20).padBottom(55).row();
        welcome.setFontScale(1.5f);
        Label score = new Label("Score: " + GameManager.getCurrentUser().getScore(), skin);
        mainTable.add(score).center().padBottom(15).center().row();
        score.setColor(Color.GOLD);
        Image avatarImage = new Image();
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getAvatars()
            .get(GameManager.getCurrentUser().getAvatarNumber())));
        avatarImage.setDrawable(new Image(avatarTexture).getDrawable());
        mainTable.add(avatarImage).size(200, 200).center().padBottom(8).row();
        //profile menu
        profileButton = new TextButton("Profile", skin);
        mainTable.add(profileButton).size(200, 70).center().padBottom(-30).row();
        profileButton.setColor(Color.ROYAL);
        //logout menu
        exitButton = new TextButton("Logout", skin);
        exitButton.setColor(Color.RED);
        mainTable.add(exitButton).size(200, 70).center().padBottom(15).row();


        //other buttons
        settingButton = new TextButton("Settings", skin);
        mainTable.add(settingButton).size(300, 70).center().padBottom(-20).row();

        preGameButton = new TextButton("Pre Game", skin);
        mainTable.add(preGameButton).size(300, 70).center().padBottom(-20).row();

        scoreBoardButton = new TextButton("ScoreBoard", skin);
        mainTable.add(scoreBoardButton).size(300, 70).center().padBottom(-20).row();

        hintButton = new TextButton("Hint", skin);
        mainTable.add(hintButton).size(300, 70).center().padBottom(20).row();

        stage.addActor(mainTable);
        addListeners();

    }

    private void addListeners() {
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear();
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.LOGIN_MENU.getScreen());

            }
        });

        settingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.SETTING_MENU.getScreen());
            }
        });

        preGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.PREGAME_MENU.getScreen());
            }
        });

        profileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (GameManager.getCurrentUser().getUsername().equals("guest")) {
                    // refuse
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.PROFILE_MENU.getScreen());
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
