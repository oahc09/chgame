
package com.oahcfly.chgame.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.test.screen.MyLoadingScreen;

public class TestGame extends CHGame {

    @Override
    public void init() {
        setGameWidthAndHeight(800, 480);
        setFPS(true);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        setScreen(new MyLoadingScreen());

    }
}
