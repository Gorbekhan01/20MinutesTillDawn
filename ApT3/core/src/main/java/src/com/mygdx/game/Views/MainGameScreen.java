package src.com.mygdx.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainGameScreen implements Screen {
    private Stage stage, fixedStage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Player player;
    private Set<Integer> pressedKeys = new HashSet<>();
    private Label xpLabel, levelLabel;
    private ArrayList<Image> heartImages = new ArrayList<>();


    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        camera.zoom = 0.3f;
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        GameManager.getNewGame().setGameStage(stage);
        player = GameManager.getCurrentUser().getPlayer();
        player.getWeapons().initiate();
        backgroundTexture = new Texture("map.png");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        //health
        fixedStage = new Stage(new ScreenViewport());

        for (int i = 1; i <= player.getHero().getHP(); i++) {
            Texture texture = new Texture("others/RedHeart.png");
            Image image = new Image(texture);
            image.setPosition(20 + (i * 30), Gdx.graphics.getHeight() - 50);
            fixedStage.addActor(image);
            heartImages.add(image);
        }

        // XP & Level
        xpLabel = new Label("XP: " + player.getXP(), new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        xpLabel.setPosition(280, Gdx.graphics.getHeight() - 50);
        levelLabel = new Label("Level: " + player.getXP(), new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        levelLabel.setPosition(400, Gdx.graphics.getHeight() - 50);

        fixedStage.addActor(xpLabel);
        fixedStage.addActor(levelLabel);

        stage.addActor(player.getPlayerImage());

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                pressedKeys.add(keycode);
                if (pressedKeys.contains(GameManager.getNewGame().getLeftKey()) &&
                    pressedKeys.contains(GameManager.getNewGame().getUpKey())) {
                    player.moveUpLeft();
                } else if (pressedKeys.contains(GameManager.getNewGame().getRightKey()) &&
                    pressedKeys.contains(GameManager.getNewGame().getUpKey())) {
                    player.moveUpRight();
                } else if (pressedKeys.contains(GameManager.getNewGame().getLeftKey()) &&
                    pressedKeys.contains(GameManager.getNewGame().getDownKey())) {
                    player.moveDownLeft();
                } else if (pressedKeys.contains(GameManager.getNewGame().getRightKey()) &&
                    pressedKeys.contains(GameManager.getNewGame().getDownKey())) {
                    player.moveDownRight();
                } else if (keycode == GameManager.getNewGame().getLeftKey()) {
                    player.moveLeft();
                } else if (keycode == GameManager.getNewGame().getRightKey()) {
                    player.moveRight();
                } else if (keycode == GameManager.getNewGame().getUpKey()) {
                    player.moveUp();
                } else if (keycode == GameManager.getNewGame().getDownKey()) {
                    player.moveDown();
                } else if (keycode == GameManager.getNewGame().getShootKey()) {
                    player.shoot();
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                pressedKeys.remove(keycode);
                player.stop();
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }


    @Override

    public void render(float delta) {
        player.update(delta);
        for (Image heart : heartImages) {
            heart.remove();
        }
        heartImages.clear();
        int lastInt = 0;

        for (int i = 1; i <= player.getHP(); i++) {
            Texture texture = new Texture("others/RedHeart.png");
            Image image = new Image(texture);
            image.setPosition(20 + (i * 30), Gdx.graphics.getHeight() - 50);
            lastInt = i;
            fixedStage.addActor(image);
            heartImages.add(image);
        }

        if (player.getHP() - player.getHero().getHP() != 0) {
            for (int i = 1; i <= Math.abs(player.getHero().getHP() - player.getHP()); i++) {
                Texture texture = new Texture("others/BlackHeart.png");
                Image image = new Image(texture);
                image.setPosition(20 + ((i + lastInt) * 30), Gdx.graphics.getHeight() - 50);
                fixedStage.addActor(image);
                heartImages.add(image);
            }
        }

        xpLabel = new Label("XP: " + player.getXP(), new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        xpLabel.setPosition(280, Gdx.graphics.getHeight() - 50);
        levelLabel = new Label("Level: " + player.getXP(), new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        levelLabel.setPosition(400, Gdx.graphics.getHeight() - 50);

        camera.position.set(player.getPlayerImage().getX() + player.getPlayerImage().getWidth() / 2,
            player.getPlayerImage().getY() + player.getPlayerImage().getHeight() / 2, 0);
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        fixedStage.act(delta);
        fixedStage.draw();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        backgroundTexture.dispose();
    }
}
