package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import src.com.mygdx.game.Models.Enemies.Enemy;

public class Bullet extends Actor {
    private Image bulletImage;
    private Vector2 velocity;
    private Stage bulletStage = GameManager.getNewGame().getGameStage();
    private Rectangle box;
    private boolean isEyebat = false;
    private Texture bulletTexture = null;

    public Bullet(Vector2 position, Vector2 direction, boolean isEyeBat) {
        if (isEyeBat) {
            bulletTexture = new Texture("enemies/eyebat/5.png");
        } else {
            bulletTexture = new Texture("weapons/Ammo.png");
        }
        bulletImage = new Image(bulletTexture);
        if (isEyeBat) {
            float centerX = position.x + bulletImage.getWidth() / 2;
            float centerY = position.y + bulletImage.getHeight() / 2;
            box = new Rectangle(centerX - 6, centerY - 6, 12, 12);
        } else {
            float centerX = position.x + bulletImage.getWidth() / 2;
            float centerY = position.y + bulletImage.getHeight() / 2;
            box = new Rectangle(centerX - 4, centerY - 4, 8, 8);
        }
        bulletImage.setSize(4, 4);
        bulletImage.setPosition(position.x, position.y);
        if (isEyeBat) {
            this.velocity = direction.nor().scl(60);
        } else {
            this.velocity = direction.nor().scl(250);
        }
        bulletStage.addActor(bulletImage);
        this.isEyebat = isEyeBat;
    }


    @Override
    public void act(float delta) {
        bulletImage.moveBy(velocity.x * delta, velocity.y * delta);
        box.setPosition(bulletImage.getX(), bulletImage.getY());

        if (!GameManager.getNewGame().getPlayer().isDamaged()) {
            if (GameManager.getNewGame().getPlayer().getBox().overlaps(box) && isEyebat) {
                GameManager.getNewGame().getPlayer().setHP();
                GameManager.getNewGame().getPlayer().setDamaged(true);
                GameManager.getNewGame().getPlayer().setInvincibleTimer(2f);
                bulletImage.remove();
            }
        }

        if (!isEyebat) {
            for (Enemy enemy : GameManager.getNewGame().getEnemies()) {
                if (enemy.getBox().overlaps(box) && !enemy.isDead()) {
                    enemy.setHp(GameManager.getNewGame().getPlayer().getWeapons().getDamage());
                    bulletImage.remove();
                    return;
                }
            }

        }

        if (bulletImage.getX() > Gdx.graphics.getWidth() || bulletImage.getX() < 0 ||
            bulletImage.getY() > Gdx.graphics.getHeight() || bulletImage.getY() < 0) {
            bulletImage.remove();
        }
    }

    public Image getBulletImage() {
        return bulletImage;
    }

}
