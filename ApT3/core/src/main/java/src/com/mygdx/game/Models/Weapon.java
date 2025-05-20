package src.com.mygdx.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Stage;
import src.com.mygdx.game.Models.Enums.Weapons;

public class Weapon {
    private Image weaponImage;
    private Vector2 position;
    private Texture weaponTexture;
    private Weapons weapon;
    private Stage stage;

    public Weapon(Weapons weapons) {
        this.weapon = weapons;
        weaponTexture = new Texture(weapon.getWeaponAd());
        weaponImage = new Image(weaponTexture);
        position = new Vector2(0, 0);
        weaponImage.setPosition(position.x, position.y);
    }

    public void initiate(){
        stage = GameManager.getNewGame().getGameStage();
    }

    public void update(Vector2 playerPosition) {
        position.set(playerPosition.x, playerPosition.y+10);
        weaponImage.setPosition(position.x, position.y);
        rotateToMouse();
        stage.addActor(this.weaponImage);
    }

    private void rotateToMouse() {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 worldMousePos = stage.screenToStageCoordinates(mousePos);
        float angle = MathUtils.atan2(worldMousePos.y - weaponImage.getY(), worldMousePos.x - weaponImage.getX()) * MathUtils.radDeg;
        weaponImage.setRotation(angle);
    }

    public Image getWeaponImage() {
        return weaponImage;
    }

    public void dispose() {
        weaponTexture.dispose();
    }

    public Weapons getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapons weapon) {
        this.weapon = weapon;
    }
}
