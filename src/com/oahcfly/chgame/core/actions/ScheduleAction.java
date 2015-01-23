
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

/**
 * 
 * <pre>
 * 延迟n秒后重复执行某个action
 * 
 * date: 2015-1-23
 * </pre>
 * @author caohao
 */
public class ScheduleAction extends RepeatAction {

    private String name;

    public ScheduleAction(float duration, Action runnableAction) {
        setCount(-1);
        setAction(Actions.delay(duration, runnableAction));
        setName("");
    }

    public ScheduleAction(String name, float duration, Action runnableAction) {
        setCount(-1);
        setAction(Actions.delay(duration, runnableAction));
        setName(name);
    }

    /**
     * 
     * <pre>
     * 修改等待时间
     * 
     * date: 2015-1-23
     * </pre>
     * @author caohao
     * @param duration
     */
    public void setDuration(float duration) {
        DelayAction delayAction = (DelayAction)getAction();
        delayAction.setDuration(duration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
