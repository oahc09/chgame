
package com.oahcfly.chgame.core.event;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * 
 * <pre>
 * 事件：含有事件类型，事件携带的数据
 * 
 * date: 2014-12-11
 * </pre>
 * @author caohao
 */
public class CHEvent extends Event {
    private HashMap<String, String> data;

    private String eventType;

    public CHEvent(String eventType, HashMap<String, String> dataMap) {
        data = dataMap;
        this.eventType = eventType;
    }

    @Override
    public void reset() {
        super.reset();
        data.clear();
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public String toString() {
        return "type:" + getEventType() + ",data:" + data.toString();
    }

    public String getEventType() {
        return eventType;
    }

}
