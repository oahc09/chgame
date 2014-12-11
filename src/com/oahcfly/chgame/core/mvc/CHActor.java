
package com.oahcfly.chgame.core.mvc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 
 * <pre>
 * 二次封装的actor
 * 
 * date: 2014-12-11
 * </pre>
 * @author caohao
 */
public abstract class CHActor extends Actor {
    private Texture bgTexture;
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);

        float x = getX();
        float y = getY();
        float scaleX = getScaleX();
        float scaleY = getScaleY();

        if (bgTexture != null) {
            batch.draw(bgTexture, x, y, getOriginX(), getOriginY(), getWidth(), getHeight(), scaleX, scaleY,
                    getRotation(), 0, 0, (int)getWidth(), (int)getHeight(), false, false);
        }
    }


    public void setBgTexture(Texture bgTexture) {
        this.bgTexture = bgTexture;
        setBounds(0, 0, bgTexture.getWidth(), bgTexture.getHeight());
    }

}
