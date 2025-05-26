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
    private TextButton loginButton, forgetPasswordButton , newPasswordConfirmButton , backButton;
    private Stage stage;
    private Table mainTable ;
    private Label errorLabel;
    private Skin skin = GameManager.getSkin();
    private LoginMenuController controller;
    private boolean first = true;
    Table buttonTable;
    private User currentUser = null;
    TextField.TextFieldStyle textFieldStyle;
    private boolean validPassword = false;
    private boolean validUsername = false;
    private Label securityQuestionLabel;
    private TextField answerField;
    private boolean waitingForSecurityAnswer = false;
    private boolean waitingForNewPassword = false;


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        controller = new LoginMenuController();
        createLoginScreen();
    }

    private void createLoginScreen() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.defaults().pad(10);
        textFieldStyle = new TextField.TextFieldStyle(skin.get(TextField.TextFieldStyle.class));
        textFieldStyle.font = GameManager.getFont(2);


        Label titleLabel = new Label(">> Login Menu <<", skin);
        usernameField = new TextField("", skin);
        usernameField.setStyle(textFieldStyle);
        passwordField = new TextField("", skin);
        passwordField.setStyle(textFieldStyle);
        mainTable.add(titleLabel).colspan(2).padBottom(30).row();
        mainTable.add(new Label("Username", skin)).right();
        mainTable.add(usernameField).width(180).height(30).row();
        mainTable.add(new Label("Password", skin)).right();
        mainTable.add(passwordField).width(180).height(30).row();
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        stage.addActor(mainTable);

        buttonTable = new Table();
        loginButton = new TextButton("Login", skin);
        backButton = new TextButton("Back", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);
        buttonTable.add(loginButton).width(180).height(70).row();
        buttonTable.add(forgetPasswordButton).width(360).height(70).row();
        buttonTable.add(backButton).width(180).height(70).row();
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

                validUsername = false;
                validPassword = false;
                currentUser = null;

                if (!GameManager.getUsers().isEmpty()) {
                    for (User user : GameManager.getUsers()) {
                        if (user.getUsername().equals(username)) {
                            currentUser = user;
                            validUsername = true;
                            break;
                        }
                    }
                }

                if (!validUsername || currentUser == null) {
                    errorLabel.setText("User not found");
                    return;
                }

                if (!controller.checkPassword(currentUser, password)) {
                    errorLabel.setText("Wrong Password");
                    return;
                } else {
                    validPassword = true;
                }

                if (validUsername && validPassword) {
                    stage.clear();
                    GameManager.setCurrentUser(currentUser);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
                }
            }
        });

        forgetPasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                errorLabel.setColor(Color.RED);

                if (!waitingForSecurityAnswer && !waitingForNewPassword) {
                    String username = usernameField.getText().trim();
                    errorLabel.setText("");

                    if (username.isEmpty()) {
                        errorLabel.setText("Please first enter username");
                        return;
                    }

                    currentUser = null;
                    for (User user : GameManager.getUsers()) {
                        if (user.getUsername().equals(username)) {
                            currentUser = user;
                            break;
                        }
                    }

                    if (currentUser == null) {
                        errorLabel.setText("User not found");
                        return;
                    }

                    securityQuestionLabel = new Label(currentUser.getSecurityQNumber(), skin);
                    securityQuestionLabel.setFontScale(0.85f);
                    answerField = new TextField("", skin);
                    answerField.setStyle(textFieldStyle);

                    buttonTable.add(securityQuestionLabel).padTop(15).center().row();
                    buttonTable.add(answerField).width(180).height(30).padTop(10).center().row();

                    forgetPasswordButton.setText("Check Answer");
                    waitingForSecurityAnswer = true;
                    errorLabel.setText("");
                    return;
                }

                if (waitingForSecurityAnswer && !waitingForNewPassword) {
                    String answer = answerField.getText().trim();

                    if (answer.isEmpty()) {
                        errorLabel.setText("Please answer the question");
                        return;
                    }

                    if (!currentUser.getSecurityAnswer().equals(answer)) {
                        errorLabel.setText("Wrong answer");
                        return;
                    }

                    errorLabel.setColor(Color.GREEN);
                    errorLabel.setText("Correct! Now enter a new password.");

                    newPasswordField = new TextField("", skin);
                    newPasswordField.setStyle(textFieldStyle);
                    buttonTable.add(new Label("New password:", skin)).padTop(10).center().row();
                    buttonTable.add(newPasswordField).width(180).height(30).padTop(10).center().row();

                    forgetPasswordButton.setText("Change Password");
                    waitingForNewPassword = true;
                    waitingForSecurityAnswer = false;
                    return;
                }

                if (waitingForNewPassword) {
                    String newPassword = newPasswordField.getText();
                    if (newPassword.isEmpty()) {
                        errorLabel.setText("Please enter new password");
                        return;
                    }

                     SignUpController signUpController = new SignUpController();
                     String error = signUpController.checkPasswordStrength(newPassword);
                     if (error != null) {
                         errorLabel.setColor(Color.RED);
                         errorLabel.setText(error);
                         return;
                     }

                    currentUser.setPassword(newPassword);

                    errorLabel.setColor(Color.GREEN);
                    errorLabel.setText("Password changed successfully!");

                    forgetPasswordButton.setText("Forget Password");
                    waitingForSecurityAnswer = false;
                    waitingForNewPassword = false;
                    currentUser = null;
                    return;
                }
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.OPENING_SCREEN.getScreen());
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
        stage.dispose();
    }
}
