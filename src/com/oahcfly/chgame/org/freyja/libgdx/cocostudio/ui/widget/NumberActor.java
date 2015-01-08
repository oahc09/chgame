
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oahcfly.chgame.core.mvc.CHActor;

/**
 * 
 * <pre>
 * 绘制数字：123456890
 * 
 * date: 2015-1-8
 * </pre>
 * @author caohao
 */
public class NumberActor extends CHActor {
    private TextureRegion region;

    public NumberActor() {
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        setSize(region.getRegionWidth(), region.getRegionHeight());
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        super.reset();
        this.region = null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO Auto-generated method stub
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        if (region != null) {
            batch.draw(region, x, y);
        }
    }

}
