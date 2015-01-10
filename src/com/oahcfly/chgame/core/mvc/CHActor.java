
package com.oahcfly.chgame.core.mvc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

/**
 * 
 * <pre>
 * 二次封装的actor
 * 
 * date: 2014-12-11
 * </pre>
 * @author caohao
 */
public class CHActor extends Actor implements Poolable {

    private Texture bgTexture;

    public CHActor() {
    }

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
        if (bgTexture != null) {
            setSize(bgTexture.getWidth(), bgTexture.getHeight());
        }
    }

    /**
     * 
     * <pre>
     * 使用缓存池
     * 
     * date: 2015-1-3
     * </pre>
     * @author caohao
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends CHActor> T obtain(Class<T> type) {
        Pool<CHActor> pool = (Pool<CHActor>)Pools.get(type);
        CHActor actor = pool.obtain();
        actor.setBgTexture(null);
        return (T)actor;
    }

    @Override
    public void reset() {
        this.bgTexture = null;
        setScale(1);
        setRotation(0);
        clear();
        setUserObject(null);
        this.setColor(new Color(1, 1, 1, 1));
        setStage(null);
        setParent(null);
    }

    public Texture getBgTexture() {
        return bgTexture;
    }

    @Override
    public boolean remove() {
        boolean remove = super.remove();
        if (remove) {
            Pools.free(this);
        }
        return remove;
    }

}
