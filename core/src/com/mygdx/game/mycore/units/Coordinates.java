package com.mygdx.game.mycore.units;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Coordinates {
    public int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    public float distance(Coordinates enemyCoord) {
        return (float) Math.sqrt(Math.pow(x - enemyCoord.x, 2) + Math.pow(y - enemyCoord.y, 2));
    }

    public Coordinates deltaCoordinates(Coordinates enemy) {
        return new Coordinates(this.x - enemy.x, this.y - enemy.y);
    }

    public boolean equals(Coordinates newCoordinates) {
        return this.x == newCoordinates.x && this.y == newCoordinates.y;
    }

    public static List<List<Integer>> battlefield(ArrayList<HeroBase> allies,
                                                  ArrayList<HeroBase> enemy) {
        List<List<Integer>> location = IntStream.range(0, 10)
                .mapToObj(row -> new ArrayList<>(Arrays.asList(new Integer[10])))
                .peek(list -> list.replaceAll(value -> 0))
                .collect(Collectors.toList());
        ArrayList<HeroBase> teams =new ArrayList<>();
        teams.addAll(allies);
        teams.addAll(enemy);
        int newValue = 0;
        for (HeroBase unit : teams) {
            int i = unit.position.x - 1;
            int j = unit.position.y - 1;
            if (allies.contains(unit)) newValue = 1;
            else newValue = -1;
            List<Integer> row = new ArrayList<>(location.get(i));
            row.set( j, newValue);
            location.set(i, row);
        }
        return location;
    }

    public static HeroBase getHeroByPosition(ArrayList<HeroBase> team, int x, int y){
        Coordinates pos = new Coordinates(x,y);
        HeroBase unit = null;
        for (HeroBase heroBase : team) {
            if (heroBase.position.equals(pos)) unit = heroBase;
        }
        return unit;
    }


}
