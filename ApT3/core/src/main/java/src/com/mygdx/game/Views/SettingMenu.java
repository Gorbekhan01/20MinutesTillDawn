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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.Pair;

import javax.swing.event.ChangeEvent;
import java.util.List;

import static src.com.mygdx.game.MainGame.backgroundMusic;

public class SettingMenu implements Screen {
    private Slider volumeSlider;
    private TextButton music1 , music2 , backButton;
    private CheckBox checkBox;

    private TextField leftKey, rightKey, upKey, downKey, shootKey, reloadKey;
    private int leftInt=0, rightInt=0, upInt=0, downInt=0, shootInt=0, reloadInt=0;
    private CheckBox checkLeft;


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
        musicSetting.setColor(Color.ORANGE);
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
        mainTable.add(checkBox).width(200).padTop(20).padBottom(20).row();

        Label keyboardTitle = new Label("-- Keyboard Controls --", skin);
        keyboardTitle.setColor(Color.OLIVE);
        keyboardTitle.setFontScale(1f);
        mainTable.add(keyboardTitle).padTop(30).row();

        Table keyboardTable = new Table();

        leftKey = new TextField("", skin);
        rightKey = new TextField("", skin);
        upKey = new TextField("", skin);
        downKey = new TextField("", skin);
        shootKey = new TextField("", skin);
        reloadKey = new TextField("", skin);

        Label lblLeft = new Label("Left", skin);
        lblLeft.setFontScale(0.8f);

        Label lblRight = new Label("Right", skin);
        lblRight.setFontScale(0.8f);

        Label lblUp = new Label("Up", skin);
        lblUp.setFontScale(0.8f);

        Label lblDown = new Label("Down", skin);
        lblDown.setFontScale(0.8f);

        Label lblReload = new Label("Reload", skin);
        lblReload.setFontScale(0.8f);

        Label lblShoot = new Label("Shoot", skin);
        lblShoot.setFontScale(0.8f);

        checkLeft = new CheckBox("   Left Click", skin);
        checkLeft.getLabel().setFontScale(0.7f);

        keyboardTable.add(lblLeft);
        keyboardTable.add(leftKey).width(80).pad(10);
        keyboardTable.add(lblRight);
        keyboardTable.add(rightKey).width(80).pad(10);
        keyboardTable.add(lblUp);
        keyboardTable.add(upKey).width(80).pad(10);
        keyboardTable.row();
        keyboardTable.add(lblDown);
        keyboardTable.add(downKey).width(80).pad(10);
        keyboardTable.add(lblReload);
        keyboardTable.add(reloadKey).width(80).pad(10);
        keyboardTable.add(lblShoot);
        keyboardTable.add(shootKey).width(80).pad(10);
        keyboardTable.add(checkLeft).width(80).pad(30);

        mainTable.add(keyboardTable).width(350).padTop(20).center().row();

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
                if (upInt != 0) GameManager.setUpKey(upInt);
                if (downInt != 0) GameManager.setDownKey(downInt);
                if (rightInt != 0) GameManager.setRightKey(rightInt);
                if (leftInt != 0) GameManager.setLeftKey(leftInt);
                if (shootInt != 0) GameManager.setShootKey(shootInt);
                if (reloadInt != 0) GameManager.setReloadKey(reloadInt);
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
            }
        });

        leftKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String down = leftKey.getText();
                if (!down.isEmpty()) {
                    for (Pair<String, Integer> pair : GameManager.getKeys()) {
                        if (pair.getFirst().equals(down)) {
                            leftInt = pair.getSecond() - 68;
                        }
                    }
                }
            }
        });

        rightKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String key = rightKey.getText();
                if (!key.isEmpty()) {
                    for (Pair<String, Integer> pair : GameManager.getKeys()) {
                        if (pair.getFirst().equals(key)) {
                            rightInt = pair.getSecond() - 68;
                        }
                    }
                }
            }
        });

        upKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String key = upKey.getText();
                if (!key.isEmpty()) {
                    for (Pair<String, Integer> pair : GameManager.getKeys()) {
                        if (pair.getFirst().equals(key)) {
                            upInt = pair.getSecond() - 68;
                        }
                    }
                }
            }
        });

        downKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String key = downKey.getText();
                if (!key.isEmpty()) {
                    for (Pair<String, Integer> pair : GameManager.getKeys()) {
                        if (pair.getFirst().equals(key)) {
                            downInt = pair.getSecond() - 68;
                        }
                    }
                }
            }
        });

        shootKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String key = shootKey.getText();
                if (!key.isEmpty()) {
                    for (Pair<String, Integer> pair : GameManager.getKeys()) {
                        if (pair.getFirst().equals(key)) {
                            shootInt = pair.getSecond() - 68;
                        }
                    }
                }
            }
        });

        reloadKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String key = reloadKey.getText();
                if (!key.isEmpty()) {
                    for (Pair<String, Integer> pair : GameManager.getKeys()) {
                        if (pair.getFirst().equals(key)) {
                            reloadInt = pair.getSecond() - 68;
                        }
                    }
                }
            }
        });
        checkLeft.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (checkLeft.isChecked()) {
                    GameManager.setLeftShoot(true);
                } else {
                    GameManager.setLeftShoot(false);
                }
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
