package src.com.mygdx.game.Models.Enums;

public enum AbilityTypes {
    VITALITY("VITALITY","Increase maximum HP\n by 1 unit","abilities/vitality.png"),
    DAMAGER("DAMAGER","Increase weapon damage \nby 25% for 10 seconds","abilities/damager.png"),
    PROCREASE("PROCREASE","Increase weapon projectile \nby 1 unit","abilities/procrease.png"),
    AMOCREASE("AMOCREASE","Increase weapon’s maximum ammo \nby 5 units","abilities/amocrease.png"),
    SPEEDY("SPEEDY","Doubles player movement speed \nfor 10 seconds","abilities/speedy.png");

    private String name;
    private String description;
    private String path;

    private AbilityTypes(String name, String description , String path) {
        this.name = name;
        this.description = description;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }
}

