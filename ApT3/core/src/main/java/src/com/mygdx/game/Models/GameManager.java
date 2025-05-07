package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import src.com.mygdx.game.MainGame;
import src.com.mygdx.game.Views.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static MainGame game;
    private static final Skin skin = new Skin(Gdx.files.internal("Skin/star-soldier-ui.json"));;
    private static Screen screen;
    private static User currentUser = null;
    private static Menu currentMenu = Menu.OPENING_SCREEN;
    private static final ArrayList<User> users = new ArrayList<>();
    public static void setGame(MainGame mainGame) {
        game = mainGame;
    }
    private static Music currentMusic;

    public static MainGame getGame() {
        return game;
    }

    public static Skin getSkin() {
        return skin;
    }

    public static void setScreen(Menu menu) {
        currentMenu = menu;
    }

    public static Screen getScreen() {
        return currentMenu.getScreen();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    private static final String[] securityQs = {
        "What is your favorite color?",
        "Where do you live?",
        "Who is your best friend?"
    };

    public static String[] getSecurityQs() {
        return securityQs;
    }

    private static final ArrayList<String> avatars = new ArrayList<>(
        List.of("avatars/1.png", "avatars/2.png", "avatars/3.png", "avatars/4.png")
    );

    public static ArrayList<String> getAvatars() {
        return avatars;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUserTemp) {
        currentUser = currentUserTemp;
    }

    public static void setMusicVolume(float volume) {
        currentMusic.setVolume(volume);
    }

    public static void setMusic(Music music) {
        currentMusic = music;
    }

    public static void changeMusic(int number) {
        MainGame.playMusic(number);
    }

}
