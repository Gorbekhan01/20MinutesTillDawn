package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.GameManager;

public class Elder extends Enemy {
    private float normalSpeed = 20f;
    private float dashSpeedMultiplier = 260f;
    private float dashTime = 5f;
    private float dashTimer = 0;
    private boolean isDashing = false;
    private Vector2 savedPosition = new Vector2();

    public Elder(int startX, int startY) {
        super(startX, startY,
            4000,
            20f,
            new String[]{"enemies/elder/1.png"},
            new String[]{
                "enemies/explosion/elderExplosion/1.png",
                "enemies/explosion/elderExplosion/2.png",
                "enemies/explosion/elderExplosion/3.png",
                "enemies/explosion/elderExplosion/4.png",
                "enemies/explosion/elderExplosion/5.png",
                "enemies/explosion/elderExplosion/6.png"
            });

        initializeAnimation();
        initializeExplosion();
        monsterImage.setSize(55, 55);
        explosionImage.setSize(55, 55);
        float centerX = position.x + monsterImage.getWidth() / 2;
        float centerY = position.y + monsterImage.getHeight() / 2;
        box = new Rectangle(centerX - 27, centerY - 27, 55, 55);
    }

    @Override
    protected void updateMovement(float delta) {
        dashTimer += delta;

        if (!isDashing && dashTimer >= dashTime) {
            isDashing = true;
            savedPosition = new Vector2(GameManager.getNewGame().getPlayer().getPosition().x + 5,
                GameManager.getNewGame().getPlayer().getPosition().y + 5);
            dashTimer = 0;
        }

        box.setPosition(position.x, position.y);
        Vector2 direction = new Vector2(savedPosition).sub(position).nor();

        if (isDashing) {
            position.add(direction.scl(dashSpeedMultiplier * delta));
            if (position.dst(savedPosition) < 3f) {
                isDashing = false;
                dashTimer = 0;
            }
        } else {
            Vector2 normalDirection = new Vector2(GameManager.getNewGame().getPlayer().getPosition()).sub(position).nor();
            position.add(normalDirection.scl(normalSpeed * delta));
        }

        monsterImage.setPosition(position.x, position.y);
        monsterImage.setDrawable(new TextureRegionDrawable(walkAnimation.getKeyFrame(stateTime, true)));
    }
}
