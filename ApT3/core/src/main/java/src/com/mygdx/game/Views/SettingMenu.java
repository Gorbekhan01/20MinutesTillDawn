package src.com.mygdx.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import src.com.mygdx.game.Models.GameManager;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static src.com.mygdx.game.MainGame.backgroundMusic;

public class SettingMenu implements Screen {
    private Slider volumeSlider;
    private TextButton music1 , music2;
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
        mainTable.add(volumeSlider).width(200).padTop(10).right();

        stage.addActor(mainTable);
    }

    private void addListeners() {
        volumeSlider.addListener(event -> {
            GameManager.setMusicVolume(volumeSlider.getValue());
            return true;
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        System.out.println(volumeSlider.getValue());
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
