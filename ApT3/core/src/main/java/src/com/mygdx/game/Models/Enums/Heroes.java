package src.com.mygdx.game.Models.Enums;

import com.badlogic.gdx.graphics.Texture;

public enum Heroes {
    SHANA(4,4 , new String[] {
        "heroes/Shana/Walk_0.png",
        "heroes/Shana/Walk_1.png",
        "heroes/Shana/Walk_2.png",
        "heroes/Shana/Walk_3.png",
        "heroes/Shana/Walk_4.png",
        "heroes/Shana/Walk_5.png",
        "heroes/Shana/Walk_6.png"
    }),
    DIAMOND(1,7 , new String[] {
        "heroes/Diamond/Walk_0.png",
        "heroes/Diamond/Walk_1.png",
        "heroes/Diamond/Walk_2.png",
        "heroes/Diamond/Walk_3.png",
        "heroes/Diamond/Walk_4.png",
        "heroes/Diamond/Walk_5.png",
        "heroes/Diamond/Walk_6.png",
        "heroes/Diamond/Walk_7.png",
    }),
    SCARLET(5,3 , new String[] {
        "heroes/Scarlet/Walk_0.png",
        "heroes/Scarlet/Walk_1.png",
        "heroes/Scarlet/Walk_2.png",
        "heroes/Scarlet/Walk_3.png",
        "heroes/Scarlet/Walk_4.png",
        "heroes/Scarlet/Walk_5.png",
        "heroes/Scarlet/Walk_6.png",
        "heroes/Scarlet/Walk_7.png",
    }),
    LILITH(3,5 , new String[] {
        "heroes/Lilith/Walk_0.png",
        "heroes/Lilith/Walk_1.png",
        "heroes/Lilith/Walk_2.png",
        "heroes/Lilith/Walk_3.png",
        "heroes/Lilith/Walk_4.png",
        "heroes/Lilith/Walk_5.png",
        "heroes/Lilith/Walk_6.png",
        "heroes/Lilith/Walk_7.png",
    }),
    DASHER(10,2,new String[] {
        "heroes/Dasher/Walk_0.png",
        "heroes/Dasher/Walk_1.png",
        "heroes/Dasher/Walk_2.png",
        "heroes/Dasher/Walk_3.png",
        "heroes/Dasher/Walk_4.png",
        "heroes/Dasher/Walk_5.png",
        "heroes/Dasher/Walk_6.png",
        "heroes/Dasher/Walk_7.png",
    });

    private int speed;
    private int HP;
    private String[] images;


    Heroes(int speed, int HP , String[] images) {
        this.speed = speed;
        this.HP = HP;
        this.images = images;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHP() {
        return HP;
    }

    public String[] getImages() {
        return images;
    }

}

