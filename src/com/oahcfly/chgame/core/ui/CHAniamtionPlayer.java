
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * <pre>
 * 帧动画播放器
 * 
 * date: 2014-11-30
 * </pre>
 * @author caohao
 */
public class CHAniamtionPlayer extends Actor {

    private Animation animation;

    private float stateTime = 0;

    // 动画结束要执行的
    private Runnable endRunnable;

    // 动画结束是否移除
    private boolean endRemove = false;

    private boolean isPlaying = false;

    public CHAniamtionPlayer(float w, float h, Animation animation) {
        setSize(w, h);
        this.animation = animation;
        this.setTouchable(Touchable.disabled);
    }

    public CHAniamtionPlayer(float w, float h, float frameDuration, Array<? extends TextureRegion> keyFrames) {
        this(w, h, new Animation(frameDuration, keyFrames));
    }

    public CHAniamtionPlayer(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        this(keyFrames.get(0).getRegionWidth(), keyFrames.get(0).getRegionHeight(), new Animation(frameDuration,
                keyFrames, playMode));
    }

    public CHAniamtionPlayer(TextureRegion img, int numx, int numy, float frameDuration, PlayMode playMode) {
        int tileWidth = img.getRegionWidth() / numx;
        int tileHeight = img.getRegionHeight() / numy;
        TextureRegion[][] imgs = img.split(tileWidth, tileHeight);
        TextureRegion[] keyFrames = imgs[0];
        setSize(keyFrames[0].getRegionWidth(), keyFrames[0].getRegionHeight());
        animation = new Animation(frameDuration, keyFrames);
        animation.setPlayMode(playMode);
    }

    public void play() {
        stateTime = 0;
        isPlaying = true;
    }

    public void pause() {
        isPlaying = false;
    }

    public void resume() {
        isPlaying = true;
    }

    public void stop() {
        isPlaying = false;
        stateTime = 0;
    }

    public boolean remove() {
        stop();
        return super.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isPlaying) {
            stateTime += Gdx.graphics.getDeltaTime();
        }

        if (animation != null) {
            TextureRegion region = animation.getKeyFrame(stateTime);
            float regionW = region.getRegionWidth();
            float regionH = region.getRegionHeight();
            float selfW = getWidth();
            float selfH = getHeight();
            if (selfW < regionW) {
                regionW = selfW;
            }

            if (selfH < regionH) {
                regionH = selfH;
            }

            float centerX = getX() + (selfW - regionW) / 2;
            float centerY = getY() + (selfH - regionH) / 2;
            batch.draw(region, centerX, centerY, regionW, regionH);
            if (animation.isAnimationFinished(stateTime)) {
                stop();
                if (endRunnable != null) {
                    endRunnable.run();
                }
                if (endRemove) {
                    this.remove();
                }
            }

        }
    }
}
