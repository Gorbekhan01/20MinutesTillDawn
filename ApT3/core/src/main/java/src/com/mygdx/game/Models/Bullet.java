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
import src.com.mygdx.game.Models.Enemies.TentacleMonster;

public class Bullet extends Actor {
    private Image bulletImage;
    private Vector2 velocity;
    private Stage stage = GameManager.getNewGame().getGameStage();
    private Rectangle box;

    public Bullet(Vector2 position, Vector2 direction) {
        Texture bulletTexture = new Texture("weapons/Ammo.png");
        bulletImage = new Image(bulletTexture);
        bulletImage.setSize(4, 4);
        box = new Rectangle(position.x, position.y, 4, 4);
        bulletImage.setPosition(position.x, position.y);
        this.velocity = direction.nor().scl(500);
        stage.addActor(bulletImage);
    }


    @Override
    public void act(float delta) {
        bulletImage.moveBy(velocity.x * delta, velocity.y * delta);
        box.setPosition(bulletImage.getX(), bulletImage.getY());

        for (TentacleMonster monster : GameManager.getNewGame().getTentacleMonsters()) {
            if (Intersector.overlaps(box, monster.getBox())) {
                monster.setHp(GameManager.getNewGame().getPlayer().getWeapons().getDamage());
                bulletImage.remove();
                break;
            }
        }


        if (bulletImage.getX() > Gdx.graphics.getWidth() || bulletImage.getX() < 0 ||
            bulletImage.getY() > Gdx.graphics.getHeight() || bulletImage.getY() < 0) {
            bulletImage.remove();
        }
    }

}
