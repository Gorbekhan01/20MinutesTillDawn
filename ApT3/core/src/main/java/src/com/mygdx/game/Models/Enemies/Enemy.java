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

public abstract class Enemy {
    protected Vector2 position;
    protected int Hp;
    protected float speed;
    protected Animation<TextureRegion> walkAnimation;
    protected Animation<TextureRegion> explosionAnimation;
    protected float stateTime;
    protected float stateTime2;
    protected Image monsterImage;
    protected Image explosionImage;
    protected Rectangle box;
    protected boolean isDead;
    protected boolean isExploding;
    protected String[] images;
    protected String[] explosionImages;

    public Enemy(int startX, int startY, int hp, float speed, String[] images, String[] explosionImages) {
        this.position = new Vector2(startX, startY);
        this.Hp = hp;
        this.speed = speed;
        this.images = images;
        this.explosionImages = explosionImages;
        this.isDead = false;
        this.isExploding = false;
        this.stateTime = 0f;
        this.stateTime2 = 0f;
    }

    protected void initializeAnimation() {
        TextureRegion[] frames = new TextureRegion[images.length];
        for (int i = 0; i < images.length; i++) {
            frames[i] = new TextureRegion(new Texture(images[i]));
        }
        walkAnimation = new Animation<>(0.1f, frames);
        monsterImage = new Image(new TextureRegionDrawable(frames[0]));
    }

    protected void initializeExplosion() {
        TextureRegion[] frames = new TextureRegion[explosionImages.length];
        for (int i = 0; i < explosionImages.length; i++) {
            frames[i] = new TextureRegion(new Texture(explosionImages[i]));
        }
        explosionAnimation = new Animation<>(0.1f, frames);
        explosionImage = new Image(new TextureRegionDrawable(frames[0]));
    }

    public void update(float delta) {
        if (isExploding) {
            handleExplosion(delta);
            return;
        }

        updateMovement(delta);
        checkDeath();
    }

    protected void handleExplosion(float delta) {
        stateTime2 += delta;
        explosionImage.setDrawable(new TextureRegionDrawable(explosionAnimation.getKeyFrame(stateTime2, false)));

        if (explosionAnimation.isAnimationFinished(stateTime2)) {
            explosionImage.remove();
            isDead = true;
            this.dispose();
        }
    }

    protected abstract void updateMovement(float delta);

    protected void checkDeath() {
        if (Hp <= 0 && !isExploding) {
            if (this instanceof Elder) {
                GameManager.getNewGame().setElderAlive(false);
                GameManager.getNewGame().resetGroundLimited();
            }
            isExploding = true;
            GameManager.getNewGame().getPlayer().setKillCount();
            monsterImage.remove();
            isDead = true;
            explosionImage.setPosition(position.x, position.y);
            Point point = new Point(position.x, position.y);
            GameManager.getNewGame().getGameStage().addActor(point.getImageBox());
            GameManager.getNewGame().getPoints().add(point);
            GameManager.getNewGame().getGameStage().addActor(explosionImage);
        }
    }

    public void dispose() {
        for (String image : images) {
            new Texture(image).dispose();
        }
    }

    public Image getMonsterImage() {
        return monsterImage;
    }

    public Rectangle getBox() {
        return box;
    }

    public void setHp(int damage) {
        Hp -= damage;
        if (Hp <= 0) Hp = 0;
    }

    public boolean isDead() {
        return isDead;
    }
}
