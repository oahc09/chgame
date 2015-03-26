
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

/**
 * 
 * <pre>
 * 重复执行某个action
 * 
 * date: 2015-1-23
 * </pre>
 * @author caohao
 */
public class CHScheduleAction extends RepeatAction {

    private String name;

    public CHScheduleAction(float intervalSeconds, int repeatCount, Action runnableAction) {
        setCount(repeatCount);
        setAction(Actions.delay(intervalSeconds, runnableAction));
        setName("");
    }

    public CHScheduleAction(String name, float intervalSeconds, int repeatCount, Action runnableAction) {
        setCount(repeatCount);
        setAction(Actions.delay(intervalSeconds, runnableAction));
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(float duration) {
        DelayAction delayAction = (DelayAction)getAction();
        delayAction.setDuration(duration);
    }

}
