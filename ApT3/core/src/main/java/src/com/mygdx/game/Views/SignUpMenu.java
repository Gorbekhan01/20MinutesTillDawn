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
import src.com.mygdx.game.Controllers.SignUpController;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;
import src.com.mygdx.game.Models.User;

import java.io.IOException;
import java.util.Random;

public class SignUpMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private SignUpController signUpController;
    private TextField usernameField, passwordField, confirmPasswordField;
    private SelectBox<String> securityQuestionSelect;
    private TextField securityAnswerField;
    private Label errorLabel;
    private TextButton signUpButton, backButton, guestButton;
    private Image avatarImage;
    private TextButton changeAvatarButton;
    private Table mainTable = new Table();
    private User user = new User(null,null);;

    @Override
    public void show() {
        signUpController = new SignUpController();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = GameManager.getSkin();

        createSignUpForm();
    }

    private void createSignUpForm() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.defaults().pad(10);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(skin.get(TextField.TextFieldStyle.class));
        textFieldStyle.font = GameManager.getFont(2);

        // Title
        Label titleLabel = new Label(">> SignUp Menu <<", skin);
        mainTable.add(titleLabel).colspan(2).padBottom(30).row();

        // Avatar Section
        avatarImage = new Image();
        changeAvatarButton = new TextButton("Change Avatar", skin);

        Table avatarTable = new Table();
        avatarTable.add(avatarImage).size(80, 80).row();
        avatarTable.add(changeAvatarButton).padTop(5);
        mainTable.add(avatarTable).colspan(2).row();

        // Form Fields
        usernameField = new TextField("", skin);
        usernameField.setStyle(textFieldStyle);
        passwordField = new TextField("", skin);
        passwordField.setStyle(textFieldStyle);
//        passwordField.setPasswordMode(true);
//        passwordField.setPasswordCharacter('*');
        confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        securityQuestionSelect = new SelectBox<>(skin);
        securityQuestionSelect.setItems(GameManager.getSecurityQs());
        securityAnswerField = new TextField("", skin);
        securityAnswerField.setStyle(textFieldStyle);

        mainTable.add(new Label("Username", skin)).right();
        mainTable.add(usernameField).width(180).height(30).row();
        mainTable.add(new Label("Password", skin)).right();
        mainTable.add(passwordField).width(180).height(30).row();
        mainTable.add(new Label("SecurityQuestion", skin)).right();
        mainTable.add(securityQuestionSelect).width(240).height(30).row();
        mainTable.add(new Label("Answer:", skin)).right();
        mainTable.add(securityAnswerField).width(180).height(30).row();

        signUpButton = new TextButton("SignUp!", skin);
        guestButton = new TextButton("Continue as Guest", skin);
        backButton = new TextButton("Back", skin);

        Table buttonTable = new Table();
        //default for all
//        buttonTable.defaults().width(180).height(60).pad(0);
        buttonTable.add(signUpButton).width(180).height(70).row();
        buttonTable.add(guestButton).width(360).height(70).row();
        buttonTable.add(backButton).width(180).height(70).row();
        mainTable.add(buttonTable).colspan(2).padTop(20).row();
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        errorLabel.setSize(400, 30);
        errorLabel.setFontScale(0.8f);
        mainTable.add(errorLabel).colspan(2).padTop(10);

        stage.addActor(mainTable);
        updateAvatar();
        addListeners();
    }

    private void addListeners() {
        signUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String securityQuestion = securityQuestionSelect.getSelected();
                String securityAnswer = securityAnswerField.getText();
                errorLabel.setText("");

                if (username.isEmpty() || password.isEmpty() ||
                    securityAnswer.isEmpty()) {
                    errorLabel.setText("Please fill all the fields");
                    return;
                }

                String usernameError = signUpController.isUsernameTaken(username);
                if (usernameError != null) {
                    errorLabel.setText(usernameError);
                    return;
                }

                String passwordError = signUpController.checkPasswordStrength(password);
                if (passwordError != null) {
                    errorLabel.setText(passwordError);
                    return;
                }
                user.setUsername(username);
                user.setPassword(password);
                user.setSecurityQNumber(securityQuestion);
                user.setSecurityAnswer(securityAnswer);
                try {
                    GameManager.saveUsers();
                } catch (Exception e) {
                    System.out.println("Error saving users");
                }

                GameManager.getUsers().add(user);
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.LOGIN_MENU.getScreen());
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.OPENING_SCREEN.getScreen());
            }
        });

        guestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                user.setUsername("Guest");
                user.setAvatarNumber(1);
                GameManager.setCurrentUser(user);
                ((Game) Gdx.app.getApplicationListener()).setScreen(Menu.MAIN_MENU.getScreen());
            }
        });

        changeAvatarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateAvatar();
            }
        });

    }

    private void updateAvatar() {
        Random random = new Random();
        int randomInt = random.nextInt(11);
        Texture avatarTexture = new Texture(Gdx.files.internal(GameManager.getAvatars().get(randomInt)));
        avatarImage.setDrawable(new Image(avatarTexture).getDrawable());
        user.setAvatarNumber(randomInt);
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
