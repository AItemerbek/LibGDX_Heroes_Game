package com.mygdx.game.mycore.units;

import java.util.ArrayList;
import java.util.Arrays;

public class Wizzard extends Magician {
    public Wizzard(String name, int x, int y) {
        super(name, 100, 100, 5,
                50, 3, 0.1, 0.2, x, y,
                true,"","");
        mp = 120;
        maxMp =120;
    }

    @Override
    public String toString() {
        return ("Wizzard " + super.toString()) + " ☼" + maxMp + "/" + mp + ": " + actions;
    }

    @Override
    public void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies) {
        int startMP = mp;
        super.step(enemies, allies);
        if (startMP == mp) {
            if (this.mp > this.maxMp / 2) {
                for (HeroBase enemy : enemies) {
                    if (enemy.liveStatus) {
                        enemy.getDamage((int) (dice() * damage / 10));
                    }
                }
                mp -= maxMp / 2;
            }

        }
        actions ="";
        char[] chars = new char[mp/20];
        Arrays.fill(chars, 'm');
        actions = new String(chars);
    }
}
