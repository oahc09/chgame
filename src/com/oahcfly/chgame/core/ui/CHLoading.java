
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.mvc.CHGame;

public class CHLoading {
    CHAniamtionPlayer chAniamtionPlayer;

    Table parentTable;

    public CHLoading() {
        parentTable = new Table();
        parentTable.setName("loading_table");
        parentTable.setSize(CHGame.getInstance().gameWidth, CHGame.getInstance().gameHeight);
        parentTable.setColor(Color.GRAY);

        // 背景半透明
        Pixmap pixmap = new Pixmap(CHGame.getInstance().gameWidth, CHGame.getInstance().gameHeight, Format.RGBA8888);
        pixmap.setColor(0f, 0f, 0f, 0.5f);
        pixmap.fill();
        parentTable.setBackground(new TextureRegionDrawable(new TextureRegion(getTexture(pixmap))));
        pixmap.dispose();
        // 背景半透明

        Array<? extends TextureRegion> keyFrames = CHGame.getInstance().getLoadingKeyFrames();
        chAniamtionPlayer = new CHAniamtionPlayer(0.12f, keyFrames, PlayMode.LOOP);
        chAniamtionPlayer.setName("actor_loading");

        // 添加loading组件
       // Image starImage = CHGame.getInstance().getImage("screen/big_star.png");
     
        parentTable.add(chAniamtionPlayer);
        chAniamtionPlayer.play();
    }

    public void show() {
        CHGame.getInstance().getScreen().addActor(parentTable);
        parentTable.setZIndex(Integer.MAX_VALUE);
    }

    private Texture getTexture(Pixmap pixmap) {
        Texture texture = new Texture(pixmap);
        // 抗锯齿
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        return texture;
    }

}
