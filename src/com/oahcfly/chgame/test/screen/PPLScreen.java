
package com.oahcfly.chgame.test.screen;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.test.ui.MainUI;
import com.oahcfly.chgame.test.ui.TestUI;

public class PPLScreen extends CHScreen {

    @Override
    public void initScreen() {
        // TODO Auto-generated method stub

        addUI(new MainUI(this));
        addUI(new TestUI(this));

        getUI("TestUI").show();

        Action[] sAction = new Action[10];
        // 使用action实现定时器
        for (int i = 0; i < 10; i++) {
            Action delayedAction = Actions.run(new Runnable() {

                @Override
                public void run() {
                    System.out.println("time:" + (System.currentTimeMillis() / 1000) + ",发射:");
                }
            });
            Action action = Actions.delay(1f, delayedAction);
            sAction[i] = action;
        }
        getStage().addAction(Actions.sequence(sAction));

    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

}
