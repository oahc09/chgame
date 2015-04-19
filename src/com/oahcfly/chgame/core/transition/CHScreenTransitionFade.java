
package com.oahcfly.chgame.core.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class CHScreenTransitionFade extends AbstractScreenTransition {

    public CHScreenTransitionFade(float duration) {
        super(duration);
        // TODO Auto-generated constructor stub
    }
 

    @Override
    public void render(SpriteBatch batch, Texture curScreen, Texture nextScreen, float progress) {

        float w = curScreen.getWidth();
        float h = curScreen.getHeight();
        float alpha = Interpolation.fade.apply(progress);
        // 清屏
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setColor(Color.WHITE);
        
        batch.begin();
        // 绘制当前场景
        batch.setColor(1, 1, 1, 1);
        batch.draw(curScreen, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0, curScreen.getWidth(), curScreen.getHeight(), false, true);

        // 绘制下个场景，透明度不断变化
        batch.setColor(1, 1, 1, alpha);
        batch.draw(nextScreen, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0, nextScreen.getWidth(), nextScreen.getHeight(), false,
                true);
        batch.end();

    }

}
