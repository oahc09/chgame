
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.oahcfly.chgame.core.helper.CHFontHelper;
import com.oahcfly.chgame.core.mvc.CHGame;

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

    private Label label;

    private float textWidth, textHeight;

    /**
     * 
     * @param text 文本
     * @param fontColor 颜色
     */
    public CHToast(String text, Color fontColor) {
        BitmapFont bitmapFont = CHFontHelper.getInstance().loadSysFont(28, text);
        init(bitmapFont, text, fontColor);
    }

    /**
     * 
     * @param ttfName ttf的key值
     * @param text 文本
     * @param fontColor 字体颜色
     * @param fontSize  字体大小
     */
    public CHToast(String ttfName, String text, Color fontColor, int fontSize) {
        BitmapFont bitmapFont;
        if (ttfName == null) {
            bitmapFont = CHFontHelper.getInstance().loadSysFont(28, text);
        } else {
            bitmapFont = CHFontHelper.getInstance().loadTtfFont(CHGame.getInstance().getTTFMap().get(ttfName),
                    fontSize, text);
        }

        init(bitmapFont, text, fontColor);
    }

    private void init(BitmapFont bitmapFont, String text, Color color) {
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        LabelStyle style = new LabelStyle(bitmapFont, color);
        FileHandle imgFileHandle = Gdx.files.classpath("com/oahcfly/chgame/res/tip_bg.png");
        style.background = new TextureRegionDrawable(new TextureRegion(new Texture(imgFileHandle)));
        label = new Label(" " + text + " ", style);
        label.setWrap(true);
        label.setWidth(400);
        label.setHeight(style.background.getMinHeight() + glyphLayout.width);

        label.setAlignment(com.badlogic.gdx.utils.Align.center);

        textWidth = glyphLayout.width;
        textHeight = glyphLayout.height;
        label.setPosition(CHGame.getInstance().gameWidth / 2 - label.getWidth() / 2, CHGame.getInstance().gameHeight
                / 2 - label.getHeight() / 2);

        setDelayDuration(1.5f);
        // 点击事件
        label.setTouchable(Touchable.enabled);
        label.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // TODO Auto-generated method stub
                super.touchUp(event, x, y, pointer, button);
                remove();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TODO Auto-generated method stub
                return true;
            }

        });
    }

    private float duration;

    public void setDelayDuration(float duration) {
        this.duration = duration;
    }

    public void setCenterPosition(float centerX, float centerY) {
        label.setPosition(centerX - label.getWidth() / 2, centerY - label.getHeight() / 2);
    }

    public void show() {
        Action delayedAction = Actions.run(new Runnable() {

            @Override
            public void run() {
                remove();
            }
        });
        label.addAction(Actions.delay(duration, delayedAction));
        CHGame.getInstance().getScreen().addActor(label);
    }

    private void remove() {
        TextureRegionDrawable regionDrawable = (TextureRegionDrawable)label.getStyle().background;
        regionDrawable.getRegion().getTexture().dispose();
        label.clear();
        label.remove();
    }

    public float getTextWidth() {
        return textWidth;
    }

    public float getTextHeight() {
        return textHeight;
    }
}
