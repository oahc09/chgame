
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Scaling;

public abstract class CHImage extends Image implements Poolable {

    public void setTexture(Texture texture) {
        // 抗锯齿
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        // 重写Image内部实现
        setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        setScaling(Scaling.stretch);
        setAlign(Align.center);
        setSize(getPrefWidth(), getPrefHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        beforeDrawSprite(batch,parentAlpha);
        super.draw(batch, parentAlpha);
        afterDrawSprite(batch,parentAlpha);
    }

    public abstract void beforeDrawSprite(Batch batch, float parentAlpha);
    
    public abstract void afterDrawSprite(Batch batch, float parentAlpha);
    
}
