package src.com.mygdx.game.Models.Enums;

public enum Heroes {
    SHANA(4,4 , "Heroes/Shana.png"),
    DIAMOND(1,7 , "Heroes/Diamond.png"),
    SCARLET(5,3 , "Heroes/Scarlet.png"),
    LILITH(3,5 , "Heroes/Lilith.png"),
    DASHER(10,2,"Heroes/Dasher.png");

    private int speed;
    private int HP;
    private String imageAd;


    Heroes(int speed, int HP , String imageAd) {
        this.speed = speed;
        this.HP = HP;
        this.imageAd = imageAd;
    }

    public int getSpeed() {
        return speed;
    }

    public String getImageAd() {
        return imageAd;
    }

    public void setImageAd(String imageAd) {
        this.imageAd = imageAd;
    }
}

