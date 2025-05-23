package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import src.com.mygdx.game.Models.*;
import src.com.mygdx.game.Models.Enums.Heroes;
import src.com.mygdx.game.Models.Enums.Weapons;


public class PreGameMenu implements Screen {

    private Stage stage;
    private Skin skin = GameManager.getSkin();
    private Table mainTable;
    private Label characterName, weaponName , timeName;
    private TextButton nextCBotton, prevCBotton, nextWBotton, prevWBotton, nextTBotton, prevTBotton , start , back;
    private TextField rightKey , leftKey, upKey, downKey , shootKey , reload;
    private int rightInt=0 , leftInt=0 , upInt=0 , downInt=0 , shootInt =0 , reloadInt =0;
    private Image character, weapon;
    private int characterCounter = 0;
    private int weaponCounter = 0;
    private int timeCounter = 0;
    private double time = 0;
    private Weapons weaponType;
    private Player player;


    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        characterName = new Label("", skin);
        weaponName = new Label("", skin);
        timeName = new Label("", skin);
        preGameMenuCreator();
        player = new Player();
        weaponType = Weapons.DUAL_SMGs;
    }

    public void preGameMenuCreator() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);


        // Characters Table
        Table charactersTable = new Table();
        Label charactersLabel = new Label("Characters", skin);
        characterName.setFontScale(0.8f);
        charactersLabel.setAlignment(Align.center);
        charactersTable.add(charactersLabel).colspan(3).center().padTop(10).row();
        // show first character
        character = new Image();
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getHeroes().get(characterCounter)));
        character.setDrawable(new Image(avatarTexture).getDrawable());
        characterName.setText("Dasher");

        prevCBotton = new TextButton("<", skin);
        nextCBotton = new TextButton(">", skin);

        charactersTable.add(prevCBotton).size(80, 80).padRight(10);
        charactersTable.add(character).size(80, 100);
        charactersTable.add(nextCBotton).size(80, 80).padLeft(10).row();
        charactersTable.add(characterName).colspan(3).center().padTop(10).padBottom(10).row();


        // Weapons Table
        Table weaponTable = new Table();
        Label weaponsLabel = new Label("Weapons", skin);
        weaponsLabel.setAlignment(Align.center);
        weaponName.setFontScale(0.8f);
        weaponTable.add(weaponsLabel).colspan(3).center().padTop(10).row();

        // show first weapon
        weapon = new Image();
        Texture avatarTextureTemp = new Texture(Gdx.files.internal(GameManager.getWeapons().get(weaponCounter)));
        weapon.setDrawable(new Image(avatarTextureTemp).getDrawable());
        weaponName.setText("Dual SMGs");


        prevWBotton = new TextButton("<", skin);
        nextWBotton = new TextButton(">", skin);

        weaponTable.add(prevWBotton).size(80, 80).padRight(10);
        weaponTable.add(weapon).size(80, 100);
        weaponTable.add(nextWBotton).size(80, 80).padLeft(10).row();
        weaponTable.add(weaponName).colspan(3).center().padTop(10).row();
        mainTable.add(charactersTable).width(stage.getWidth() * 0.8f).left().row();
        mainTable.add(weaponTable).width(stage.getWidth() * 0.8f).right().row();


        Table timeTable = new Table();
        Label timeLabel = new Label("Game Time", skin);
        timeName.setFontScale(0.8f);
        timeLabel.setAlignment(Align.center);
        timeTable.add(timeLabel).colspan(3).center().padTop(10).row();
        prevTBotton = new TextButton("<", skin);
        nextTBotton = new TextButton(">", skin);

        // show first time
        time = 2.5;
        timeName.setText("2.5 min");

        timeTable.add(prevTBotton).size(80, 80).padRight(10);
        timeTable.add(timeName).size(80, 80);
        timeTable.add(nextTBotton).size(80, 80).padLeft(10).row();
        mainTable.add(timeTable).width(stage.getWidth() * 0.8f).padTop(10).center().row();

        Label keyboardLabel = new Label("Keyboard settings", skin);
        Table keyboardTable = new Table();
        keyboardLabel.setAlignment(Align.center);
        keyboardTable.add(keyboardLabel).colspan(3).center().padTop(20).padBottom(10).row();

        Label rightKeyLabel = new Label("Right", skin);
        rightKeyLabel.setFontScale(0.8f);
        keyboardTable.add(rightKeyLabel).padRight(5);
        rightKey = new TextField("", skin);
        keyboardTable.add(rightKey).size(80, 40).padRight(10);

        Label leftKeyLabel = new Label("Left", skin);
        leftKeyLabel.setFontScale(0.8f);
        keyboardTable.add(leftKeyLabel).padRight(5);
        leftKey = new TextField("", skin);
        keyboardTable.add(leftKey).size(80, 40).padRight(10);

        Label upKeyLabel = new Label("Up", skin);
        upKeyLabel.setFontScale(0.8f);
        keyboardTable.add(upKeyLabel).padRight(5);
        upKey = new TextField("", skin);
        keyboardTable.add(upKey).size(80, 40).padRight(10).row();

        Label downKeyLabel = new Label("Down", skin);
        downKeyLabel.setFontScale(0.8f);
        keyboardTable.add(downKeyLabel).padRight(5);
        downKey = new TextField("", skin);
        keyboardTable.add(downKey).size(80, 40).padRight(10).padTop(10);

        Label reloadLabel = new Label("Reload", skin);
        reloadLabel.setFontScale(0.8f);
        keyboardTable.add(reloadLabel).padRight(5);
        reload = new TextField("", skin);
        keyboardTable.add(reload).size(80, 40).padRight(10).padTop(10);

        Label shootKeyLabel = new Label("Shoot", skin);
        shootKeyLabel.setFontScale(0.8f);
        keyboardTable.add(shootKeyLabel).padRight(5);
        shootKey = new TextField("", skin);
        keyboardTable.add(shootKey).size(80, 40).padTop(10);

        mainTable.add(keyboardTable).width(stage.getWidth() * 0.8f).padTop(20).center().row();


        start = new TextButton("Start", skin);
        mainTable.add(start).width(200).padTop(20).center().row();
        back = new TextButton("Back", skin);
        mainTable.add(back).width(200).center().row();

        stage.addActor(mainTable);


        addListeners();

    }

    public void addListeners() {
        nextCBotton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterCounter++;
                if (characterCounter >= 4) {
                    characterCounter = 4;
                }
                updateCharacter();
            }


        });
        prevCBotton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterCounter--;
                if (characterCounter <= 0) {
                    characterCounter = 0;
                }
                updateCharacter();
            }

        });

        nextWBotton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                weaponCounter++;
                if (weaponCounter >= 2) {
                    weaponCounter = 2;
                }
                updateWeapon();
            }


        });
        prevWBotton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                weaponCounter--;
                if (weaponCounter <= 0) {
                    weaponCounter = 0;
                }
                updateWeapon();
            }

        });

        nextTBotton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                timeCounter++;
                if (timeCounter >= 3) {
                    timeCounter = 3;
                }
                updateTimer();
            }


        });
        prevTBotton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                timeCounter--;
                if (timeCounter <= 0) {
                    timeCounter = 0;
                }
                updateTimer();
            }

        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getCurrentUser().setPlayer(player);
                NewGame newGame = new NewGame(time,GameManager.getCurrentUser().getPlayer());
                if (upInt!=0) newGame.setUpKey(upInt);
                if (downInt!=0) newGame.setDownKey(downInt);
                if (rightInt!=0) newGame.setRightKey(rightInt);
                if (leftInt!=0) newGame.setLeftKey(leftInt);
                if (shootInt!=0) newGame.setShootKey(shootInt);
                if (reloadInt!=0) newGame.setShootKey(reloadInt);
                GameManager.setNewGame(newGame);
                System.out.println("new gm");
                Weapon weapon1 = new Weapon(weaponType);
                newGame.getPlayer().setWeapons(weapon1);
                GameManager.getNewGame().getPlayer().initializePlayer();
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_GAME_SCREEN.getScreen());
            }

        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
                            leftInt = pair.getSecond()-68;
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
                            rightInt = pair.getSecond()-68;
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
                            upInt = pair.getSecond()-68;
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
                            downInt = pair.getSecond()-68;
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
                            shootInt = pair.getSecond()-68;
                        }
                    }
                }
            }
        });

    }


    private void updateCharacter() {
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getHeroes().get(characterCounter)));
        character.setDrawable(new Image(avatarTexture).getDrawable());
        switch (characterCounter) {
            case 0:
                player.setHero(Heroes.DASHER);
                characterName.setText("Dasher");
                break;
            case 1:
                player.setHero(Heroes.DIAMOND);
                characterName.setText("Diamond");
                break;
            case 2:
                player.setHero(Heroes.LILITH);
                characterName.setText("Lilith");
                break;
            case 3:
                player.setHero(Heroes.SCARLET);
                characterName.setText("Scarlet");
                break;
            case 4:
                player.setHero(Heroes.SHANA);
                characterName.setText("Shana");
                break;

        }
    }

    private void updateWeapon() {
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getWeapons().get(weaponCounter)));
        weapon.setDrawable(new Image(avatarTexture).getDrawable());
        switch (weaponCounter) {
            case 0:
                weaponType = Weapons.DUAL_SMGs;
                weaponName.setText("Dual SMGs");
                break;
            case 1:
                weaponType = Weapons.REVOLVER;
                weaponName.setText("Revolver");
                break;
            case 2:
                weaponType = Weapons.SHOTGUN;
                weaponName.setText("Shotgun");
                break;
        }
    }

    private void updateTimer() {

        switch (timeCounter) {
            case 0:
                time = 2.5;
                timeName.setText("2.5 min");
                break;
            case 1:
                time = 5;
                timeName.setText("5 min");
                break;
            case 2:
                time = 10 ;
                timeName.setText("10 min");
                break;
            case 3:
                time = 20 ;
                timeName.setText("20 min");
                break;

        }
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
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
