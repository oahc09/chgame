
package com.oahcfly.chgame.core.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * 
 * <pre>
 * 事件监听器
 * 
 * date: 2014-12-11
 * </pre>
 * @author caohao
 */
public abstract class CHEventListener implements EventListener {

    @Override
    public boolean handle(Event event) {
        if (event instanceof CHEvent) {
            handleEvent((CHEvent)event);
            return true;
        } else {
            Gdx.app.error("CHEventListener", "event error~~"+event.getClass().getName());
        }
        return false;
    }

    /**处理事件*/
    public abstract void handleEvent(CHEvent chEvent);

}
