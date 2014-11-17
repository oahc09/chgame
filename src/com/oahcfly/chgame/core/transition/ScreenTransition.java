
package com.oahcfly.chgame.core.transition;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.oahcfly.chgame.core.CHGame;
import com.oahcfly.chgame.core.CHScreen;

/**
 * 
 * <pre>
 * screen切换动画
 * 
 * date: 2014-7-27
 * </pre>
 * @author caohao
 */
public abstract class ScreenTransition {

    public abstract void doTransition();

    private CHScreen fromScreen;

    private CHScreen toScreen;

    public void transition(CHScreen fromScreen, CHScreen toScreen) {
        this.fromScreen = fromScreen;
        this.toScreen = toScreen;
        Group oldGroup = fromScreen.getStage().getRoot();
        oldGroup.setOrigin(fromScreen.getStage().getWidth() / 2, fromScreen.getStage().getHeight() / 2);

        doTransition();
    }

    public CHScreen getOldScreen() {
        return fromScreen;
    }

    public CHScreen getNewScreen() {
        return toScreen;
    }

    public void toNewScreen() {
        CHGame.getInstance().setScreen(toScreen);
    }
}
