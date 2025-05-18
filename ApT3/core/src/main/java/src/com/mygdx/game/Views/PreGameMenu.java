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
import src.com.mygdx.game.Models.Enums.Heroes;
import src.com.mygdx.game.Models.Enums.Weapons;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.NewGame;


public class PreGameMenu implements Screen {

    private Stage stage;
    private Skin skin = GameManager.getSkin();
    private Table mainTable;
    private Label characterName, weaponName , timeName;
    private TextButton nextCBotton, prevCBotton, nextWBotton, prevWBotton, nextTBotton, prevTBotton , start , back;
    private Image character, weapon;
    private int characterCounter = 0;
    private int weaponCounter = 0;
    private int timeCounter = 0;
    private double time = 0;


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
        character = new Image();
        prevCBotton = new TextButton("<", skin);
        nextCBotton = new TextButton(">", skin);

        charactersTable.add(prevCBotton).size(80, 80).padRight(10);
        charactersTable.add(character).size(80, 100);
        charactersTable.add(nextCBotton).size(80, 80).padLeft(10).row();
        charactersTable.add(characterName).colspan(3).center().padTop(10).padBottom(20).row();


        // Weapons Table
        Table weaponTable = new Table();
        Label weaponsLabel = new Label("Weapons", skin);
        weaponsLabel.setAlignment(Align.center);
        weaponName.setFontScale(0.8f);
        weaponTable.add(weaponsLabel).colspan(3).center().padTop(10).row();
        weapon = new Image();
        prevWBotton = new TextButton("<", skin);
        nextWBotton = new TextButton(">", skin);

        weaponTable.add(prevWBotton).size(80, 80).padRight(10);
        weaponTable.add(weapon).size(80, 100);
        weaponTable.add(nextWBotton).size(80, 80).padLeft(10).row();
        weaponTable.add(weaponName).colspan(3).center().padTop(10).row();
        mainTable.add(charactersTable).width(stage.getWidth() * 0.8f).center().row();
        mainTable.add(weaponTable).width(stage.getWidth() * 0.8f).center().padTop(20).row();


        Table timeTable = new Table();
        Label timeLabel = new Label("Game Time", skin);
        timeName.setFontScale(0.8f);
        timeLabel.setAlignment(Align.center);
        timeTable.add(timeLabel).colspan(3).center().padTop(10).row();
        prevTBotton = new TextButton("<", skin);
        nextTBotton = new TextButton(">", skin);

        timeTable.add(prevTBotton).size(80, 80).padRight(10);
        timeTable.add(timeName).size(80, 80);
        timeTable.add(nextTBotton).size(80, 80).padLeft(10).row();
        mainTable.add(timeTable).width(stage.getWidth() * 0.8f).padTop(20).center().row();

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
                NewGame newGame = new NewGame(time,GameManager.getCurrentUser().getPlayer());
                GameManager.setNewGame(newGame);
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_GAME_SCREEN.getScreen());
            }

        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
            }

        });


    }


    private void updateCharacter() {
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getHeroes().get(characterCounter)));
        character.setDrawable(new Image(avatarTexture).getDrawable());
        switch (characterCounter) {
            case 0:
                GameManager.getCurrentUser().getPlayer().setHero(Heroes.DASHER);
                characterName.setText("Dasher");
                break;
            case 1:
                GameManager.getCurrentUser().getPlayer().setHero(Heroes.DIAMOND);
                characterName.setText("Diamond");
                break;
            case 2:
                GameManager.getCurrentUser().getPlayer().setHero(Heroes.LILITH);
                characterName.setText("Lilith");
                break;
            case 3:
                GameManager.getCurrentUser().getPlayer().setHero(Heroes.SCARLET);
                characterName.setText("Scarlet");
                break;
            case 4:
                GameManager.getCurrentUser().getPlayer().setHero(Heroes.SHANA);
                characterName.setText("Shana");
                break;

        }
    }

    private void updateWeapon() {
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getWeapons().get(weaponCounter)));
        weapon.setDrawable(new Image(avatarTexture).getDrawable());
        switch (weaponCounter) {
            case 0:
                GameManager.getCurrentUser().getPlayer().setWeapons(Weapons.DUAL_SMGs);
                weaponName.setText("Dual SMGs");
                break;
            case 1:
                GameManager.getCurrentUser().getPlayer().setWeapons(Weapons.REVOLVER);
                weaponName.setText("Revolver");
                break;
            case 2:
                GameManager.getCurrentUser().getPlayer().setWeapons(Weapons.SHOTGUN);
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
