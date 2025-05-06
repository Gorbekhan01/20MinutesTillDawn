package src.com.mygdx.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import src.com.mygdx.game.Controllers.LoginMenuController;
import src.com.mygdx.game.Controllers.SignUpController;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.User;

public class LoginMenu implements Screen {

    private TextField usernameField, passwordField, newPasswordField;
    private TextButton loginButton, forgetPasswordButton , newPasswordConfirmButton;
    private Stage stage;
    private Table mainTable = new Table();
    private Label errorLabel;
    private Skin skin = GameManager.getSkin();
    private LoginMenuController controller;
    private boolean first = true;
    Table buttonTable = new Table();
    private User currentUser;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller = new LoginMenuController();
        createLoginScreen();
    }

    private void createLoginScreen() {
        mainTable.setFillParent(true);
        mainTable.defaults().pad(10);
        Label titleLabel = new Label(">> Login Menu <<", skin);
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        mainTable.add(titleLabel).colspan(2).padBottom(30).row();
        mainTable.add(new Label("Username", skin)).right();
        mainTable.add(usernameField).width(180).height(30).row();
        mainTable.add(new Label("Password", skin)).right();
        mainTable.add(passwordField).width(180).height(30).row();
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        stage.addActor(mainTable);


        loginButton = new TextButton("Login", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);
        buttonTable.add(loginButton).width(180).height(70).row();
        buttonTable.add(forgetPasswordButton).width(360).height(70).row();
        mainTable.add(buttonTable).colspan(2).padTop(20).row();
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        errorLabel.setSize(400, 30);
        errorLabel.setFontScale(0.8f);
        mainTable.add(errorLabel).colspan(2).pad(10);
        addListeners();


    }

    private void addListeners() {
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                errorLabel.setText("");

                if (username.isEmpty() || password.isEmpty()) {
                    errorLabel.setText("Please fill all the fields");
                    return;
                }

                //find user by username
                if (!GameManager.getUsers().isEmpty()) {
                    for (User user : GameManager.getUsers()) {
                        if (user.getUsername().equals(username)) {
                            currentUser = user;
                            break;
                        }
                    }
                }

                if (currentUser == null) {
                    errorLabel.setText("User not found");
                    return;
                }
                if (!controller.checkPassword(currentUser, password)) {
                    errorLabel.setText("Wrong Password");
                }
            }

        });

        forgetPasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                errorLabel.setColor(Color.RED);
                if (first) {
                    String username = usernameField.getText();
                    if (usernameField.getText().isEmpty()) {
                        errorLabel.setText("Please first enter username");
                        return;
                    }

                    //find user by username
                    if (!GameManager.getUsers().isEmpty()) {
                        for (User user : GameManager.getUsers()) {
                            if (user.getUsername().equals(username)) {
                                currentUser = user;
                                break;
                            }
                        }
                    }

                    if (currentUser == null) {
                        errorLabel.setText("User not found");
                        return;
                    }
                    Label changePassword = new Label("Set a new Password", skin);
                    newPasswordField = new TextField("", skin);

                    buttonTable.add(changePassword).padTop(15).center().row();
                    changePassword.setFontScale(0.8f);
                    buttonTable.add(newPasswordField).width(180).height(30).padTop(10).center().row();
                    forgetPasswordButton.setText("confirm");
                    first = false;
                }
                if (!first) {
                    String newPassword = newPasswordField.getText();
                    if (newPassword.isEmpty()) {
                        errorLabel.setText("Please fill all the fields");
                        return;
                    }
                    SignUpController signUpController = new SignUpController();
                    String error = signUpController.checkPasswordStrength(newPassword);
                    if (error != null) {
                        errorLabel.setText(error);
                        return;
                    }
                    currentUser.setPassword(newPassword);
                    errorLabel.setText("password changed successfully");
                    errorLabel.setColor(Color.GREEN);
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
    }


    @Override
    public void resize(int width, int height) {

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
    }
}
