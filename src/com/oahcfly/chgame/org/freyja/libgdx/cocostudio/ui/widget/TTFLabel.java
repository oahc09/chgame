
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.util.FontUtil;

/**
 * 让Label支持TTF,使用ttf后Label的font不会发生变化,每次修改Text的时候重新创建font
 * [如何没有设置ttf，就使用系统默认字体]
 * @author i see
 * 
 */
public class TTFLabel extends Label {

    public TTFLabel(CharSequence text, TTFLabelStyle ttfLabelStyle) {
        super(text, ttfLabelStyle);
    }

    @Override
    public void setText(CharSequence newText) {
        setText(newText, false);
    }

    /**
     * 
     * <pre>
     * TODO
     * 
     * date: 2015-1-5
     * </pre>
     * @author caohao
     * @param newText
     * @param useSysFont 是否调用系统字体
     */
    public void setText(CharSequence newText, boolean useSysFont) {
        if (textEquals(newText))
            return;

        TTFLabelStyle style = (TTFLabelStyle)getStyle();
        if (style.getFontFileHandle() != null) {
            style.font = createFont(style, "" + newText);
        } else {
            // 使用系统自带字体
            style.font = useSysFont ? CHGame.getInstance().getInternationalGenerator().appendToFont(newText.toString())
                    : CHGame.getInstance().getDefaultBitmapFont();
        }
        super.setStyle(style);
        super.setText(newText);
    }

    public void setText(CharSequence newText, BitmapFont font) {
        LabelStyle style = getStyle();
        style.font = font;

        super.setStyle(style);
        super.setText(newText);
    }

    //    public int labelAlign;
    //
    //    public int lineAlign;
    //
    //    @Override
    //    public void setAlignment(int labelAlign, int lineAlign) {
    //        this.labelAlign = labelAlign;
    //        this.lineAlign = lineAlign;
    //        super.setAlignment(labelAlign, lineAlign);
    //    }

    //    @Override
    //    public void setStyle(LabelStyle style) {
    //        style.font = createFont((TTFLabelStyle)style, "" + getText());
    //
    //        super.setStyle(style);
    //    }

    BitmapFont createFont(TTFLabelStyle ttfStyle, String text) {
        return FontUtil.createFont(ttfStyle.getFontFileHandle(), text, ttfStyle.getFontSize());
    }
}
