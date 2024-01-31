package com.mygdx.game.mycore.units;

import java.util.ArrayList;

public class Rogue extends Melee {
    protected int invisibleStatus;
    protected double baseevasion;

    public Rogue(String name, int x, int y) {
        super(name, 300, 300, 30,
                60, 2, 0.1, 0.1, x, y,
                true, "", "");
        invisibleStatus = 0;
        baseevasion = 0.1;
    }

    @Override
    public String toString() {
        if (invisibleStatus == 1) return ("Invisible Rogue " + super.toString()) + ": " + actions;
        return ("Rogue " + super.toString()) + ": " + actions;
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        Coordinates curentPosition = this.position;
        super.step(enemies, allies);
        invisibleStatus = 0;
        actions = "";
        evasion = baseevasion;
        if (curentPosition != this.position) {
            invisibleStatus = 1;
            evasion += 0.9 * invisibleStatus;
            actions = "Invisible";
        }
    }
}
