package com.mygdx.game.mycore.units;

import java.util.ArrayList;

public class Apprentice extends HeroBase {
    public Apprentice(String name, int x, int y) {
        super(name, 200, 200, 0,
                10, 4, 0, 0.2, x, y,
                true,"", "");
    }

    public ArrayList<Archer> findAllArcher(ArrayList<HeroBase> allies) {
        ArrayList<Archer> archers = new ArrayList<>();
        for (HeroBase ally : allies) {
            if (ally.liveStatus) {
                if (ally.getType().equals("Crossbowman") || ally.getType().equals("Sniper")) {
                    archers.add((Archer) ally);
                }
            }
        }
        return archers;
    }

    public Archer findBestChoice(ArrayList<Archer> archers) {
        Archer target = archers.get(0);
        int minArrows = 1000;
        for (Archer archer : archers) {
            if (archer.arrows < minArrows) {
                target = archer;
                minArrows = archer.arrows;
            }
        }
        return target;
    }

    @Override
    public String toString() {
        return ("Apprentice " + super.toString() + ": " + actions);
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        if (!this.getLiveStatus()) {
            return;
        }
        ArrayList<Archer> alliesArchers = findAllArcher(allies);
        if (alliesArchers.isEmpty()) {
            return;
        }
        Archer target = findBestChoice(alliesArchers);
        int quantity = 1;
        if (target.getType().equals("Crossbowman")) quantity = 2;
        target.arrows += quantity;
        if (target.arrows > 8) target.arrows = 8;
        HeroBase enemy = getNearestEnemy(enemies);
        if (enemy == null) return;
        if (this.getDistance(enemy) < 2) {
            enemy.getDamage(calculateDamage(this, enemy));
        }
    }
}
