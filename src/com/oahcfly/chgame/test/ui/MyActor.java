
package com.oahcfly.chgame.test.ui;

import com.oahcfly.chgame.core.mvc.CHActor;
import com.oahcfly.chgame.core.mvc.CHGame;

public class MyActor extends CHActor {

    public MyActor() {

    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

    @Override
    public void act(float delta) {
        // TODO Auto-generated method stub
        super.act(delta);

        if (getX() > CHGame.getInstance().gameWidth) {
            remove();
        }
        if (getY() > CHGame.getInstance().gameHeight) {
            remove();
        }
    }

}
