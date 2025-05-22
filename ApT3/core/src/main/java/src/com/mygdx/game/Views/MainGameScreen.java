package src.com.mygdx.game.Views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import src.com.mygdx.game.Models.Enemies.Elder;
import src.com.mygdx.game.Models.Enemies.EyeBat;
import src.com.mygdx.game.Models.Enemies.TentacleMonster;
import src.com.mygdx.game.Models.Enemies.Tree;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.Player;
import src.com.mygdx.game.Models.Point;
import box2dLight.PointLight;
import box2dLight.RayHandler;

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
    private Label xpLabel, levelLabel, timeLabel, weaponLabel;
    private ArrayList<Image> heartImages = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private double time;
    private double totalTime;
    private float timeAccumulator = 0;
    private int passedTime = 0;
    private RayHandler rayHandler;
    private PointLight playerLight;
    private World world;
    private boolean elderSpawned = false;
    private float lasTimeSpawnedTentacle = 0;
    private float getLasTimeSpawnedEyebat = 0;
    private boolean firstTimeSpawnedTentacle = true;
    private boolean firstTimeSpawnedEyebat = true;

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        camera.zoom = 0.35f;
        time = GameManager.getNewGame().getTime() * 60;
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        GameManager.getNewGame().setGameStage(stage);
        player = GameManager.getCurrentUser().getPlayer();
        player.getWeapons().initiate();
        backgroundTexture = new Texture("map.png");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.1f);
        playerLight = new PointLight(rayHandler, 100, Color.WHITE, 300, 0, 0);
        playerLight.setSoftnessLength(100);

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
        levelLabel = new Label("Level: " + player.getLevel(), new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        levelLabel.setPosition(400, Gdx.graphics.getHeight() - 50);
        timeLabel = new Label((int) time / 60 + ":" + time % 60, new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        timeLabel.setPosition(600, Gdx.graphics.getHeight() - 50);
        weaponLabel = new Label(GameManager.getNewGame().getPlayer().getWeapons().getAmmo() + "/" +
            GameManager.getNewGame().getPlayer().getWeapons().getWeapon().getAmmoMax(),
            new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        weaponLabel.setPosition(750, Gdx.graphics.getHeight() - 50);
        fixedStage.addActor(xpLabel);
        fixedStage.addActor(levelLabel);
        fixedStage.addActor(timeLabel);
        fixedStage.addActor(weaponLabel);

        // Trees
        int treeCount = (int) (Math.random() * 20);
        if (!GameManager.getNewGame().isWasPaused()) {
            for (int i = 0; i < treeCount; i++) {
                int x = (int) (Math.random() * Gdx.graphics.getWidth() - 100);
                int y = (int) (Math.random() * Gdx.graphics.getHeight() - 100);
                boolean accepted = true;
                Tree tree = new Tree(x, y);
                for (Tree t : trees) {
                    if (tree.getBox().overlaps(t.getBox())) {
                        accepted = false;
                        break;
                    }
                }
                if (accepted) {
                    trees.add(tree);
                    GameManager.getNewGame().getTrees().add(tree);
                    Image image = tree.getImage();
                    image.setPosition(x, y);
                    stage.addActor(image);
                    System.out.println("Tree " + tree.getBox().getX() + " " + tree.getBox().getY());
                }
            }
            GameManager.getNewGame().getEyeBat().removeIf(EyeBat::isDead);
            GameManager.getNewGame().getTentacleMonsters().removeIf(TentacleMonster::isDead);
            GameManager.getNewGame().getElder().removeIf(Elder::isDead);


        }

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
                } else if (keycode == GameManager.getNewGame().getReloadKey()) {
                    player.getWeapons().setIsReloading(true);
                } else if (keycode == Input.Keys.NUM_1) { // cheat code for time
                    time = 60;
                    totalTime = 60;
                } else if (keycode == Input.Keys.NUM_2) { // cheat code for health
                    player.addHp(1);
                } else if (keycode == Input.Keys.P) { // pause the game
                    GameManager.getNewGame().setSurvivedTime(passedTime);
                    GameManager.getNewGame().setSavedGame(MainGameScreen.this);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.PAUSE_MENU.getScreen());
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
        create();
        totalTime = GameManager.getNewGame().getTime() * 60;
    }


    public void create() {
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.2f);

        playerLight = new PointLight(rayHandler, 50, Color.WHITE, 120, player.getPosition().x, player.getPosition().y);
        playerLight.setSoftnessLength(50);
        playerLight.setXray(false);
    }

    @Override

    public void render(float delta) {
        world.step(delta, 6, 2);
        playerLight.setPosition(player.getPosition().x, player.getPosition().y);
        rayHandler.updateAndRender();
        rayHandler.setCombinedMatrix(camera.combined);

        if (GameManager.getNewGame().isWasPaused()) {
            for (TentacleMonster monster : GameManager.getNewGame().getTentacleMonsters()) {
                stage.addActor(monster.getMonsterImage());
            }
            for (Tree tree : GameManager.getNewGame().getTrees()) {
                stage.addActor(tree.getImage());
            }
            GameManager.getNewGame().setWasPaused(false);
        }
        GameManager.getNewGame().getPoints().removeIf(Point::getVisible);



        player.update(delta);
        for (Image heart : heartImages) {
            heart.remove();
        }
        heartImages.clear();

        timeAccumulator += delta;
        if (timeAccumulator >= 1) {
            time -= 1;
            timeAccumulator = 0;
            passedTime += 1;
        }

        // dead
        if (player.getHP() <= 0) {
            GameManager.getNewGame().setResult("dead");
            GameManager.getNewGame().setSurvivedTime(passedTime);
            GameManager.playSound("sounds/lost.wav");
            GameManager.getNewGame().getElder().removeIf(Elder::isDead);
            GameManager.getNewGame().getEyeBat().removeIf(EyeBat::isDead);
            GameManager.getNewGame().getTentacleMonsters().removeIf(TentacleMonster::isDead);
            GameManager.getNewGame().setWasPaused(false);
            ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.GAME_OVER.getScreen());
        }

        // victory
        if (time == 0) {
            GameManager.getNewGame().setResult("victory");
            GameManager.getNewGame().setSurvivedTime(passedTime);
            GameManager.playSound("sounds/win.wav");
            GameManager.getNewGame().getElder().removeIf(Elder::isDead);
            GameManager.getNewGame().getEyeBat().removeIf(EyeBat::isDead);
            GameManager.getNewGame().getTentacleMonsters().removeIf(TentacleMonster::isDead);
            GameManager.getNewGame().setWasPaused(false);
            ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.GAME_OVER.getScreen());
        }


        if (!player.isDamaged()) {
            for (Tree tree : trees) {
                if (player.getBox().overlaps(tree.getBox())) {
                    player.setHP();
                    player.setDamaged(true);
                    player.setInvincibleTimer(1.5f);
                }
            }
        }


        // spawning enemies
        if (passedTime % 4 == 0) {
            if (firstTimeSpawnedTentacle) {
                int numberOfMonsters;
                if (totalTime == 1) {
                    numberOfMonsters = (int) (passedTime / 10) + 1;
                } else {
                    numberOfMonsters = (int) (passedTime / 30) + 1;
                }
                spawnMonsters(numberOfMonsters);
                firstTimeSpawnedTentacle = false;
                lasTimeSpawnedTentacle = passedTime;
            } else if (passedTime != lasTimeSpawnedTentacle) {
                int numberOfMonsters;
                if (totalTime == 1) {
                    numberOfMonsters = (int) (passedTime / 10) + 1;
                } else {
                    numberOfMonsters = (int) (passedTime / 30) + 1;
                }
                spawnMonsters(numberOfMonsters);
                lasTimeSpawnedTentacle = passedTime;
            }
        }

        if (passedTime >= totalTime / 4) {
            if (firstTimeSpawnedEyebat) {
                if ((passedTime & 10) == 0) {
                    int numberOfMonsters;
                    if (totalTime == 1) {
                        numberOfMonsters = (int) (5 * passedTime - (int) totalTime + 30) / 12;
                    } else {
                        numberOfMonsters = (int) (4 * passedTime - (int) totalTime + 30) / 30;
                    }
                    spawnEyeBat(numberOfMonsters);
                }
                lasTimeSpawnedTentacle = passedTime;
                firstTimeSpawnedEyebat = false;
            } else {
                if (passedTime != lasTimeSpawnedTentacle) {
                    if ((passedTime & 10) == 0) {
                        int numberOfMonsters;
                        if (totalTime == 1) {
                            numberOfMonsters = (int) (5 * passedTime - (int) totalTime + 30) / 12;
                        } else {
                            numberOfMonsters = (int) (4 * passedTime - (int) totalTime + 30) / 30;
                        }
                        spawnEyeBat(numberOfMonsters);
                    }
                    lasTimeSpawnedTentacle = passedTime;
                }
            }
        }

        if (!elderSpawned) {
            if (passedTime >= totalTime / 2) {
                spawnElder(1);
                elderSpawned = true;
            }
        }


        //updating enemies
        for (TentacleMonster monster : GameManager.getNewGame().getTentacleMonsters()) {
            monster.update(delta);
        }
        for (EyeBat monster : GameManager.getNewGame().getEyeBat()) {
            monster.update(delta);
        }
        for (Elder monster : GameManager.getNewGame().getElder()) {
            monster.update(delta);
        }

        for (Tree tree : trees) {
            stage.addActor(tree.getImage());
        }


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


        xpLabel.setText("XP: " + player.getXP());
        xpLabel.setPosition(280, Gdx.graphics.getHeight() - 50);
        levelLabel.setText("Level: " + player.getLevel());
        levelLabel.setPosition(400, Gdx.graphics.getHeight() - 50);
        timeLabel.setText((int) time / 60 + ":" + (int) (time % 60) + "\nSurvive!");
        weaponLabel.setText(GameManager.getNewGame().getPlayer().getWeapons().getAmmo() + "/" +
            GameManager.getNewGame().getPlayer().getWeapons().getWeapon().getAmmoMax());
        weaponLabel.setPosition(750, Gdx.graphics.getHeight() - 50);

        camera.position.set(player.getPlayerImage().getX() + player.getPlayerImage().getWidth() / 2,
            player.getPlayerImage().getY() + player.getPlayerImage().getHeight() / 2, 0);
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        fixedStage.act(delta);
        fixedStage.draw();

    }

    public void spawnMonsters(int count) {

        for (int i = 0; i < count; i++) {
            int random = (int) (Math.random() * 4);
            double x = 0;
            double y = 0;
            TentacleMonster monster = null;

            if (random == 0) {
                x = Math.random() * Gdx.graphics.getWidth();
                monster = new TentacleMonster((int) x, 0);

            } else if (random == 1) {
                y = Math.random() * Gdx.graphics.getHeight();
                monster = new TentacleMonster(0, (int) y);
            } else if (random == 2) {
                y = Math.random() * Gdx.graphics.getHeight();
                monster = new TentacleMonster(Gdx.graphics.getWidth(), (int) y);
            } else if (random == 3) {
                x = Math.random() * Gdx.graphics.getWidth();
                monster = new TentacleMonster((int) x, Gdx.graphics.getHeight());

            }
            GameManager.getNewGame().getTentacleMonsters().add(monster);
            stage.addActor(monster.getMonsterImage());
        }
    }

    public void spawnEyeBat(int count) {

        for (int i = 0; i < count; i++) {
            int random = (int) (Math.random() * 4);
            double x = 0;
            double y = 0;
            EyeBat monster = null;

            if (random == 0) {
                x = Math.random() * Gdx.graphics.getWidth();
                monster = new EyeBat((int) x, 0);

            } else if (random == 1) {
                y = Math.random() * Gdx.graphics.getHeight();
                monster = new EyeBat(0, (int) y);
            } else if (random == 2) {
                y = Math.random() * Gdx.graphics.getHeight();
                monster = new EyeBat(Gdx.graphics.getWidth(), (int) y);
            } else if (random == 3) {
                x = Math.random() * Gdx.graphics.getWidth();
                monster = new EyeBat((int) x, Gdx.graphics.getHeight());

            }
            GameManager.getNewGame().getEyeBat().add(monster);
            stage.addActor(monster.getMonsterImage());
        }
    }

    public void spawnElder(int count) {

        for (int i = 0; i < count; i++) {
            int random = (int) (Math.random() * 4);
            double x = 0;
            double y = 0;
            Elder monster = null;

            if (random == 0) {
                x = Math.random() * Gdx.graphics.getWidth();
                monster = new Elder((int) x, 0);

            } else if (random == 1) {
                y = Math.random() * Gdx.graphics.getHeight();
                monster = new Elder(0, (int) y);
            } else if (random == 2) {
                y = Math.random() * Gdx.graphics.getHeight();
                monster = new Elder(Gdx.graphics.getWidth(), (int) y);
            } else if (random == 3) {
                x = Math.random() * Gdx.graphics.getWidth();
                monster = new Elder((int) x, Gdx.graphics.getHeight());

            }
            GameManager.getNewGame().getElder().add(monster);
            stage.addActor(monster.getMonsterImage());
        }
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
        fixedStage.dispose();
        world.dispose();

    }
}
