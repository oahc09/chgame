
package com.oahcfly.chgame.core.listener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * 
 * <pre>
 * 输入事件二次封装的接口，支持缩放和淡入淡出等效果
 * 
 * date: 2014-12-3
 * </pre>
 * @author caohao
 */
public class CHClickListener extends ClickListener {

    public enum CLICKTYPE {
        SCALE, FADE
    }

    private CLICKTYPE clickType = CLICKTYPE.SCALE;

    public CHClickListener() {
    }

    public CHClickListener(CLICKTYPE clickType) {
        this.clickType = clickType;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer > 0) {
            // 屏蔽2指同时点击的情况
            return false;
        }
        switch (clickType) {
            case SCALE:
                event.getListenerActor().setScale(1.1f);
                break;
            case FADE:
                Color color = event.getListenerActor().getColor();
                color.a = 0.5f;
                event.getListenerActor().setColor(color);
                break;
            default:
                break;
        }
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        if (pointer > 0) {
            // 屏蔽2指同时点击的情况
            return;
        }
        switch (clickType) {
            case SCALE:
                event.getListenerActor().setScale(1f);
                break;
            case FADE:
                Color color = event.getListenerActor().getColor();
                color.a = 1f;
                event.getListenerActor().setColor(color);
                break;
            default:
                break;
        }
    }

}
