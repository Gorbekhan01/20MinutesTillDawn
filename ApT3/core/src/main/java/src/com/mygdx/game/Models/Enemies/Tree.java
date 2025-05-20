package src.com.mygdx.game.Models.Enemies;

import com.badlogic.gdx.math.Rectangle;

public class Tree {
    private int x;
    private int y;
    private Rectangle box;
    private String image = "enemies/tree.png";

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        box = new Rectangle(x, y, 60, 60);
    }

    public Rectangle getBox() {
        return box;
    }

    public String getImage() {
        return image;
    }
}
