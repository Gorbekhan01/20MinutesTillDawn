package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Intersector;
import src.com.mygdx.game.Models.Enemies.Elder;
import src.com.mygdx.game.Models.Enemies.EyeBat;
import src.com.mygdx.game.Models.Enemies.TentacleMonster;

public class Bullet extends Actor {
    private Image bulletImage;
    private Vector2 velocity;
    private Stage stage = GameManager.getNewGame().getGameStage();
    private Rectangle box;
    private boolean isEyebat = false;
    private Texture bulletTexture = null;

    public Bullet(Vector2 position, Vector2 direction , boolean isEyeBat) {
        if (isEyeBat) {
            bulletTexture= new Texture("enemies/eyebat/5.png");
        } else {
            bulletTexture= new Texture("weapons/Ammo.png");
        }
        bulletImage = new Image(bulletTexture);
        if (isEyeBat) {
            box = new Rectangle(position.x, position.y, 12, 12);
        } else {
            box = new Rectangle(position.x, position.y, 8, 8);
        }
        bulletImage.setSize(4, 4);
        bulletImage.setPosition(position.x, position.y);
        if (isEyeBat) {
            this.velocity = direction.nor().scl(60);
        } else {
            this.velocity = direction.nor().scl(250);
        }
        stage.addActor(bulletImage);
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

        boolean collision = false;
        if (!isEyebat) {
            for (EyeBat eyeBat : GameManager.getNewGame().getEyeBat()) {
                if (eyeBat.getBox().overlaps(box)) {
                    eyeBat.setHp(GameManager.getNewGame().getPlayer().getWeapons().getDamage());
                    bulletImage.remove();
                    collision = true;
                    break;
                }
            }

            if (!collision) {
                for (TentacleMonster tentacleMonster : GameManager.getNewGame().getTentacleMonsters()) {
                    if (tentacleMonster.getBox().overlaps(box)) {
                        tentacleMonster.setHp(GameManager.getNewGame().getPlayer().getWeapons().getDamage());
                        bulletImage.remove();
                        break;
                    }
                }
            }

            if (!collision) {
                for (Elder elder : GameManager.getNewGame().getElder()) {
                    if (elder.getBox().overlaps(box)) {
                        elder.setHp(GameManager.getNewGame().getPlayer().getWeapons().getDamage());
                        bulletImage.remove();
                        break;
                    }
                }
            }


        }

        if (bulletImage.getX() > Gdx.graphics.getWidth() || bulletImage.getX() < 0 ||
            bulletImage.getY() > Gdx.graphics.getHeight() || bulletImage.getY() < 0) {
            bulletImage.remove();
        }
    }

}
