package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;

import javax.swing.event.ChangeEvent;

import static src.com.mygdx.game.MainGame.backgroundMusic;

public class SettingMenu implements Screen {
    private Slider volumeSlider;
    private TextButton music1 , music2 , backButton;
    private CheckBox checkBox;
    private Table mainTable;
    private Stage stage;
    private Skin skin = GameManager.getSkin();

    public void show() {
        stage = new Stage();
        mainTable = new Table();
        mainTable.setFillParent(true);

        createSetting();
        addListeners();

        Gdx.input.setInputProcessor(stage);
    }

    private void createSetting() {
        mainTable.clear();

        Label title = new Label("Settings", skin);
        title.setFontScale(1.5f);
        mainTable.add(title).center().padTop(50).padBottom(60).row();

        Label musicSetting = new Label("-- Music --", skin);
        musicSetting.setFontScale(1f);
        mainTable.add(musicSetting).padTop(10).row();

        volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setValue(0.5f);

        Label volumeLabel = new Label("Volume", skin);
        mainTable.add(volumeLabel).padTop(50).left().row();
        mainTable.add(volumeSlider).width(200).padTop(10).row();

        music1 = new TextButton("music 1", skin);
        music2 = new TextButton("music 2", skin);

        mainTable.add(music1).size(200, 60).padTop(20).center().row();
        mainTable.add(music2).size(200, 60).padTop(-10).center().row();
        checkBox = new CheckBox("  SFX sound ON", skin);
        mainTable.add(checkBox).width(200).padTop(20).row();
        backButton = new TextButton("back", skin);
        mainTable.add(backButton).width(200).padTop(20).row();
        stage.addActor(mainTable);
    }

    private void addListeners() {
        volumeSlider.addListener(event -> {
            GameManager.setMusicVolume(volumeSlider.getValue());
            return true;
        });
        music1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.changeMusic(1);
            }
        });
        music2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.changeMusic(2);
            }
        });
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (checkBox.isChecked()) {
                    GameManager.setSFX(true);
                } else {
                    GameManager.setSFX(false);
                }
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        GameManager.setMusicVolume(volumeSlider.getValue());
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
