package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mycore.units.Apprentice;
import com.mygdx.game.mycore.units.Coordinates;
import com.mygdx.game.mycore.units.Crossbowman;
import com.mygdx.game.mycore.units.HeroBase;
import com.mygdx.game.mycore.units.Monk;
import com.mygdx.game.mycore.units.Names;
import com.mygdx.game.mycore.units.Pikeman;
import com.mygdx.game.mycore.units.Rogue;
import com.mygdx.game.mycore.units.Sniper;
import com.mygdx.game.mycore.units.Wizzard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture crossBowMan, mage, monk, peasant, rouge, sniper, spearMan, fon;
    Texture crossBowManDead, mageDead, monkDead, peasantDead, rougeDead, sniperDead, spearManDead;
    Texture shield, winged, arrow, maxHP, hp, mp;
    BitmapFont font;
    Music music;
    Random random = new Random();
    ArrayList<HeroBase> darkSide;
    ArrayList<HeroBase> lightSide;
    ArrayList<HeroBase> heroOrder;
    int steps = 0;
    boolean step = true;

    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music/11-Fight2.mp3"));
        music.play();
        music.isLooping();
        fon = new Texture("background/grass.png");
        darkSide = new ArrayList<>();
        lightSide = new ArrayList<>();
        heroOrder = new ArrayList<>();
        batch = new SpriteBatch();
        font = new BitmapFont();
        int numberOfTeams = 10;
        createTeams(numberOfTeams);
        heroOrder.addAll(lightSide);
        heroOrder.addAll(darkSide);
        heroOrder.sort((o1, o2) -> o1.getInitiative() - o2.getInitiative());

        this.spearMan = new Texture("units/pikeman.png");
        this.sniper = new Texture("units/sniper.png");
        this.rouge = new Texture("units/rogue.png");
        this.mage = new Texture("units/wizard.png");
        this.monk = new Texture("units/monk.png");
        this.crossBowMan = new Texture("units/crossbowman.png");
        this.peasant = new Texture("units/apprentice.png");
        this.spearManDead = new Texture("deadunits/pikeman53.png");
        this.sniperDead = new Texture("deadunits/sniper53.png");
        this.rougeDead = new Texture("deadunits/rogue53.png");
        this.mageDead = new Texture("deadunits/wizard53.png");
        this.monkDead = new Texture("deadunits/monk53.png");
        this.crossBowManDead = new Texture("deadunits/crossbowman53.png");
        this.peasantDead = new Texture("deadunits/apprentice53.png");
        this.shield = new Texture("effects/shield.png");
        this.winged = new Texture("effects/winged.png");
        this.arrow = new Texture("effects/arrow.png");
        this.maxHP = new Texture("effects/maxHP.png");
        this.hp = new Texture("effects/hp.png");
        this.mp = new Texture("effects/mp.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        HashMap<String, Texture> sprites = new HashMap<>();
        sprites.put("S", sniper);
        sprites.put("P", spearMan);
        sprites.put("R", rouge);
        sprites.put("M", monk);
        sprites.put("C", crossBowMan);
        sprites.put("A", peasant);
        sprites.put("W", mage);
        sprites.put("s", sniperDead);
        sprites.put("p", spearManDead);
        sprites.put("r", rougeDead);
        sprites.put("m", monkDead);
        sprites.put("c", crossBowManDead);
        sprites.put("a", peasantDead);
        sprites.put("w", mageDead);
        batch.begin();
        batch.draw(fon, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        String choice = "";
        for (int i = 10; i >= 1; i--) {
            for (int j = 1; j <= 10; j++) {
                int x = j;
                int y = i;
                HeroBase hero = Coordinates.getHeroByPosition(heroOrder, i, j);
                if (hero == null) choice = "";
                else {
                    choice = String.valueOf(hero.getType().charAt(0));
                    if (!hero.getLiveStatus()) choice = choice.toLowerCase();

                    int h = Objects.equals(hero.getStatus(), "L") ? 1 : -1;
                    x += h == 1 ? 0 : 1;
                    x *= Gdx.graphics.getWidth() / 12;
                    y *= Gdx.graphics.getHeight() / 10 - 10;
                    if (sprites.containsKey(choice)) {
                        if (choice.equals("R") && hero.getActions().equals("Invisible"))
                            batch.draw(winged, x, y,
                                    sniper.getWidth() * h, sniper.getHeight());
                        batch.draw(sprites.get(choice), x, y,
                                sniper.getWidth() * h, sniper.getHeight());
                        if (choice.equals("P") && hero.getActions().equals("Shielded"))
                            batch.draw(shield, x, y,
                                    sniper.getWidth() * h, sniper.getHeight());
                        if (choice.equals("S") || choice.equals("C")) {
                            {
                                for (int k = 0; k < hero.getActions().length(); k++) {
                                    batch.draw(arrow, x, y - k * 4,
                                            sniper.getWidth() * h, sniper.getHeight());
                                }
                            }
                        }
                        if (choice.equals("W") || choice.equals("M")) {
                            {
                                for (int k = 0; k < hero.getActions().length(); k++) {
                                    batch.draw(mp, x, y + k * 3,
                                            sniper.getWidth() * h, sniper.getHeight());
                                }
                            }
                        }
                        if (hero.getLiveStatus()) {
                            batch.draw(maxHP, x, y, sniper.getWidth() * h, 34);
                            batch.draw(hp, x, y + 1,
                                    sniper.getWidth() * h, 32 * hero.getHp() / hero.getMaxHp());
                        }
                    }
                }
            }
        }

        batch.end();

        if (Gdx.input.justTouched() & step) {
            if (teamFall(lightSide)) {
                Gdx.graphics.setTitle("Darkside team WIN!!!");
                step = false;
            }
            if (teamFall(darkSide)) {
                Gdx.graphics.setTitle("Lightside team WIN!!!");
                step = false;
            }
            if (steps >= 200) {
                Gdx.graphics.setTitle("Draw!!!");
                step = false;
            }
            try {
                heroOrder = teemSteps(heroOrder);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            steps++;
        }
    }


    @Override
    public void dispose() {
        fon.dispose();
        batch.dispose();
        this.peasant.dispose();
        this.crossBowMan.dispose();
        this.monk.dispose();
        this.spearMan.dispose();
        this.rouge.dispose();
        this.sniper.dispose();
        this.mage.dispose();
        this.peasantDead.dispose();
        this.crossBowManDead.dispose();
        this.monkDead.dispose();
        this.spearManDead.dispose();
        this.rougeDead.dispose();
        this.sniperDead.dispose();
        this.mageDead.dispose();
        this.shield.dispose();
        this.winged.dispose();
        this.arrow.dispose();
        this.maxHP.dispose();
        this.hp.dispose();
        this.mp.dispose();
        music.dispose();
    }

    HeroBase getRandomHero(int choice, int x, int y) {
        HeroBase base = null;
        switch (choice) {
            case 0:
                base = new Monk(getName(), x, y);
                break;
            case 1:
            case 2:
                base = new Pikeman(getName(), x, y);
                break;
            case 3:
            case 4:
                base = new Crossbowman(getName(), x, y);
                break;
            case 5:
                base = new Apprentice(getName(), x, y);
                break;
            case 6:
                base = new Wizzard(getName(), x, y);
                break;
            case 7:
            case 8:
                base = new Rogue(getName(), x, y);
                break;
            case 9:
            case 10:
                base = new Sniper(getName(), x, y);
                break;
            default:
        }
        ;
        return base;
    }

    String getName() {
        return Names.values()[new Random().nextInt(Names.values().length - 1)].toString();
    }

    ArrayList<HeroBase> teemSteps(ArrayList<HeroBase> order) throws InterruptedException {
        for (HeroBase hero : order) {
            if (lightSide.contains(hero)) hero.step(darkSide, lightSide);
            else hero.step(lightSide, darkSide);

        }
        return order;
    }

    boolean teamFall(ArrayList<HeroBase> team) {
        for (HeroBase heroBase : team) {
            if (heroBase.getLiveStatus()) return false;
        }
        return true;
    }

    void createTeams(int numbers) {
        for (int i = 0; i < numbers; i++) {
            darkSide.add(getRandomHero(random.nextInt(5, 11), i + 1, numbers));
            lightSide.add(getRandomHero(random.nextInt(6), i + 1, 1));
            darkSide.get(darkSide.size() - 1).changeStatus("L");
            lightSide.get(lightSide.size() - 1).changeStatus("R");
        }
    }

}
