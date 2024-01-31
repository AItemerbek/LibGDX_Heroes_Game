package com.mygdx.game.mycore.units;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mygdx.game.mycore.units.ShortestPathFinder.*;

abstract public class HeroBase implements Game {
    protected String name;
    protected int maxHp, hp, armor, damage, initiative;
    protected double criticalChance, evasion;
    protected Coordinates position;
    protected boolean liveStatus;
    protected String actions;
    protected String status;


    protected HeroBase(String name, int maxHp, int hp, int armor, int damage, int initiative,
                       double criticalChance, double evasion, int x, int y, boolean liveStatus,
                       String actions, String status) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = hp;
        this.armor = armor;
        this.damage = damage;
        this.initiative = initiative;
        this.criticalChance = criticalChance;
        this.evasion = evasion;
        this.position = new Coordinates(x, y);
        this.liveStatus = liveStatus;
        this.actions = "";
        this.status = "";
    }

    static Random random = new Random();

    public float getDistance(HeroBase enemy) {
        return position.distance(enemy.position);
    }

    public int getInitiative() {
        return this.initiative;
    }

    public boolean getLiveStatus() {
        return this.liveStatus;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }


    public String getActions() {return actions;}

    public int getMaxHp() {return maxHp;}
    public int getHp() {return hp;}

    public void changeStatus(String info) {
        this.status = info;
    }

    public HeroBase getNearestEnemy(ArrayList<HeroBase> enemies) {
        HeroBase nearestEnemy = null;
        for (HeroBase enemy : enemies) {
            if (enemy.liveStatus) {
                if (nearestEnemy == null ||
                        position.distance(enemy.position)
                      < position.distance(nearestEnemy.position)) {
                    nearestEnemy = enemy;
                }
            }
        }
        return nearestEnemy;
    }

    public double dice() {
        return random.nextDouble(0.8, 1.2);
    }

    public int calculateDamage(HeroBase self, HeroBase enemy) {
        int criticalDamage = 1;
        int evaletionEffect = 1;
        double randomCritValue = random.nextDouble();
        if (randomCritValue <= self.criticalChance) criticalDamage = 2;
        double randomEvValue = random.nextDouble();
        if (randomEvValue <= enemy.evasion) evaletionEffect = 10;
        return (int) (dice() * ((self.damage * criticalDamage) *
                (100 - enemy.armor) * 0.01 / evaletionEffect));
    }


    public void getDamage(int currentDamage) {
        if (!this.liveStatus) return;
        if (currentDamage == 0) currentDamage = 1;
        this.hp -= currentDamage;
        if (this.hp <= 0) {
            this.hp = 0;
            this.liveStatus = false;
        }
    }

    public void getDamageNearestEnemy(HeroBase enemy, int currentDamage) {
        if (currentDamage == 0) currentDamage = 1;
        if (currentDamage > enemy.hp) currentDamage = enemy.hp;
        enemy.getDamage(currentDamage);
    }

    protected void moveTo(Coordinates target) {
        Coordinates destination = target;
        Coordinates delta = position.deltaCoordinates(destination);
        this.position = new Coordinates(target.x, target.y);
        if (delta.y < 0) {
            this.status = "R";
        }
        if (delta.y > 0) {
            this.status = "L";
        }
    }
    public int getHealthReport() {
        return this.maxHp - this.hp;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return (name + position + " ♥" + maxHp + "/" + hp);
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
    }
}
