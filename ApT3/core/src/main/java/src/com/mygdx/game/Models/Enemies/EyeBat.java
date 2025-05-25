package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.Bullet;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Point;

public class EyeBat {
    private Vector2 position;
    private int Hp = 50;
    private float speed = 20f;
    private Animation<TextureRegion> walkAnimation, explosionAnimation;
    private float stateTime, stateTime2;
    private Image monsterImage, explosionImage;
    private Rectangle box;
    private boolean isDead = false, isExploding = false;
    private float shootTimer = 0f;
    private final float shootInterval = 3f;

    private String[] images = new String[]{
        "enemies/eyebat/1.png",
        "enemies/eyebat/2.png",
        "enemies/eyebat/3.png"
    };

    private String[] explosionImages = new String[]{
        "enemies/explosion/1.png",
        "enemies/explosion/2.png",
        "enemies/explosion/3.png",
        "enemies/explosion/4.png"
    };

    public EyeBat(int startX, int startY) {
        this.position = new Vector2(startX, startY);
        initializeAnimation();
        initializeExplosion();
        monsterImage.setPosition(position.x, position.y);
        float centerX = position.x + monsterImage.getWidth() / 2;
        float centerY = position.y + monsterImage.getHeight() / 2;
        box = new Rectangle(centerX - 10, centerY - 10, 20, 20);
    }

    private void initializeAnimation() {
        TextureRegion[] frames = new TextureRegion[images.length];
        for (int i = 0; i < images.length; i++) {
            frames[i] = new TextureRegion(new Texture(images[i]));
        }
        walkAnimation = new Animation<>(0.1f, frames);
        stateTime = 0f;
        monsterImage = new Image(new TextureRegionDrawable(frames[0]));
        monsterImage.setSize(20, 20);
    }

    private void initializeExplosion() {
        TextureRegion[] frames = new TextureRegion[explosionImages.length];
        for (int i = 0; i < explosionImages.length; i++) {
            frames[i] = new TextureRegion(new Texture(explosionImages[i]));
        }
        explosionAnimation = new Animation<>(0.1f, frames);
        stateTime2 = 0f;
        explosionImage = new Image(new TextureRegionDrawable(frames[0]));
        explosionImage.setSize(20, 20);
    }

    public void update(float delta) {
        if (isExploding) {
            stateTime2 += delta;
            explosionImage.setDrawable(new TextureRegionDrawable(explosionAnimation.getKeyFrame(stateTime2, false)));

            if (explosionAnimation.isAnimationFinished(stateTime2)) {
                explosionImage.remove();
                isDead = true;
                this.dispose();
            }
            return;
        }

        shootTimer += delta;
        if (shootTimer >= shootInterval) {
            shoot();
            shootTimer = 0;
        }

        stateTime += delta;
        box.setPosition(position.x, position.y);
        Vector2 playerPosition = GameManager.getNewGame().getPlayer().getPosition();

        Vector2 direction = new Vector2(playerPosition).sub(position).nor();
        position.add(direction.scl(speed * delta));

        if (Hp <= 0 && !isExploding) {
            isExploding = true;
            GameManager.getNewGame().getPlayer().setKillCount();
            monsterImage.remove();
            explosionImage.setPosition(position.x, position.y);
            Point point = new Point(position.x, position.y);
            GameManager.getNewGame().getGameStage().addActor(point.getImageBox());
            GameManager.getNewGame().getPoints().add(point);
            GameManager.getNewGame().getGameStage().addActor(explosionImage);
            this.dispose();
            isDead = true;
        } else {
            if (direction.x < 0) {
                monsterImage.setScale(-1, 1);
            } else {
                monsterImage.setScale(1, 1);
            }
            monsterImage.setPosition(position.x, position.y);
            monsterImage.setDrawable(new TextureRegionDrawable(walkAnimation.getKeyFrame(stateTime, true)));
        }
    }

    private void shoot() {
        Vector2 bulletDirection = new Vector2(GameManager.getNewGame().getPlayer().getPosition()).sub(position).nor();
        Bullet bullet = new Bullet(new Vector2(position.x, position.y), bulletDirection, true);
        GameManager.getNewGame().getGameStage().addActor(bullet);
        GameManager.getNewGame().getBullets().add(bullet);
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
