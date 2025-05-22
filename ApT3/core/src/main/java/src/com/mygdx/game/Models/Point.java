package src.com.mygdx.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Point {
    private Vector2 position;
    private Rectangle box;
    private String image = "others/point.png";
    private Texture texture;
    private Image imageBox;
    private boolean inVisible = false;

    public Point(float x, float y) {
        this.position = new Vector2(x, y);
        this.box = new Rectangle(position.x, position.y, 45, 45);
        this.texture = new Texture(image);
        this.imageBox = new Image(texture);
        imageBox.setSize(2,2);
        imageBox.setPosition(position.x, position.y);
    }

    public Rectangle getBox() {
        return box;
    }

    public Image getImageBox() {
        return imageBox;
    }

    public boolean getVisible() {
        return inVisible;
    }

    public void setVisible(boolean visible) {
        this.inVisible = visible;
    }

}
