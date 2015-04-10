
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.oahcfly.chgame.core.mvc.CHGame;

/**
 * 
 * <pre>
 *  倒计时action。10->9->8.....
 *  如，Ready->Go、一系列图片按倒计时显示
 *  
 * date: 2014-12-29
 * </pre>
 * @author caohao
 */
public class CHCountDownAction extends TemporalAction {

    private int curPosition;

    private TextureRegion curTextureRegion;

    private Array<TextureRegion> textureRegions;

    private Runnable callBackRunnable;

    public CHCountDownAction() {
    }

    /**
     * 
     * @param duration 总时长
     */
    public CHCountDownAction(float duration) {
        super(duration);
    }

    public CHCountDownAction(float duration, Array<Texture> textureArray) {
        super(duration);
        textureRegions = new Array<TextureRegion>();
        for (Texture texture : textureArray) {
            textureRegions.add(new TextureRegion(texture));
        }
    }

    public CHCountDownAction(float duration, TextureRegion[] textureRegionArray) {
        super(duration);
        textureRegions = new Array<TextureRegion>();
        for (TextureRegion textureRegion : textureRegionArray) {
            textureRegions.add(textureRegion);
        }
    }

    private Image stageImage;

    @Override
    protected void update(float percent) {
        curPosition = (int)(percent * textureRegions.size);

        if (percent >= 1 && callBackRunnable != null) {
            callBackRunnable.run();
            if (stageImage != null) {
                stageImage.remove();
            }
            if (actor != null) {
                actor.remove();
            }
            return;
        }
        curTextureRegion = textureRegions.get(curPosition);
        if (actor instanceof Group) {
            if (stageImage == null) {
                stageImage = new Image(new TextureRegionDrawable(curTextureRegion));
                stageImage.setPosition(CHGame.getInstance().gameWidth / 2, CHGame.getInstance().gameHeight / 2,
                        Align.center);
                ((Group)actor).addActor(stageImage);
            } else {
                stageImage.setSize(curTextureRegion.getRegionWidth(), curTextureRegion.getRegionHeight());
                stageImage.setDrawable(new TextureRegionDrawable(curTextureRegion));
            }
        } else if (actor instanceof Image) {
            actor.setSize(curTextureRegion.getRegionWidth(), curTextureRegion.getRegionHeight());
            ((Image)actor).setDrawable(new TextureRegionDrawable(curTextureRegion));
        }

    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub
        super.restart();
        textureRegions.clear();
        curPosition = 0;
        curTextureRegion = null;
    }

    @Override
    protected void begin() {
        // TODO Auto-generated method stub
        super.begin();
        curPosition = 0;
        curTextureRegion = textureRegions.get(curPosition);
    }

    public void setTextureRegions(Array<TextureRegion> textureRegions) {
        this.textureRegions = textureRegions;
    }

    /**
     * 
     * <pre>
     * Static factory method for CountDownAction . Pools instance.
     * 
     * date: 2014-12-29
     * </pre>
     * @author caohao
     * @param duration
     * @param textureArray
     * @return
     */
    public static CHCountDownAction obtain(float duration, Array<Texture> textureArray) {
        Pool<CHCountDownAction> pool = Pools.get(CHCountDownAction.class);
        CHCountDownAction t = pool.obtain();
        t.setPool(pool);
        t.setDuration(duration);
        Array<TextureRegion> textureRegions = new Array<TextureRegion>();
        for (Texture texture : textureArray) {
            textureRegions.add(new TextureRegion(texture));
        }
        t.setTextureRegions(textureRegions);
        return t;
    }

    public static CHCountDownAction obtain(float duration, TextureRegion[] textureRegionArray) {
        Pool<CHCountDownAction> pool = Pools.get(CHCountDownAction.class);
        CHCountDownAction t = pool.obtain();
        t.setPool(pool);
        t.setDuration(duration);
        Array<TextureRegion> textureRegions = new Array<TextureRegion>();
        for (TextureRegion textureRegion : textureRegionArray) {
            textureRegions.add(textureRegion);
        }
        t.setTextureRegions(textureRegions);
        return t;
    }

    public Runnable getCallBackRunnable() {
        return callBackRunnable;
    }

    public void setCallBackRunnable(Runnable callBackRunnable) {
        this.callBackRunnable = callBackRunnable;
    }
}
