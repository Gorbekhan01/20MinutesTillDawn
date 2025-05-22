package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.Enums.Weapons;

public class Weapon {
    private Image weaponImage;
    private Vector2 position;
    private Texture weaponTexture;
    private Weapons weapon;
    private Stage stage;
    private int damage;
    private int maxAmmo;
    private int reloadTime;
    private int ammo;
    private boolean isReloading = false;
    private float reloadTimer = 0f;

    private String[] reloadingImages = new String[] {
        "weapons/reloadWeapon/1.png",
        "weapons/reloadWeapon/2.png",
        "weapons/reloadWeapon/3.png",
    };

    private Animation<TextureRegion> reloadAnimation;
    private float stateTime;
    private Image reloadImage;

    public Weapon(Weapons weapons) {
        this.weapon = weapons;
        weaponTexture = new Texture(weapon.getWeaponAd());
        weaponImage = new Image(weaponTexture);
        position = new Vector2(0, 0);
        weaponImage.setPosition(position.x, position.y);
        damage = weapons.getDamage();
        maxAmmo = weapon.getAmmoMax();
        ammo = maxAmmo;
        reloadTime = weapons.getReloadTime();
        initializeAnimation();
    }

    private void initializeAnimation() {
        TextureRegion[] frames = new TextureRegion[reloadingImages.length];
        for (int i = 0; i < reloadingImages.length; i++) {
            frames[i] = new TextureRegion(new Texture(reloadingImages[i]));
        }
        reloadAnimation = new Animation<>(0.1f, frames);
        stateTime = 0f;
        reloadImage = new Image(new TextureRegionDrawable(frames[0]));
        reloadImage.setSize(5, 5);
    }

    public void initiate() {
        stage = GameManager.getNewGame().getGameStage();
    }

    public void update(float delta) {
        Vector2 playerPosition = GameManager.getNewGame().getPlayer().getPosition();
        position.set(playerPosition.x, playerPosition.y + 10);
        weaponImage.setPosition(position.x, position.y);

        if (isReloading) {
            stateTime += delta;
            reloadTimer += delta;
            weaponImage.setDrawable(new TextureRegionDrawable(reloadAnimation.getKeyFrame(stateTime, true)));

            if (reloadTimer >= reloadTime) {
                ammo = maxAmmo;
                isReloading = false;
                reloadTimer = 0f;
                weaponImage.setDrawable(new TextureRegionDrawable(new TextureRegion(weaponTexture)));
            }

        } else {
            rotateToMouse();
        }
        stage.addActor(this.weaponImage);

    }

    private void rotateToMouse() {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 worldMousePos = stage.screenToStageCoordinates(mousePos);
        float angle = MathUtils.atan2(worldMousePos.y - weaponImage.getY(), worldMousePos.x - weaponImage.getX()) * MathUtils.radDeg;
        weaponImage.setRotation(angle);
    }

    public void setAmmo(int usedAmmo) {
        ammo -= usedAmmo;
        if (ammo <= 0 && !isReloading) {
            isReloading = true;
            stateTime = 0f;
            reloadTimer = 0f;
        }
    }

    public boolean isReloading() {
        return isReloading;
    }

    public Image getWeaponImage() {
        return weaponImage;
    }

    public int getDamage() {
        return damage;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void dispose() {
        weaponTexture.dispose();
    }

    public Weapons getWeapon() {
        return weapon;
    }

    public void setIsReloading(boolean isReloading) {
        this.isReloading = isReloading;
    }
}
