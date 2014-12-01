
package com.oahcfly.chgame.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class CHClickListener extends InputListener {

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
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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
