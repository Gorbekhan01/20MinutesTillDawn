package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bullet extends Actor {
    private Image bulletImage;
    private Vector2 velocity;
    private Stage stage = GameManager.getNewGame().getGameStage();

    public Bullet(Vector2 position, Vector2 direction) {
        Texture bulletTexture = new Texture("weapons/Ammo.png");
        bulletImage = new Image(bulletTexture);
        bulletImage.setSize(4,4);
        bulletImage.setPosition(position.x, position.y);
        this.velocity = direction.nor().scl(500);
        stage.addActor(bulletImage);
    }

    @Override
    public void act(float delta) {
        bulletImage.moveBy(velocity.x * delta, velocity.y * delta);

        if (bulletImage.getX() > Gdx.graphics.getWidth() || bulletImage.getX() < 0 ||
            bulletImage.getY() > Gdx.graphics.getHeight() || bulletImage.getY() < 0) {
            bulletImage.remove();
        }
    }
}
