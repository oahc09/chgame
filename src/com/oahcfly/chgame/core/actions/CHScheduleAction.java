
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction;

/**
 * 
 * <pre>
 * 每间隔多长时间执行某个action
 * 
 * date: 2015-1-23
 * </pre>
 * @author caohao
 */
public class CHScheduleAction extends DelegateAction {

    private String name = "CHScheduleAction";

    private Runnable callbackRunnable;

    public CHScheduleAction(float intervalSeconds, int repeatCount, Action runnableAction) {
        setCount(repeatCount);
        setAction(Actions.delay(intervalSeconds, runnableAction));
        setName(name);
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

    public Runnable getCallbackRunnable() {
        return callbackRunnable;
    }

    public void setCallbackRunnable(Runnable callbackRunnable) {
        this.callbackRunnable = callbackRunnable;
    }

    static public final int FOREVER = -1;

    private int repeatCount, executedCount;

    private boolean finished;

    protected boolean delegate(float delta) {
        if (executedCount == repeatCount)
            return true;
        if (action.act(delta)) {
            if (finished) {
                if (callbackRunnable != null) {
                    callbackRunnable.run();
                }
                return true;
            }
            if (repeatCount > 0)
                executedCount++;
            if (executedCount == repeatCount) {
                if (callbackRunnable != null) {
                    callbackRunnable.run();
                }
                return true;
            }
            if (action != null)
                action.restart();
        }
        return false;
    }

    /** Causes the action to not repeat again. */
    public void finish() {
        finished = true;
    }

    public void restart() {
        super.restart();
        executedCount = 0;
        finished = false;
    }

    /** Sets the number of times to repeat. Can be set to {@link #FOREVER}. */
    public void setCount(int count) {
        this.repeatCount = count;
    }

    public int getCount() {
        return repeatCount;
    }
}
