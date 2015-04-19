
package com.oahcfly.chgame.core.transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IScreenTransition {
    public float getDuration();

    /**
     * 
     * <pre>
     * 绘制
     * 
     * date: 2015-4-18
     * </pre>
     * @author caohao
     * @param batch
     * @param curScreen 当前场景的纹理
     * @param nextScreen 下个场景的纹理
     * @param progress 当前的进度 （0~1）
     */
    public void render(SpriteBatch batch, Texture curScreen, Texture nextScreen, float progress);
}
