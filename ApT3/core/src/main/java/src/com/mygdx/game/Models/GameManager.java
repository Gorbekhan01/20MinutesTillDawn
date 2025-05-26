package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import src.com.mygdx.game.MainGame;
import src.com.mygdx.game.Views.MainMenu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static MainGame game;
    private static final Skin skin = new Skin(Gdx.files.internal("Skin/star-soldier-ui.json"));
    private static Screen screen;
    private static User currentUser = null;
    private static Menu currentMenu = Menu.OPENING_SCREEN;
    private static final ArrayList<User> users = new ArrayList<>();
    private static NewGame newGame;
    private static int rightKey = 32;
    private static int leftKey = 29;
    private static int upKey = 51;
    private static int downKey = 47;
    private static int shootKey = 62;
    private static int reloadKey = 46;
    private static boolean leftShoot = false;

    private static ArrayList<Pair<String, Integer>> keys = new ArrayList<>(
        List.of(
            new Pair<>("a", (int) 'a'), new Pair<>("b", (int) 'b'), new Pair<>("c", (int) 'c'),
            new Pair<>("d", (int) 'd'), new Pair<>("e", (int) 'e'), new Pair<>("f", (int) 'f'),
            new Pair<>("g", (int) 'g'), new Pair<>("h", (int) 'h'), new Pair<>("i", (int) 'i'),
            new Pair<>("j", (int) 'j'), new Pair<>("k", (int) 'k'), new Pair<>("l", (int) 'l'),
            new Pair<>("m", (int) 'm'), new Pair<>("n", (int) 'n'), new Pair<>("o", (int) 'o'),
            new Pair<>("p", (int) 'p'), new Pair<>("q", (int) 'q'), new Pair<>("r", (int) 'r'),
            new Pair<>("s", (int) 's'), new Pair<>("t", (int) 't'), new Pair<>("u", (int) 'u'),
            new Pair<>("v", (int) 'v'), new Pair<>("w", (int) 'w'), new Pair<>("x", (int) 'x'),
            new Pair<>("y", (int) 'y'), new Pair<>("z", (int) 'z')
        )
    );


    public static void setGame(MainGame mainGame) {
        game = mainGame;
    }

    private static Music currentMusic;
    private static boolean SFX = false;

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

    public static Sound sound;

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
        List.of("heroes/Dasher/Walk_0.png", "heroes/Diamond/Walk_0.png", "heroes/Lilith/Walk_0.png", "heroes/Scarlet/Walk_0.png"
            , "heroes/Shana/Walk_0.png")
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
        GameManager.SFX = SFX;
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
        GameManager.newGame = newGame;
    }

    public static NewGame getNewGame() {
        return newGame;
    }

    public static ArrayList<Pair<String, Integer>> getKeys() {
        return keys;
    }

    public static void playSound(String address) {
        sound = Gdx.audio.newSound(Gdx.files.internal(address));
        if (SFX) {
            sound.play();
        }
    }

    public static void loadUsers() throws FileNotFoundException {
        users.clear();
        Gson gson = new Gson();
        Type playerListType = new TypeToken<List<User>>(){}.getType();

        try (FileReader reader = new FileReader("users.json")) {
            ArrayList<User> loaded = gson.fromJson(reader, playerListType);
            users.addAll(loaded);
        } catch (Exception e) {
            System.out.println("Error loading users.json");
        }
    }

    public static void saveUsers() throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("users.json")) {
            gson.toJson(users, writer);
        }
    }

    public static int getRightKey() {
        return rightKey;
    }

    public static void setRightKey(int rightKey) {
        GameManager.rightKey = rightKey;
    }

    public static int getLeftKey() {
        return leftKey;
    }

    public static void setLeftKey(int leftKey) {
        GameManager.leftKey = leftKey;
    }

    public static int getUpKey() {
        return upKey;
    }

    public static void setUpKey(int upKey) {
        GameManager.upKey = upKey;
    }

    public static int getDownKey() {
        return downKey;
    }

    public static void setDownKey(int downKey) {
        GameManager.downKey = downKey;
    }

    public static int getShootKey() {
        return shootKey;
    }

    public static void setShootKey(int shootKey) {
        GameManager.shootKey = shootKey;
    }

    public static int getReloadKey() {
        return reloadKey;
    }

    public static void setReloadKey(int reloadKey) {
        GameManager.reloadKey = reloadKey;
    }

    public static boolean isLeftShoot() {
        return leftShoot;
    }

    public static void setLeftShoot(boolean leftShoot) {
        GameManager.leftShoot = leftShoot;
    }
}
