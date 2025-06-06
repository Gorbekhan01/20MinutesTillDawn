package src.com.mygdx.game.Views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import javax.swing.JFileChooser;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Controllers.ProfileMenuController;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ProfileMenu implements Screen {
    private Stage stage;
    private Skin skin = GameManager.getSkin();
    private TextButton usernameChangeButton, passwordChangeButton, selectFileButton, backButton, deleteAccountButton;
    private TextField username, password;
    private Label error;
    private Table mainTable;
    private ProfileMenuController controller;
    private Texture avatarTexture;
    private Image avatarImage ;
    DragAndDrop dragAndDrop = new DragAndDrop();


    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        controller = new ProfileMenuController();
        avatarImage = new Image();
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getAvatars()
            .get(GameManager.getCurrentUser().getAvatarNumber())));
        avatarImage.setDrawable(new Image(avatarTexture).getDrawable());


        createProfileMenu();


    }

    private void createProfileMenu() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(skin.get(TextField.TextFieldStyle.class));
        textFieldStyle.font = GameManager.getFont(2);




        selectFileButton = new TextButton("Select Image", skin);
        selectFileButton.setPosition(100, 100);

        mainTable.add(avatarImage).size(100, 100).row();
        mainTable.add(selectFileButton).row();

        Table usernameTable = new Table();

        Label titleUsername = new Label("username", skin);
        titleUsername.setFontScale(0.8f);
        usernameTable.add(titleUsername).left().padRight(10);
        username = new TextField("", skin);
        username.setStyle(textFieldStyle);
        usernameTable.add(username).left();
        usernameChangeButton = new TextButton("change", skin);
        usernameTable.add(usernameChangeButton).right();

        mainTable.add(usernameTable).center();
        mainTable.row();

        Table passwordTable = new Table();
        Label titlePassword = new Label("password", skin);
        titlePassword.setFontScale(0.8f);
        passwordTable.add(titlePassword).left().padRight(10);
        password = new TextField("", skin);
        passwordTable.add(password).left();
        passwordChangeButton = new TextButton("change", skin);
        passwordTable.add(passwordChangeButton).right();

        mainTable.add(passwordTable).center().row();
        mainTable.row();

        deleteAccountButton = new TextButton("Delete Account", skin);
        deleteAccountButton.setColor(Color.RED);
        mainTable.add(deleteAccountButton).padTop(20).center().row();

        backButton = new TextButton("Back", skin);
        mainTable.add(backButton).padTop(10).center();

        addListeners();
        error = new Label("", skin);
        mainTable.row();
        mainTable.add(error);
        stage.addActor(mainTable);


    }

    private void addListeners() {

        selectFileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               updateAvatar();
            }
        });

        usernameChangeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (username.getText().isEmpty()) {
                    error.setText("username field is empty");
                    return;
                }
                String result = controller.isUsernameTaken(username.getText());
                if (result != null) {
                   error.setText(result);
                } else {
                    error.setText("username changed!");
                    GameManager.getCurrentUser().setUsername(username.getText());
                }
            }
        });

        passwordChangeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (password.getText().isEmpty()) {
                    error.setText("password field is empty");
                    return;
                }
                String result = controller.checkPasswordStrength(password.getText());
                if (result != null) {
                    error.setText(result);
                } else {
                    error.setText("password changed!");
                    GameManager.getCurrentUser().setUsername(password.getText());
                }
            }
        });


        deleteAccountButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getUsers().remove(GameManager.getCurrentUser());
                try {
                    GameManager.saveUsers();
                } catch (Exception e) {
                    System.out.println("Error saving users");
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.LOGIN_MENU.getScreen());
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
            }
        });


    }

    private void updateAvatar() {
        Random random = new Random();
        int randomInt = random.nextInt(11);
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getAvatars().get(randomInt)));
        avatarImage.setDrawable(new Image(avatarTexture).getDrawable());
        GameManager.getCurrentUser().setAvatarNumber(randomInt);
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
