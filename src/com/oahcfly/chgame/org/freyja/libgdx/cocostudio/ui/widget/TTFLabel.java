
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.util.FontUtil;

/**
 * 让Label支持TTF,使用ttf后Label的font不会发生变化,每次修改Text的时候重新创建font
 * 
 * @author i see
 * 
 */
public class TTFLabel extends Label {

    public TTFLabel(CharSequence text, TTFLabelStyle ttfLabelStyle) {
        super(text, ttfLabelStyle);
    }

    @Override
    public void setText(CharSequence newText) {
        LabelStyle style = getStyle();
        style.font = createFont((TTFLabelStyle)style, "" + newText);
 
        super.setStyle(style);
        super.setText(newText);
    }

    public int labelAlign;

    public int lineAlign;

    @Override
    public void setAlignment(int labelAlign, int lineAlign) {
        this.labelAlign = labelAlign;
        this.lineAlign = lineAlign;
        super.setAlignment(labelAlign, lineAlign);
    }

    @Override
    public void setStyle(LabelStyle style) {
        style.font = createFont((TTFLabelStyle)style, "" + getText());

        super.setStyle(style);
    }

    BitmapFont createFont(TTFLabelStyle ttfStyle, String text) {
        return FontUtil.createFont(ttfStyle.getFontFileHandle(), text, ttfStyle.getFontSize());
    }
}
