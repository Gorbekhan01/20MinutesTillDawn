package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import src.com.mygdx.game.Models.Ability;
import src.com.mygdx.game.Models.Enums.AbilityTypes;
import src.com.mygdx.game.Models.GameManager;

import java.util.ArrayList;
import java.util.Arrays;

public class AbilityMenu implements Screen {
    private Stage stage;
    private Table mainTable;
    private TextButton selected;
    private ArrayList<AbilityTypes> selectedRandomAbilities = new ArrayList<>();
    private ArrayList<AbilityTypes> abilityTypes = new ArrayList<>();
    private ArrayList<Integer> randomNumbers = new ArrayList<>();
    private int counter = 3;
    private int selectedIndex = -1;
    private Table abilityTable1;
    private Table abilityTable2;
    private Table abilityTable3;
    private Label error;

    @Override
    public void show() {
        abilityTable1 = new Table();
        abilityTable2 = new Table();
        abilityTable3 = new Table();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        abilityTypes.addAll(Arrays.asList(AbilityTypes.values()));
        addRandomAbilities();
        createAbilityMenu();

    }

    public void createAbilityMenu() {


        Label label = new Label("select one of these abilities"
            ,new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        mainTable.add(label).center().row();
        float tableWidth = 250;
        float tableHeight = 250;

        abilityTable1.setSize(tableWidth, tableHeight);
        abilityTable2.setSize(tableWidth, tableHeight);
        abilityTable3.setSize(tableWidth, tableHeight);

        abilityTable1.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
        abilityTable2.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
        abilityTable3.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));

        Label label1 = new Label(selectedRandomAbilities.get(0).getName(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        Label label2 = new Label(selectedRandomAbilities.get(1).getName(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        Label label3 = new Label(selectedRandomAbilities.get(2).getName(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));

        Label description1 = new Label(selectedRandomAbilities.get(0).getDescription(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        description1.setFontScale(0.7f);
        Label description2 = new Label(selectedRandomAbilities.get(1).getDescription(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        description2.setFontScale(0.7f);
        Label description3 = new Label(selectedRandomAbilities.get(2).getDescription(),
            new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        description3.setFontScale(0.7f);

        Texture abilityTexture1 = new Texture(Gdx.files.internal(selectedRandomAbilities.get(0).getPath()));
        Image abilityImage1 = new Image(abilityTexture1);
        abilityImage1.setSize(100, 100);

        Texture abilityTexture2 = new Texture(Gdx.files.internal(selectedRandomAbilities.get(1).getPath()));
        Image abilityImage2 = new Image(abilityTexture2);
        abilityImage2.setSize(100, 100);

        Texture abilityTexture3 = new Texture(Gdx.files.internal(selectedRandomAbilities.get(2).getPath()));
        Image abilityImage3 = new Image(abilityTexture3);
        abilityImage3.setSize(100, 100);

        abilityTable1.add(label1).center().padBottom(10).row();
        abilityTable1.add(abilityImage1).size(100, 100).padBottom(10).row();
        abilityTable1.add(description1).center().padBottom(10).row();

        abilityTable2.add(label2).center().padBottom(10).row();
        abilityTable2.add(abilityImage2).size(100, 100).padBottom(10).row();
        abilityTable2.add(description2).center().padBottom(10).row();

        abilityTable3.add(label3).center().padBottom(10).row();
        abilityTable3.add(abilityImage3).size(100, 100).padBottom(10).row();
        abilityTable3.add(description3).center().padBottom(10).row();

        mainTable.add(abilityTable1).uniform().size(tableWidth, tableHeight).pad(30);
        mainTable.add(abilityTable2).uniform().size(tableWidth, tableHeight).pad(30);
        mainTable.add(abilityTable3).uniform().size(tableWidth, tableHeight).pad(30);

        mainTable.row();
        selected = new TextButton("Back to game", GameManager.getSkin());
        mainTable.add(selected).center().row();
        error = new Label("",new Label.LabelStyle(GameManager.getFont(2), Color.WHITE));
        error.setColor(Color.RED);
        mainTable.add(error).center().row();
        addListeners();
    }

    public void addListeners() {
        abilityTable1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedIndex = 0;
            }
        });
        abilityTable2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedIndex = 1;
            }
        });
        abilityTable3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedIndex = 2;
            }
        });
        selected.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedIndex == -1) {
                    error.setText("You should select one of these abilities!");
                    return;
                }
                Ability ability = new Ability(selectedRandomAbilities.get(selectedIndex));
                GameManager.getNewGame().getPlayer().getAbilities().add(ability);
                GameManager.getNewGame().setWasPaused(true);
                GameManager.getNewGame().setPaused(false);
                ((Game) Gdx.app.getApplicationListener()).setScreen(GameManager.getNewGame().getSavedGame());
            }
        });

    }


    public void addRandomAbilities() {
        while (counter > 0) {
            int random = (int) (Math.random() * abilityTypes.size());
            if (!randomNumbers.contains(random)) {
                randomNumbers.add(random);
                selectedRandomAbilities.add(abilityTypes.get(random));
                counter--;
            }
        }
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (selectedIndex) {
            case 0:
                abilityTable1.setBackground(GameManager.getSkin().newDrawable("white", Color.DARK_GRAY));
                abilityTable2.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
                abilityTable3.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
                break;
            case 1:
                abilityTable1.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
                abilityTable2.setBackground(GameManager.getSkin().newDrawable("white", Color.DARK_GRAY));
                abilityTable3.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
                break;
            case 2:
                abilityTable1.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
                abilityTable2.setBackground(GameManager.getSkin().newDrawable("white", Color.GRAY));
                abilityTable3.setBackground(GameManager.getSkin().newDrawable("white", Color.DARK_GRAY));
                break;
        }

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
