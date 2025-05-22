package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Point;

public class Elder {
    private Vector2 position;
    private int Hp = 400;
    private float normalSpeed = 20f;
    private float dashSpeedMultiplier = 260f;
    private float dashTime = 5f;
    private Animation<TextureRegion> walkAnimation, explosionAnimation;
    private float stateTime, stateTime2;
    private Image monsterImage, explosionImage;
    private Rectangle box;
    private boolean isDead = false, isExploding = false;
    private float dashTimer = 0;
    private boolean isDashing = false;
    private Vector2 savedPosition = new Vector2();

    private String[] images = new String[]{
            "enemies/elder/1.png",
    };

    private String[] explosionImages = new String[]{
            "enemies/explosion/1.png",
            "enemies/explosion/2.png",
            "enemies/explosion/3.png",
            "enemies/explosion/4.png"
    };

    public Elder(int startX, int startY) {
        this.position = new Vector2(startX, startY);
        initializeAnimation();
        initializeExplosion();
        monsterImage.setPosition(position.x, position.y);
        box = new Rectangle(position.x, position.y, 55, 55);
    }

    private void initializeAnimation() {
        TextureRegion[] frames = new TextureRegion[images.length];
        for (int i = 0; i < images.length; i++) {
            frames[i] = new TextureRegion(new Texture(images[i]));
        }
        walkAnimation = new Animation<>(0.1f, frames);
        stateTime = 0f;
        monsterImage = new Image(new TextureRegionDrawable(frames[0]));
        monsterImage.setSize(55, 55);
    }

    private void initializeExplosion() {
        TextureRegion[] frames = new TextureRegion[explosionImages.length];
        for (int i = 0; i < explosionImages.length; i++) {
            frames[i] = new TextureRegion(new Texture(explosionImages[i]));
        }
        explosionAnimation = new Animation<>(0.1f, frames);
        stateTime2 = 0f;
        explosionImage = new Image(new TextureRegionDrawable(frames[0]));
        explosionImage.setSize(55, 55);
    }

    public void update(float delta) {
        if (isExploding) {
            stateTime2 += delta;
            explosionImage.setDrawable(new TextureRegionDrawable(explosionAnimation.getKeyFrame(stateTime2, false)));

            if (explosionAnimation.isAnimationFinished(stateTime2)) {
                explosionImage.remove();
                this.dispose();
            }
            return;
        }

        dashTimer += delta;

        if (!isDashing && dashTimer >= dashTime) {
            isDashing = true;
            savedPosition = new Vector2(GameManager.getNewGame().getPlayer().getPosition().x + 5
                    , GameManager.getNewGame().getPlayer().getPosition().y + 5);
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


        if (Hp <= 0 && !isExploding) {
            isExploding = true;
            GameManager.getNewGame().getPlayer().setKillCount();
            monsterImage.remove();
            explosionImage.setPosition(position.x, position.y);
            Point point = new Point(position.x, position.y);
            GameManager.getNewGame().getGameStage().addActor(point.getImageBox());
            GameManager.getNewGame().getPoints().add(point);
            GameManager.getNewGame().getGameStage().addActor(explosionImage);
            isDead = true;
        } else {
            monsterImage.setPosition(position.x, position.y);
            monsterImage.setDrawable(new TextureRegionDrawable(walkAnimation.getKeyFrame(stateTime, true)));
        }
    }

    public Image getMonsterImage() {
        return monsterImage;
    }

    public void dispose() {
        for (String image : images) {
            new Texture(image).dispose();
        }
    }

    public Rectangle getBox() {
        return box;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int damage) {
        Hp -= damage;
        if (Hp <= 0) {
            Hp = 0;
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
