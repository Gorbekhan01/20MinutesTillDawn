package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.GameManager;

public class TentacleMonster extends Enemy {
    public TentacleMonster(int startX, int startY) {
        super(startX, startY,
            25,
            16f,
            new String[]{
                "enemies/TentacleMonster/1.png",
                "enemies/TentacleMonster/2.png",
                "enemies/TentacleMonster/3.png"
            },
            new String[]{
                "enemies/explosion/1.png",
                "enemies/explosion/2.png",
                "enemies/explosion/3.png",
                "enemies/explosion/4.png"
            });

        initializeAnimation();
        initializeExplosion();
        monsterImage.setSize(20, 20);
        explosionImage.setSize(20, 20);
        float centerX = position.x + monsterImage.getWidth() / 2;
        float centerY = position.y + monsterImage.getHeight() / 2;
        box = new Rectangle(centerX - 10, centerY - 10, 20, 20);
    }

    @Override
    protected void updateMovement(float delta) {
        stateTime += delta;
        box.setPosition(position.x, position.y);
        Vector2 playerPosition = GameManager.getNewGame().getPlayer().getPosition();
        Vector2 direction = new Vector2(playerPosition).sub(position).nor();
        position.add(direction.scl(speed * delta));

        if (direction.x < 0) {
            monsterImage.setScale(-1, 1);
        } else {
            monsterImage.setScale(1, 1);
        }
        monsterImage.setPosition(position.x, position.y);
        monsterImage.setDrawable(new TextureRegionDrawable(walkAnimation.getKeyFrame(stateTime, true)));
    }
}
