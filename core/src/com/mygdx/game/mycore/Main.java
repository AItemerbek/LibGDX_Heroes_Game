package com.mygdx.game.mycore;

import com.mygdx.game.mycore.units.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int numberOfTeams = 10;
        createTeams(numberOfTeams);
        heroOrder.addAll(lightSide);
        heroOrder.addAll(darkSide);
        heroOrder.sort(Comparator.comparingInt(HeroBase::getInitiative));
        int steps = 0;
        if (teamFall(lightSide)) {
            System.out.println("Darkside team WIN!!!");

        }
        if (teamFall(darkSide)) {
            System.out.println("Lightside team WIN!!!");

        }
        if (steps >= 200) {
            System.out.println("Draw!!!");

        }
        heroOrder = teemSteps(heroOrder);
        steps++;
//        Coordinates.battlefield(heroOrder);

    }

    static boolean teamFall(ArrayList<HeroBase> team) {
        for (HeroBase heroBase : team) {
            if (heroBase.getLiveStatus()) return false;
        }
        return true;
    }

    static ArrayList<HeroBase> teemSteps(ArrayList<HeroBase> order) {
        for (HeroBase hero : order) {
            if (lightSide.contains(hero)) hero.step(darkSide, lightSide);
            else hero.step(lightSide, darkSide);
        }
//        return cleanDeadHeroes(order);
        return order;
    }

    static String getName() {
        return Names.values()[new Random().nextInt(Names.values().length - 1)].toString();
    }

    static void createTeams(int numbers) {
        for (int i = 0; i < numbers; i++) {
            darkSide.add(getRandomHero(random.nextInt(6, 11), i + 1, numbers));
            lightSide.add(getRandomHero(random.nextInt(6), i + 1, 1));
        }
    }

    static HeroBase getRandomHero(int choice, int x, int y) {
        HeroBase base = null;
        switch (choice) {
            case 0:
                base = new Monk(getName(), x, y);
                break;
            case 1:
                base = new Pikeman(getName(), x, y);
                break;
            case 2:
                base = new Pikeman(getName(), x, y);
                break;
            case 3:
                base = new Crossbowman(getName(), x, y);
                break;
            case 4:
                base = new Crossbowman(getName(), x, y);
                break;
            case 5:
                base = new Apprentice(getName(), x, y);
                break;
            case 6:
                base = new Rogue(getName(), x, y);
                break;
            case 7:
                base = new Rogue(getName(), x, y);
                break;
            case 8:
                base = new Sniper(getName(), x, y);
                break;
            case 9:
                base = new Sniper(getName(), x, y);
                break;
            case 10:
                base = new Wizzard(getName(), x, y);
                break;
            default:
        }
        return base;
    }

    static Random random = new Random();
    static ArrayList<HeroBase> darkSide = new ArrayList<>();
    static ArrayList<HeroBase> lightSide = new ArrayList<>();
    public static ArrayList<HeroBase> heroOrder = new ArrayList<>();
}
