
package com.oahcfly.chgame.test;

import java.util.Random;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.test.screen.AStarScreen;
import com.oahcfly.chgame.test.screen.MyLoadingScreen;

public class TestGame extends CHGame {

    @Override
    public void init() {
        setGameWidthAndHeight(480, 800);
        setFPS(true);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        addScreen(new AStarScreen());
        setScreen(new MyLoadingScreen());

        //new DatabaseTest();

   
    }

    private static Random ran = new Random();

    private final static int delta = 0x9fa5 - 0x4e00 + 1;

    public static String getRandomHan() {
        char c = (char)(0x4e00 + ran.nextInt(delta));
        System.out.println("getRandomHan:" + c);
        return String.valueOf(c);
    }

    @Override
    public void savaDataBeforeExit() {
        FreeTypeFontGenerator fontGenerator;

    }
}
