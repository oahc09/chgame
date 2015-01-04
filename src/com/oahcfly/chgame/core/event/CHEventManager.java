
package com.oahcfly.chgame.core.event;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 
 * <pre>
 * 事件管理中心
 * 
 * date: 2015-1-3
 * </pre>
 * @author caohao
 */
public class CHEventManager {
    private static CHEventManager instance = new CHEventManager();

    public static CHEventManager getInstance() {
        return instance;
    }

    private HashMap<String, Actor> eventHashMap = new HashMap<String, Actor>();

    /**
     * 
     * <pre>
     * actor注册事件
     * 
     * date: 2015-1-3
     * </pre>
     * @author caohao
     * @param eventType 事件类型
     * @param actor 事件作用对象
     */
    public void registerEvent(String eventType, Actor actor) {
        eventHashMap.put(eventType, actor);
    }

    /**
     * 
     * <pre>
     * actor取消事件
     * 
     * date: 2015-1-3
     * </pre>
     * @author caohao
     * @param eventType
     * @param actor
     */
    public void unRegisterEvent(String eventType, Actor actor) {
        eventHashMap.put(eventType, actor);
    }

    /**
     * 
     * <pre>
     * 处理事件
     * 
     * date: 2015-1-3
     * </pre>
     * @author caohao
     * @param event
     */
    public final void handleEvent(CHEvent event) {
        Actor actor = eventHashMap.get(event.getEventType());
        if (actor != null) {
            // 发送出事件
            actor.fire(event);
        }
    }
}
