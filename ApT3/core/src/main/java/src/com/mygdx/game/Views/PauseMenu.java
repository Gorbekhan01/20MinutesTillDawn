package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import src.com.mygdx.game.Models.Ability;
import src.com.mygdx.game.Models.Enemies.Elder;
import src.com.mygdx.game.Models.Enemies.EyeBat;
import src.com.mygdx.game.Models.Enemies.TentacleMonster;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;

public class PauseMenu implements Screen {

    private Stage stage;
    private Table mainTable;
    private TextButton backButton, giveUpButton;
    private Label cheatCodesLabel, abilityLabel;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        stage.addActor(mainTable);

        backButton = new TextButton("Resume", GameManager.getSkin());
        giveUpButton = new TextButton("Give Up", GameManager.getSkin());
        GameManager.getNewGame().getEyeBat().removeIf(EyeBat::isDead);
        GameManager.getNewGame().getTentacleMonsters().removeIf(TentacleMonster::isDead);
        GameManager.getNewGame().getElder().removeIf(Elder::isDead);

        createPauseMenu();
    }

    private void createPauseMenu() {
        Table cheatCodesTable = new Table();
        cheatCodesLabel = new Label("Cheat Codes",new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        cheatCodesTable.add(cheatCodesLabel).padBottom(10).center().row();

        Label cheatCodesInfo = new Label(
            "Press 1 -> Decrease time to 1 min \n" +
                "Press 2 -> Add HP \n", new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        cheatCodesTable.add(cheatCodesInfo).center().row();

        Table abilityTable = new Table();
        abilityLabel = new Label("Ability", new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        abilityTable.add(abilityLabel).padBottom(10).center().row();

        for (Ability ability : GameManager.getNewGame().getPlayer().getAbilities()) {
            Texture texture = new Texture(ability.getAbilityType().getPath());
            Image image = new Image(texture);
            image.setSize(12, 12);
            abilityTable.add(image).padBottom(10).padRight(10);
        }

        mainTable.add(cheatCodesTable).padBottom(20).row();
        mainTable.add(abilityTable).padBottom(20).row();

        mainTable.add(backButton).padBottom(10).row();
        mainTable.add(giveUpButton).padBottom(10).row();

        addListeners();
    }

    private void addListeners() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                GameManager.getNewGame().setWasPaused(true);
                ((Game) Gdx.app.getApplicationListener()).setScreen(GameManager.getNewGame().getSavedGame());
            }
        });

        giveUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                GameManager.getNewGame().setResult("dead");
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.GAME_OVER.getScreen());
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
