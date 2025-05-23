package src.com.mygdx.game.Models.Enums;

public enum Weapons {
    DUAL_SMGs(6,1,1,20,"weapons/DualSMGs.png"),
    REVOLVER(2,1,1,10,"weapons/Revolver.png"),
    SHOTGUN(24,2,4,8,"weapons/Shotgun.png");


    private int ammoMax;
    private int reloadTime;
    private int projectile;
    private int damage;
    private String weaponAd;


    Weapons(int ammoMax, int reloadTime, int projectile, int damage, String weaponAd) {
        this.ammoMax = ammoMax;
        this.reloadTime = reloadTime;
        this.projectile = projectile;
        this.damage = damage;
        this.weaponAd = weaponAd;
    }

    public int getAmmoMax() {
        return ammoMax;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getDamage() {
        return damage;
    }

    public String getWeaponAd() {
        return weaponAd;
    }
}
