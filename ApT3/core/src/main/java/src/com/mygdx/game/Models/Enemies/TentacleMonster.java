package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import src.com.mygdx.game.Models.GameManager;
import src.com.mygdx.game.Models.Point;

public class TentacleMonster {
    private Vector2 position;
    private int Hp = 25;
    private float speed = 7f;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private Image monsterImage;
    private Rectangle box;
    private boolean isDead = false;

    private String[] images = new String[]{
        "enemies/TentacleMonster/1.png",
//        "enemies/TentacleMonster/2.png",
//        "enemies/TentacleMonster/3.png",
//        "enemies/TentacleMonster/4.png",
//        "enemies/TentacleMonster/5.png",
//        "enemies/TentacleMonster/6.png"
    };

    public TentacleMonster(int startX, int startY) {
        this.position = new Vector2(startX, startY);
        initializeAnimation();
        monsterImage.setPosition(position.x, position.y);
        box = new Rectangle(position.x, position.y, 20, 20);
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

    public void update(float delta) {
        stateTime += delta;
        box.setPosition(position.x, position.y);
        Vector2 playerPosition = GameManager.getNewGame().getPlayer().getPosition();

        Vector2 direction = new Vector2(playerPosition).sub(position).nor();
        position.add(direction.scl(speed * delta));

        if (Hp == 0) {
            isDead = true;
            // killed by player
            GameManager.getNewGame().getPlayer().setKillCount();
            monsterImage.remove();
            Point point = new Point(position.x, position.y);
            GameManager.getNewGame().getPoints().add(point);
            GameManager.getNewGame().getGameStage().addActor(point.getImageBox());
            this.dispose();
        }

        if (direction.x < 0) {
            monsterImage.setScale(-1, 1);
        } else {
            monsterImage.setScale(1, 1);
        }

            monsterImage.setPosition(position.x, position.y);
        monsterImage.setDrawable(new TextureRegionDrawable(walkAnimation.getKeyFrame(stateTime, true)));

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
