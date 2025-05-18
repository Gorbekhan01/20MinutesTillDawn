package src.com.mygdx.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;

public class OpeningScreen implements Screen {
    private Stage stage;
    private BitmapFont font;
    private int posY = 0;
    private boolean animationFinished = false;
    private boolean buttonsCreated = false;
    private Table mainTable = new Table();
    Texture texture = new Texture(Gdx.files.internal("T_20Logo.png"));
    Image image = new Image(texture);

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
//        image = new Label("20 Minutes Till Dawn", GameManager.getSkin());
        image.setSize(400,150);
        mainTable.add(image);

        image.setPosition(
            Gdx.graphics.getWidth() / 2 - image.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - 100
        );

        stage.addActor(image);

        if (!animationFinished) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    animationFinished = true;
                    if (!buttonsCreated) {
                        createButtons();
                        buttonsCreated = true;
                    }
                }
            }, 1f);
        } else if (!buttonsCreated) {
            createButtons();
            buttonsCreated = true;
        }
    }

    private void createButtons() {
        Table table = new Table();
        table.setFillParent(true);
        mainTable.add(table);
        table.center();
        stage.addActor(table);
        table.padTop(50);

        TextButton signUpButton = new TextButton("Sign Up", GameManager.getSkin());
        TextButton loginButton = new TextButton("Login", GameManager.getSkin());
        TextButton exitButton = new TextButton("Exit", GameManager.getSkin());

        table.add(signUpButton).width(200).height(70).padBottom(3).row();
        table.add(loginButton).width(200).height(70).padBottom(3).row();
        table.add(exitButton).width(200).height(70).row();
        table.defaults().space(10);

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetAnimationState();
                GameManager.getGame().setScreen(Menu.SIGNUP_MENU.getScreen());
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetAnimationState();
                GameManager.getGame().setScreen(Menu.LOGIN_MENU.getScreen());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void resetAnimationState() {
        animationFinished = false;
        buttonsCreated = false;
        posY = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 0);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!animationFinished) {
            posY += 10;
            if (posY <200) {
                image.setPosition(
                    Gdx.graphics.getWidth() / 2 - image.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - 100 + posY
                );
            }
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if (image != null) {
            image.setPosition(
                (float) width / 2 - image.getWidth() / 2,
                (float) height / 2 - 100 + posY
            );
        }
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
        if (stage != null) stage.dispose();
        if (font != null) font.dispose();
    }
}
