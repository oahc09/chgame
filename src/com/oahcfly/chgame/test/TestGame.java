
package com.oahcfly.chgame.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.oahcfly.chgame.core.CHGame;
import com.oahcfly.chgame.test.screen.FirstScreen;

public class TestGame extends CHGame {

    @Override
    public void init() {
        // TODO Auto-generated method stub
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        setScreen(new FirstScreen());
        

    }

}
