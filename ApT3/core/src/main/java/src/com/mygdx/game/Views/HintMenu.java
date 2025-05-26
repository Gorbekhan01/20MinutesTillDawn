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
import com.badlogic.gdx.utils.Align;
import src.com.mygdx.game.Models.Enums.AbilityTypes;
import src.com.mygdx.game.Models.Enums.Heroes;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;

public class HintMenu implements Screen {
    private static final float PADDING = 15f;
    private static final float ITEM_PADDING = 4f;
    private static final float TITLE_PADDING = 6f;
    private static final float IMAGE_SIZE = 48f;
    private static final float ABILITY_IMAGE_SIZE = 40f;
    private static final int ITEMS_PER_ROW = 5;

    private Stage stage;
    private TextButton backButton;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(PADDING);

        mainTable.add(createSectionTitle("== CHARACTERS ==", Color.CYAN)).padBottom(TITLE_PADDING).row();
        mainTable.add(createHeroSection()).padBottom(PADDING).row();

        mainTable.add(createSectionTitle("== ABILITIES ==", Color.ORANGE)).padBottom(TITLE_PADDING).row();
        mainTable.add(createAbilitySection()).padBottom(PADDING).row();

        mainTable.add(createSectionTitle("== CONTROLS ==", Color.GREEN)).padBottom(TITLE_PADDING).row();
        mainTable.add(createControlsSection()).padBottom(PADDING).row();

        mainTable.add(createSectionTitle("== CHEAT CODES ==", Color.SALMON)).padBottom(TITLE_PADDING).row();
        mainTable.add(createCheatsSection());

        backButton = new TextButton("  back  ",GameManager.getSkin());
        mainTable.row();
        mainTable.row();
        mainTable.add(backButton).row();

        stage.addActor(mainTable);
        addListeners();
    }

    private Table createHeroSection() {
        Table section = new Table();

        int count = 0;
        for (Heroes hero : Heroes.values()) {
            if (count % ITEMS_PER_ROW == 0 && count != 0) {
                section.row();
            }
            section.add(createHeroCard(hero)).pad(ITEM_PADDING);
            count++;
        }

        return section;
    }

    private Table createAbilitySection() {
        Table section = new Table();

        int count = 0;
        for (AbilityTypes ability : AbilityTypes.values()) {
            if (count % ITEMS_PER_ROW == 0 && count != 0) {
                section.row();
            }
            section.add(createAbilityCard(ability)).pad(ITEM_PADDING);
            count++;
        }

        return section;
    }

    private Table createControlsSection() {
        Table section = new Table();

        Table col1 = new Table();
        addControl(col1, "Shoot", GameManager.getShootKey());
        col1.row();
        addControl(col1, "Move Down", GameManager.getDownKey());
        col1.row();
        addControl(col1, "Move Right", GameManager.getRightKey());

        Table col2 = new Table();
        addControl(col2, "Reload", GameManager.getReloadKey());
        col2.row();
        addControl(col2, "Move Left", GameManager.getLeftKey());
        col2.row();
        addControl(col2, "Move Up", GameManager.getUpKey());

        section.add(col1).padRight(10f);
        section.add(col2);

        return section;
    }

    private Table createCheatsSection() {
        Table section = new Table();

        section.add(createCheatLabel("[1] Set time to 1 min")).left().row();
        section.add(createCheatLabel("[2] Add HP")).left().row();
        section.add(createCheatLabel("[3] Add Level")).left().row();
        section.add(createCheatLabel("[4] Start boss fight")).left().row();

        return section;
    }

    private Label createSectionTitle(String text, Color color) {
        Label label = new Label(text, new Label.LabelStyle(GameManager.getFont(1), color));
        label.setAlignment(Align.center);
        return label;
    }

    private Table createHeroCard(Heroes hero) {
        Table card = new Table();

        Image image = new Image(new Texture(hero.getImages()[0]));
        Label info = new Label(hero.getName() + "\nSpeed: " + hero.getSpeed() + "\nHP: " + hero.getHP(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        info.setFontScale(0.7f);
        info.setAlignment(Align.center);

        card.add(image).size(IMAGE_SIZE, IMAGE_SIZE).padBottom(2f).row();
        card.add(info);

        return card;
    }

    private Table createAbilityCard(AbilityTypes ability) {
        Table card = new Table();

        Image image = new Image(new Texture(ability.getPath()));
        Label info = new Label(ability.getDescription(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        info.setFontScale(0.65f);
        info.setAlignment(Align.center);

        card.add(image).size(ABILITY_IMAGE_SIZE, ABILITY_IMAGE_SIZE).padBottom(2f).row();
        card.add(info);

        return card;
    }

    private void addControl(Table table, String action, int keyCode) {
        String keyStr = (keyCode == 62) ? "Space" : String.valueOf((char)(keyCode + 68));
        if (GameManager.isLeftShoot() && action.equals("Shoot")){
            keyStr = "Left";
        }
        Label lbl = new Label("[" + keyStr + "] " + action,
            new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        lbl.setFontScale(0.75f);
        table.add(lbl).left().padBottom(2f);
    }

    private Label createCheatLabel(String text) {
        Label lbl = new Label(text, new Label.LabelStyle(GameManager.getFont(1), Color.WHITE));
        lbl.setFontScale(0.75f);
        return lbl;
    }

    private void addListeners(){
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (GameManager.getNewGame()!=null) {
                        GameManager.getNewGame().setWasPaused(true);
                        ((Game) Gdx.app.getApplicationListener()).setScreen(GameManager.getNewGame().getSavedGame());
                    } else {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());

                    }
                } catch (Exception e) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
                }

            }

        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
    @Override public void dispose() { stage.dispose(); }
}
