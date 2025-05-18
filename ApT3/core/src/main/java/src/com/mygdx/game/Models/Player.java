package src.com.mygdx.game.Models;

import src.com.mygdx.game.Models.Enums.Heroes;
import src.com.mygdx.game.Models.Enums.Weapons;

public class Player {
    private Heroes hero;
    private Weapons weapons;

    public void setHero(Heroes hero) {
        this.hero = hero;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }
}
