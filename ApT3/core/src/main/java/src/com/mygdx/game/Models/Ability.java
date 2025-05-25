package src.com.mygdx.game.Models;

import src.com.mygdx.game.Models.Enums.AbilityTypes;

public class Ability {
    private AbilityTypes abilityType;
    private boolean used = false;
    private int timer  = 0;

    public Ability(AbilityTypes abilityType) {
        this.abilityType = abilityType;
        if (abilityType.equals(AbilityTypes.PROCREASE)) {
            GameManager.getNewGame().getPlayer().getWeapons().addProjectile();
            used = true;
        } else if (abilityType.equals(AbilityTypes.AMOCREASE)) {
            GameManager.getNewGame().getPlayer().getWeapons().addMaxAmmo(5);
            used = true;
        } else if (abilityType.equals(AbilityTypes.VITALITY)) {
            GameManager.getNewGame().getPlayer().addMaxHP(1);
            GameManager.getNewGame().getPlayer().addHp(1);
            used = true;
        } else if (abilityType.equals(AbilityTypes.DAMAGER)) {
            GameManager.getNewGame().getPlayer().getWeapons().addDamage();
            timer = 10;
            used = false;
        } else if (abilityType.equals(AbilityTypes.SPEEDY)) {
            GameManager.getNewGame().getPlayer().addMaxSpeed();
            timer = 10;
            used = false;
        }
    }

    public void update() {
        if (!used) {
            timer--;
            if (timer <= 0) {
                used = true;
                if (abilityType.equals(AbilityTypes.DAMAGER)) {
                    GameManager.getNewGame().getPlayer().getWeapons().decreaseDamage();
                } else if (abilityType.equals(AbilityTypes.SPEEDY)) {
                    GameManager.getNewGame().getPlayer().decreaseMaxSpeed();
                }
            }
        }
    }
    public AbilityTypes getAbilityType() {
        return abilityType;
    }

    public boolean isUsed() {
        return used;
    }
}
