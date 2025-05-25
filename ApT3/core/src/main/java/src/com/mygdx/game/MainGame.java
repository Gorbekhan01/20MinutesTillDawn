package src.com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Menu;

import static src.com.mygdx.game.Models.GameManager.getScreen;
import static src.com.mygdx.game.Models.GameManager.setScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private SpriteBatch batch;
    private Texture image;

    public static Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        GameManager.setGame(this);
        setScreen(Menu.OPENING_SCREEN.getScreen());
        playMusic(1);

    }

    public static void playMusic(int number) {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/"+number+".mp3"));
        GameManager.setMusic(backgroundMusic);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0f);
        backgroundMusic.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}
