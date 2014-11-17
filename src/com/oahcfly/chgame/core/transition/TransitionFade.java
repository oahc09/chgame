
package com.oahcfly.chgame.core.transition;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class TransitionFade extends ScreenTransition {

    @Override
    public void doTransition() {
        Group oldGroup = getOldScreen().getStage().getRoot();
        oldGroup.addAction(
                Actions.sequence(Actions.fadeOut(1f),Actions.run(new Runnable() {
                    
                    @Override
                    public void run() {
                        toNewScreen();
                    }
                })));
    }

}
