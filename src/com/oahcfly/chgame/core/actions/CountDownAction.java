
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
 * 
 * date: 2014-12-29
 * </pre>
 * @author caohao
 */
public class CountDownAction extends TemporalAction {

    private int curPosition;

    private TextureRegion curTextureRegion;

    private Array<TextureRegion> textureRegions;

    public CountDownAction() {
    }

    public CountDownAction(float duration) {
        super(duration);
    }

    public CountDownAction(float duration, Array<Texture> textureArray) {
        super(duration);
        textureRegions = new Array<TextureRegion>();
        for (Texture texture : textureArray) {
            textureRegions.add(new TextureRegion(texture));
        }
    }

    public CountDownAction(float duration, TextureRegion[] textureRegionArray) {
        super(duration);
        textureRegions = new Array<TextureRegion>();
        for (TextureRegion textureRegion : textureRegionArray) {
            textureRegions.add(textureRegion);
        }
    }

    private Image stageImage;

    @Override
    protected void update(float percent) {
        curPosition = (int)(percent * (textureRegions.size - 1));

        curTextureRegion = textureRegions.get(curPosition);
        if (actor instanceof Group) {
            if (stageImage == null) {
                stageImage = new Image(new TextureRegionDrawable(curTextureRegion));
                stageImage.setPosition(CHGame.getInstance().gameWidth / 2, CHGame.getInstance().gameHeight / 2,
                        Align.center);
                ((Group)actor).addActor(stageImage);
            } else {
                stageImage.setDrawable(new TextureRegionDrawable(curTextureRegion));
            }
        } else if (actor instanceof Image) {
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
    public static CountDownAction obtain(float duration, Array<Texture> textureArray) {
        Pool<CountDownAction> pool = Pools.get(CountDownAction.class);
        CountDownAction t = pool.obtain();
        t.setPool(pool);
        t.setDuration(duration);
        Array<TextureRegion> textureRegions = new Array<TextureRegion>();
        for (Texture texture : textureArray) {
            textureRegions.add(new TextureRegion(texture));
        }
        t.setTextureRegions(textureRegions);
        return t;
    }

    public static CountDownAction obtain(float duration, TextureRegion[] textureRegionArray) {
        Pool<CountDownAction> pool = Pools.get(CountDownAction.class);
        CountDownAction t = pool.obtain();
        t.setPool(pool);
        t.setDuration(duration);
        Array<TextureRegion> textureRegions = new Array<TextureRegion>();
        for (TextureRegion textureRegion : textureRegionArray) {
            textureRegions.add(textureRegion);
        }
        t.setTextureRegions(textureRegions);
        return t;
    }
}
