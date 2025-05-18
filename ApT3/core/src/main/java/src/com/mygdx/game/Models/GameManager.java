package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import src.com.mygdx.game.MainGame;
import src.com.mygdx.game.Views.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static MainGame game;
    private static final Skin skin = new Skin(Gdx.files.internal("Skin/star-soldier-ui.json"));
    ;
    private static Screen screen;
    private static User currentUser = null;
    private static Menu currentMenu = Menu.OPENING_SCREEN;
    private static final ArrayList<User> users = new ArrayList<>();
    private static NewGame newGame;

    public static void setGame(MainGame mainGame) {
        game = mainGame;
    }

    private static Music currentMusic;
    private static boolean SFX = true;

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
        List.of("avatars/1.png", "avatars/2.png", "avatars/3.png", "avatars/4.png"
            , "avatars/5.png", "avatars/6.png", "avatars/7.png", "avatars/8.png",
            "avatars/9.png", "avatars/10.png", "avatars/11.png")
    );

    private static final ArrayList<String> heroes = new ArrayList<>(
        List.of("heroes/Dasher.png","heroes/Diamond.png","heroes/Lilith.png","heroes/Scarlet.png","heroes/Shana.png")
    );

    private static final ArrayList<String> weapons = new ArrayList<>(
        List.of("weapons/DualSMGs.png","weapons/Revolver.png","weapons/Shotgun.png")
    );

    public static ArrayList<String> getAvatars() {
        return avatars;
    }

    public static ArrayList<String> getHeroes() {
        return heroes;
    }

    public static ArrayList<String> getWeapons() {
        return weapons;
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

    public static boolean isSFX() {
        return SFX;
    }

    public static void setSFX(boolean SFX) {
        SFX = SFX;
    }

    public static BitmapFont getFont(int number) {
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        BitmapFont customFont;
        switch (number) {
            case 1:
                generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChevyRay - Express.ttf"));
                parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 20;
                customFont = generator.generateFont(parameter);
                generator.dispose();
                return customFont;
            case 2:
                generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/NotoSans-Regular.ttf"));
                parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 20;
                customFont = generator.generateFont(parameter);
                generator.dispose();
                return customFont;

        }
        return null;
    }

    public static void setNewGame(NewGame newGame) {
        newGame = newGame;
    }

    public static NewGame getNewGame() {
        return newGame;
    }


}
