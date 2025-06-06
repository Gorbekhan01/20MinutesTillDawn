package src.com.mygdx.game.Models;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.Enemies.Elder;
import src.com.mygdx.game.Models.Enemies.Enemy;
import src.com.mygdx.game.Models.Enemies.EyeBat;
import src.com.mygdx.game.Models.Enemies.TentacleMonster;
import src.com.mygdx.game.Models.Enums.Heroes;
import src.com.mygdx.game.Models.Enums.Weapons;

import java.util.ArrayList;
import java.util.logging.Level;

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
    private int HP = 0;
    private int maxHP = 0;
    private int level = 0;
    private int XP = 0;
    private int XP4Level = 0;
    private Rectangle box;
    private float invincibleTimer = 0;
    private boolean damaged = false;
    private int killCount = 0;
    private ArrayList<Ability> abilities = new ArrayList<>();
    private int maxSpeed;


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
        HP = hero.getHP();

        playerImage = new Image(new TextureRegionDrawable(regions[0]));
        playerImage.setSize(15, 20);
        playerImage.setPosition(position.x, position.y);
        box = new Rectangle(position.x, position.y, playerImage.getImageWidth(), playerImage.getImageHeight());
        XP = 0;
        level = 0;
        maxHP = hero.getHP();
        maxSpeed = hero.getSpeed();

    }

    public void moveLeft() {
        velocity.set(-40 * maxSpeed, 0);
        isMoving = true;
        playerImage.setScale(-1, 1);
    }

    public void moveRight() {
        velocity.set(40 * maxSpeed, 0);
        isMoving = true;
        playerImage.setScale(1, 1);
    }

    public void moveUp() {
        velocity.set(0, 40 * maxSpeed);
        isMoving = true;
    }

    public void moveDown() {
        velocity.set(0, -40 * maxSpeed);
        isMoving = true;
    }

    public void moveUpRight() {
        velocity.set(20 * maxSpeed, 20 * maxSpeed);
        isMoving = true;
    }

    public void moveUpLeft() {
        velocity.set(-20 * maxSpeed, 20 * maxSpeed);
        isMoving = true;
    }

    public void moveDownRight() {
        velocity.set(20 * maxSpeed, -20 * maxSpeed);
        isMoving = true;
    }

    public void moveDownLeft() {
        velocity.set(-20 * maxSpeed, -20 * maxSpeed);
        isMoving = true;
    }

    public void stop() {
        velocity.set(0, 0);
        isMoving = false;
    }

    public void update(float delta) {
        stateTime += delta;
        position.add(velocity.x * delta, velocity.y * delta);

        if (GameManager.getNewGame().isGroundLimited()) {
            if (GameManager.getNewGame().getBound().isPlayerInside(this)) {
                position.x -= velocity.x * delta;
                position.y -= velocity.y * delta;
                if (!damaged) {
                    HP -= 1;
                    damaged = true;
                    invincibleTimer = 2f;
                }
            }
        }


        if (position.x < -playerImage.getWidth() / 2) position.x = -playerImage.getWidth() / 2;
        if (position.x + playerImage.getWidth() / 2 > Gdx.graphics.getWidth())
            position.x = Gdx.graphics.getWidth() - playerImage.getWidth() / 2;
        if (position.y < -playerImage.getHeight() / 2) position.y = -playerImage.getHeight() / 2;
        if (position.y + playerImage.getHeight() / 2 > Gdx.graphics.getHeight())
            position.y = Gdx.graphics.getHeight() - playerImage.getHeight() / 2;


        playerImage.setPosition(position.x, position.y);
        box.setPosition(position.x, position.y);
        weapon.update(delta);

        for (Point point : GameManager.getNewGame().getPoints()) {
            if (Intersector.overlaps(box, point.getBox())) {
                GameManager.playSound("sounds/point.wav");
                point.setVisible(true);
                setXP(3);
                setXP4Level(3);
                point.getImageBox().remove();
                break;
            }
        }

        if (!damaged) {
            for (Enemy monster : GameManager.getNewGame().getEnemies()) {
                if (Intersector.overlaps(box, monster.getBox()) && !monster.isDead()) {
                    HP -= 1;
                    damaged = true;
                    position = new Vector2(this.getPosition().x + 5, this.getPosition().y + 5);
                    invincibleTimer = 2f;
                    break;
                }
            }
        }

        if (damaged) {
            invincibleTimer -= delta;
            float alpha = (float) (0.5f + 0.5f * Math.sin(invincibleTimer * 10));
            playerImage.setColor(1, 1, 1, alpha);
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
            GameManager.playSound("sounds/shot.wav");
            Vector2 bulletDirection = new Vector2((float) Math.cos(Math.toRadians(weapon.getWeaponImage().getRotation())),
                (float) Math.sin(Math.toRadians(weapon.getWeaponImage().getRotation())));

            for (int i = 0; i < weapon.getProjectile(); i++) {
                Bullet bullet = new Bullet(new Vector2(position.x + i * 10, position.y + i * 10), bulletDirection, false);
                GameManager.getNewGame().getGameStage().addActor(bullet);
                GameManager.getNewGame().getBullets().add(bullet);
            }
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
        if (HP >= maxHP) {
            HP = maxHP;
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

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void addMaxHP(int add) {
        maxHP += add;
    }


    public void addMaxSpeed() {
        maxSpeed = maxSpeed * 2;
    }

    public void decreaseMaxSpeed() {
        maxSpeed = maxSpeed / 2;
    }

    public int getXP4Level(){
        return XP4Level;
    }

    public void setXP4Level(int XP4Level) {
        this.XP4Level += XP4Level;
    }

    public void resetXP4Level(){
        XP4Level = 0;
    }
}





