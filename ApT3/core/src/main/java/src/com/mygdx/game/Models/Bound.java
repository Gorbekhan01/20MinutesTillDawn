package src.com.mygdx.game.Models;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bound extends Actor {
    private Texture[][] textures;
    private Image[] images;
    private final Stage stage;
    private float elapsedTime = 0;
    private int frameIndex = 0;
    private float frameDuration = 0.1f;

    // تعریف محدوده‌ی حصار
    private float minX, maxX, minY, maxY;

    public Bound(Stage stage) {
        this.stage = stage;
        loadTextures();
        placeFence();
        defineBounds();
    }

    private void loadTextures() {
        textures = new Texture[][]{
            {new Texture("fence/1.png"), new Texture("fence/2.png"), new Texture("fence/3.png"), new Texture("fence/4.png")},
            {new Texture("fence/1.png"), new Texture("fence/2.png"), new Texture("fence/3.png"), new Texture("fence/4.png")},
            {new Texture("fence/1.png"), new Texture("fence/2.png"), new Texture("fence/3.png"), new Texture("fence/4.png")},
            {new Texture("fence/1.png"), new Texture("fence/2.png"), new Texture("fence/3.png"), new Texture("fence/4.png")}
        };

        images = new Image[4];
        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(textures[i][0]);
        }
    }

    private void placeFence() {
        float worldWidth = stage.getWidth();
        float worldHeight = stage.getHeight();
        float fenceThickness = 20;

        images[2].setBounds(100, 100, fenceThickness, worldHeight - 200); // چپ
        images[3].setBounds(worldWidth - fenceThickness - 100, 100, fenceThickness, worldHeight - 200); // راست

        images[0].setRotation(90);
        images[1].setRotation(90);

        images[0].setBounds(worldWidth - fenceThickness - 100, worldHeight - fenceThickness - 100, fenceThickness, worldWidth - 200);
        images[1].setBounds(worldWidth - fenceThickness - 100, 100, fenceThickness, worldWidth - 200);

        for (Image img : images) {
            stage.addActor(img);
        }
    }


    private void defineBounds() {
        minX = 100;
        maxX = stage.getWidth() - 100;
        minY = 100;
        maxY = stage.getHeight() - 100;
    }

    public boolean isPlayerInside(Player player) {
        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;
        float playerWidth = player.getPlayerImage().getWidth();
        float playerHeight = player.getPlayerImage().getHeight();

        return (playerX < minX || playerX + playerWidth > maxX ||
            playerY < minY || playerY + playerHeight > maxY);
    }


    @Override
    public void act(float delta) {

        if (GameManager.getNewGame().isElderAlive()) {
            elapsedTime += delta;
            if (elapsedTime > frameDuration) {
                elapsedTime = 0;
                frameIndex = (frameIndex + 1) % 4;

                for (int i = 0; i < images.length; i++) {
                    if (textures[i] != null) {
                        images[i].setDrawable(new Image(textures[i][frameIndex]).getDrawable());
                    }
                }
            }
        } else {
            for (int i = 0; i < images.length; i++) {
                if (textures[i] != null) {
                    images[i].remove();
                }
            }
        }
    }
}
