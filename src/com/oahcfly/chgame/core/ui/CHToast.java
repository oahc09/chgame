
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.util.FontUtil;

/**
 * 
 * <pre>
 * 文本Toast
 * 
 * date: 2014-12-22
 * </pre>
 * @author caohao
 */
public class CHToast {

    Label label;

    private float textWidth, textHeight;

    /**
     * 
     * @param ttfName ttf的key值
     * @param text 文本
     * @param fontColor 字体颜色
     * @param fontSize  字体大小
     */
    public CHToast(String ttfName, String text, Color fontColor, int fontSize) {
        BitmapFont bitmapFont = FontUtil.createFont(CHGame.getInstance().getTTFMap().get(ttfName), text, fontSize);
        LabelStyle style = new LabelStyle(bitmapFont, Color.BLACK);
        style.background = new TextureRegionDrawable(
                new TextureRegion(CHGame.getInstance().getTexture("ui/tip_bg.png")));
        label = new Label(" " + text + " ", style);
        label.setHeight(style.background.getMinHeight());
        textWidth = bitmapFont.getBounds(text).width;
        textHeight = bitmapFont.getBounds(text).height;
        label.setPosition(CHGame.getInstance().gameWidth / 2 - textWidth / 2, CHGame.getInstance().gameHeight / 4
                - textHeight / 2);
    }

    public void setCenterPosition(float centerX, float centerY) {
        label.setPosition(centerX - textWidth / 2, centerY - textHeight / 2);
    }

    public void show() {
        Action delayedAction = Actions.run(new Runnable() {

            @Override
            public void run() {
                label.remove();
            }
        });
        label.addAction(Actions.delay(1.5f, delayedAction));
        CHGame.getInstance().getScreen().addActor(label);
    }
}
