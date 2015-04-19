
package com.oahcfly.chgame.core.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class CHScreenTransitionSlide extends AbstractScreenTransition {
    public static final int TO_LEFT = 1;

    public static final int TO_RIGHT = 2;

    public static final int TO_UP = 3;

    public static final int TO_BOTTOM = 4;

    private boolean slideOut;

    private int direction;

    private Interpolation easing;

    /**
     * 
     * @param duration
     * @param direction 方向
     * @param slideOut 当前场景是否滑动出去
     * @param easing
     */
    public CHScreenTransitionSlide(float duration, int direction, boolean slideOut, Interpolation easing) {
        super(duration);
        this.direction = direction;
        this.slideOut = slideOut;
        this.easing = easing;
    }

    @Override
    public void render(SpriteBatch batch, Texture curScreen, Texture nextScreen, float progress) {
        float w = curScreen.getWidth();
        float h = curScreen.getHeight();
        float x = 0;
        float y = 0;
        if (easing != null)
            progress = easing.apply(progress);
        // calculate position offset
        switch (direction) {
            case TO_LEFT:
                x = -w * progress;
                if (!slideOut)
                    x += w;
                break;
            case TO_RIGHT:
                x = w * progress;
                if (!slideOut)
                    x -= w;
                break;
            case TO_UP:
                y = h * progress;
                if (!slideOut)
                    y -= h;
                break;
            case TO_BOTTOM:
                y = -h * progress;
                if (!slideOut)
                    y += h;
                break;
        }
        // drawing order depends on slide type ('in' or 'out')
        Texture texBottom = slideOut ? nextScreen : curScreen;
        Texture texTop = slideOut ? curScreen : nextScreen;
        // finally, draw both screens
        //        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setColor(Color.WHITE);

        batch.begin();

        batch.draw(texBottom, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0, curScreen.getWidth(), curScreen.getHeight(), false, true);
        batch.draw(texTop, x, y, 0, 0, w, h, 1, 1, 0, 0, 0, nextScreen.getWidth(), nextScreen.getHeight(), false, true);

        batch.end();

    }
}
