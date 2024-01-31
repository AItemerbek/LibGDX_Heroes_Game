package com.mygdx.game.mycore.units;

import java.util.ArrayList;

public interface Game {
    void step(ArrayList<HeroBase> enemies, ArrayList<HeroBase> allies);
}
