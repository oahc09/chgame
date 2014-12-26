
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.mvc.CHGame;

/**
 * 
 * <pre>
 * loading界面[5秒超时自动消失]
 * 由table+帧动画实现
 * date: 2014-12-23
 * </pre>
 * @author caohao
 */
public class CHLoading {
    private CHAniamtionPlayer chAniamtionPlayer;

    private Table parentTable;

    public CHLoading() {
        parentTable = new Table();
        parentTable.setName("loading_table");
        parentTable.setSize(CHGame.getInstance().gameWidth, CHGame.getInstance().gameHeight);
        parentTable.setColor(Color.GRAY);

        // 背景半透明
        Pixmap pixmap = new Pixmap(CHGame.getInstance().gameWidth, CHGame.getInstance().gameHeight, Format.RGBA8888);
        pixmap.setColor(0f, 0f, 0f, 0.5f);
        pixmap.fill();
        parentTable
                .setBackground(new TextureRegionDrawable(new TextureRegion(CHGame.getInstance().getTexture(pixmap))));
        pixmap.dispose();
        // 背景半透明

        Array<? extends TextureRegion> keyFrames = CHGame.getInstance().getLoadingKeyFrames();
        chAniamtionPlayer = new CHAniamtionPlayer(0.12f, keyFrames, PlayMode.LOOP);
        chAniamtionPlayer.setName("actor_loading");

        // 添加loading组件

        parentTable.add(chAniamtionPlayer);
    }

    public void show() {
        chAniamtionPlayer.play();
        parentTable.setVisible(true);
        parentTable.setTouchable(Touchable.enabled);

        if (parentTable.getParent() == null) {
            System.out.println("add to parent");
            CHGame.getInstance().getScreen().addActor(parentTable);
        }
        parentTable.setZIndex(Integer.MAX_VALUE);
        parentTable.addAction(Actions.delay(5f, Actions.run(new Runnable() {

            @Override
            public void run() {
                // 5s超时自动消失
                dismiss();
            }
        })));
    }

    public void dismiss() {
        parentTable.clearActions();
        parentTable.clearListeners();
        chAniamtionPlayer.stop();
        parentTable.setVisible(false);
        parentTable.setTouchable(Touchable.disabled);
    }

}
