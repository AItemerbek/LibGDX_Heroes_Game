package com.mygdx.game.mycore.units;

import java.util.ArrayList;
import java.util.List;

public class Melee extends HeroBase {

    public Melee(String name, int maxHp, int hp, int armor, int damage, int initiative,
                 double criticalChance, double evasion, int x, int y,
                 boolean liveStatus, String actions, String status) {
        super(name, maxHp, hp, armor, damage, initiative, criticalChance, evasion, x, y,
                liveStatus, actions,status);
    }


    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        if (!this.getLiveStatus()) {
            return;
        }
        HeroBase enemy = getNearestEnemy(enemies);
        if (enemy == null) {
            return;
        }
        if (this.getDistance(enemy) < 2) getDamageNearestEnemy(enemy, calculateDamage(this, enemy));
        else {
            List<List<Integer>> field = Coordinates.battlefield(allies,enemies);
            Coordinates self = new Coordinates(this.position.x-1,this.position.y-1);
            Coordinates target = new Coordinates(enemy.position.x-1,enemy.position.y-1);
            List<Coordinates> path = ShortestPathFinder.findShortestPath(field,self,target);
            if (path.isEmpty()) return;
            Coordinates pos = path.get(1);
            this.moveTo(pos);
        }
    }

}
