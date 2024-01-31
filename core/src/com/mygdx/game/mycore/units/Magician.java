package com.mygdx.game.mycore.units;

import java.util.ArrayList;
import java.util.Random;

public class Magician extends HeroBase {
    protected int mp, maxMp;

    public Magician(String name, int maxHp, int hp, int armor, int damage, int initiative,
                    double criticalChance, double evasion, int x, int y,
                    boolean liveStatus, String actions, String status) {
        super(name, maxHp, hp, armor, damage, initiative, criticalChance, evasion, x, y,
                liveStatus, actions, status);
    }

    public HeroBase getMostDamaged(ArrayList<HeroBase> allies) {
        int maxDamage = 0;
        HeroBase mostDamagedHero = null;
        for (HeroBase ally : allies) {
            if (ally.liveStatus) {
                if (mostDamagedHero == null || ally.getHealthReport() > maxDamage) {
                    maxDamage = ally.getHealthReport();
                    mostDamagedHero = ally;
                }
            }
        }
        return mostDamagedHero;
    }

    public int calculateHeal(HeroBase ally){
        Random random = new Random();
        int criticalDamage = 1;
        double randomCritValue = random.nextDouble();
        if (randomCritValue <= this.criticalChance) criticalDamage = 2;
        int currentHeal = -damage * criticalDamage;
        if (currentHeal < -ally.getHealthReport()) currentHeal = -ally.getHealthReport();
        return currentHeal;
    }

    public ArrayList<Melee> findMelee(ArrayList<HeroBase> allies) {
        ArrayList<Melee> melee = new ArrayList<>();
        for (HeroBase ally : allies) {
                if (ally.getType().equals("Pikeman") || ally.getType().equals("Rogue")) {
                    melee.add((Melee) ally);
            }
        }
        return melee;
    }

    public void restoreMp(int value){
        mp += value ;
        if (mp > maxMp) mp = maxMp;
    }

    public boolean allMeleeDead(ArrayList<Melee> melee){
        for (Melee heroBase : melee) {
            if (heroBase.liveStatus) return false;
        }
        return true;
    }

    public boolean isPositionEmpty(ArrayList<HeroBase> enemies, Coordinates deadHeroCoord){
        for (HeroBase enemy : enemies) {
            if (enemy.position.equals(deadHeroCoord)) return false;
        }
        return true;
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        if (!this.getLiveStatus()) {
            return;
        }
        if (mp < maxMp / 6) {
            restoreMp(maxMp /2 );
            return;
        }
        ArrayList<Melee> melee = findMelee(allies);
        if (allMeleeDead(findMelee(allies))){
            if (mp < maxMp){
                restoreMp( maxMp /2 );
                return;
            }
            for (Melee target : melee) {
                if (!isPositionEmpty(enemies,target.position)){
                    continue;
                }
                target.liveStatus = true;
                target.hp = target.maxHp / 2;
                this.mp = 0;
                return;
            }
        }
        HeroBase ally = getMostDamaged(allies);
        if (ally == null) return;
        int currentHeal = calculateHeal(ally);
        if (currentHeal == 0){
            return;
        }
        ally.getDamage(currentHeal);
        mp -= maxMp / 6;
    }
}
