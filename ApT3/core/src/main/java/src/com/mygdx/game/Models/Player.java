package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.Enemies.TentacleMonster;
import src.com.mygdx.game.Models.Enums.Heroes;
import src.com.mygdx.game.Models.Enums.Weapons;

public class Player {
    private Image playerImage;
    private Vector2 position;
    private Vector2 velocity;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private boolean isMoving;
    private Texture[] walkFrames;
    private Heroes hero = Heroes.DASHER;
    private Weapon weapon = null;
    private int HP;
    private int level = 0;
    private int XP = 0;
    private Rectangle box;
    private float invincibleTimer = 0;
    private boolean damaged = false;
    private int killCount = 0;

    public void initializePlayer() {
        walkFrames = new Texture[hero.getImages().length];
        TextureRegion[] regions = new TextureRegion[hero.getImages().length];

        for (int i = 0; i < hero.getImages().length; i++) {
            walkFrames[i] = new Texture(hero.getImages()[i]);
            regions[i] = new TextureRegion(walkFrames[i]);
        }

        walkAnimation = new Animation<>(0.1f, regions);
        stateTime = 0f;
        isMoving = false;

        position = new Vector2(600, 600);
        velocity = new Vector2(0, 0);

        playerImage = new Image(new TextureRegionDrawable(regions[0]));
        playerImage.setSize(15, 20);
        playerImage.setPosition(position.x, position.y);
        box = new Rectangle(position.x, position.y, playerImage.getImageWidth(), playerImage.getImageHeight());

    }

    public void moveLeft() {
        velocity.set(-40 * hero.getSpeed(), 0);
        isMoving = true;
        playerImage.setScale(-1, 1);
    }

    public void moveRight() {
        velocity.set(40 * hero.getSpeed(), 0);
        isMoving = true;
        playerImage.setScale(1, 1);
    }

    public void moveUp() {
        velocity.set(0, 40 * hero.getSpeed());
        isMoving = true;
    }

    public void moveDown() {
        velocity.set(0, -40 * hero.getSpeed());
        isMoving = true;
    }

    public void moveUpRight() {
        velocity.set(20 * hero.getSpeed(), 20 * hero.getSpeed());
        isMoving = true;
    }

    public void moveUpLeft() {
        velocity.set(-20 * hero.getSpeed(), 20 * hero.getSpeed());
        isMoving = true;
    }

    public void moveDownRight() {
        velocity.set(20 * hero.getSpeed(), -20 * hero.getSpeed());
        isMoving = true;
    }

    public void moveDownLeft() {
        velocity.set(-20 * hero.getSpeed(), -20 * hero.getSpeed());
        isMoving = true;
    }

    public void stop() {
        velocity.set(0, 0);
        isMoving = false;
    }

    public void update(float delta) {
        stateTime += delta;
        position.add(velocity.x * delta, velocity.y * delta);

        if (position.x < 0) position.x = 0;
        if (position.x + playerImage.getWidth() > Gdx.graphics.getWidth())
            position.x = Gdx.graphics.getWidth() - playerImage.getWidth();
        if (position.y < 0) position.y = 0;
        if (position.y + playerImage.getHeight() > Gdx.graphics.getHeight())
            position.y = Gdx.graphics.getHeight() - playerImage.getHeight();

        playerImage.setPosition(position.x, position.y);
        box.setPosition(position.x, position.y);
        weapon.update(delta);

        for (Point point : GameManager.getNewGame().getPoints()) {
            if (Intersector.overlaps(box, point.getBox())) {
                point.setVisible(true);
                setXP(3);
                point.getImageBox().remove();
                break;
            }
        }

        if (!damaged) {
            for (TentacleMonster monster : GameManager.getNewGame().getTentacleMonsters()) {
                if (Intersector.overlaps(box, monster.getBox())) {
                    HP -= 1;
                    damaged = true;
                    invincibleTimer = 2f;
                    break;
                }
            }
        }

        if (damaged) {
            invincibleTimer -= delta;
            playerImage.setColor(1, 1, 1, 0.5f);
            if (invincibleTimer <= 0) {
                damaged = false;
                playerImage.setColor(1, 1, 1, 1f);
            }
        }

        if (isMoving) {
            playerImage.setDrawable(new TextureRegionDrawable(walkAnimation.getKeyFrame(stateTime, true)));
        }

    }


    public void shoot() {
        if (weapon.getAmmo() < 0) {
            weapon.setIsReloading(true);
        }
        if (!weapon.isReloading()) {
            Vector2 bulletDirection = new Vector2((float) Math.cos(Math.toRadians(weapon.getWeaponImage().getRotation())),
                (float) Math.sin(Math.toRadians(weapon.getWeaponImage().getRotation())));

            Bullet bullet = new Bullet(new Vector2(position.x, position.y), bulletDirection);
            GameManager.getNewGame().getGameStage().addActor(bullet);
            GameManager.getNewGame().getBullets().add(bullet);
            weapon.setAmmo(1);
        }
    }

    public Image getPlayerImage() {
        return playerImage;
    }

    public void dispose() {
        for (Texture texture : walkFrames) {
            texture.dispose();
        }
    }

    public Weapon getWeapons() {
        return weapon;
    }

    public void setWeapons(Weapon weapons) {
        this.weapon = weapons;
    }

    public Heroes getHero() {
        return hero;
    }

    public void setHero(Heroes hero) {
        this.hero = hero;
        HP = hero.getHP();
    }

    public int getHP() {
        return Math.max(HP, 0);
    }

    public void setHP() {
        HP--;
    }

    public void addHp(int amount) {
        HP += amount;
        if (HP >= hero.getHP()) {
            HP = hero.getHP();
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel() {
        level++;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int value) {
        XP += value;
    }

    public Rectangle getBox() {
        return box;
    }

    public float getInvincibleTimer() {
        return invincibleTimer;
    }

    public void setInvincibleTimer(float invincibleTimer) {
        this.invincibleTimer = invincibleTimer;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount() {
        killCount++;
    }
}





