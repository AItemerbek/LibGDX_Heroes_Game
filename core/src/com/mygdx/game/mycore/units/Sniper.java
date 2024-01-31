package com.mygdx.game.mycore.units;

import java.util.ArrayList;

public class Sniper extends Archer{
    public Sniper(String name, int x, int y) {
        super(name, 150, 150, 10,
                60, 1, 0.2, 0.3, x, y,
                true,"","");
        arrows = 4;
        attackDistance = 6;
    }

    @Override
    public String toString() {
        return ("Sniper " + super.toString() + " ↑" + arrows + ": " + actions);
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        super.step(enemies,allies);
    }
}
