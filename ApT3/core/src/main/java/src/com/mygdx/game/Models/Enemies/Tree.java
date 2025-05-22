package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Tree {
    private int x;
    private int y;
    private Rectangle box;
    private String image = "enemies/tree.png";
    private Texture texture;
    private Image img;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        box = new Rectangle(x, y, 60, 60);
        texture = new Texture(image);
        img = new Image(texture);

    }

    public Rectangle getBox() {
        return box;
    }

    public Image getImage() {
        return img;
    }
}
