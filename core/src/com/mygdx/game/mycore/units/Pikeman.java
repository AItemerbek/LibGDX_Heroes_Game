package com.mygdx.game.mycore.units;

import java.util.ArrayList;

public class Pikeman extends Melee {
    protected int shieldStatus, basearmor;

    public Pikeman(String name, int x, int y) {
        super(name, 300, 300, 30,
                70, 2, 0.1, 0.1, x, y,
                true, "","");
        shieldStatus = 0;
        basearmor = 30;
    }

    @Override
    public String toString() {
        if (shieldStatus == 1) return ("Shielded Pikeman " + super.toString()) + ": " + actions;
        else return ("Pikeman " + super.toString()) + ": " + actions;
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        Coordinates curentPosition = this.position;
        super.step(enemies, allies);
        shieldStatus = 0;
        actions = "";
        armor = basearmor;
        if (curentPosition != this.position) {
            shieldStatus = 1;
            armor += armor * shieldStatus;
            actions = "Shielded";
        }
    }
}
