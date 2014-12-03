
package com.oahcfly.chgame.core.ui;

import java.util.regex.Pattern;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * 彩色Label
 * 文本例子 String text="#422005Complete goals in\n#FFFFFF100 #422005moves"
 * ColorLabel label=new ColorLabel(text,new LabelStyle());
 * 
 */
public class ColorLabel extends Label {
    private BitmapFont font;

    @SuppressWarnings("unused")
    private TextBounds bounds;

    private Array<Array<ColorFont>> textss;

    public ColorLabel(CharSequence text, LabelStyle style) {
        super(text, style);
        textss = new Array<Array<ColorFont>>();
        font = style.font;
        setStyle(style);
        cpuText(text.toString());
    }

    private void cpuText(String text) {
        textss.clear();
        float maxWidth = 0;
        bounds = font.getMultiLineBounds(text);
        float maxHeight = -font.getDescent() + font.getXHeight();
        String tn[] = text.split("\n");// 换行
        for (int n = 0; n < tn.length; n++) {
            String ts[] = tn[n].split("#");
            float gbx = 0;
            Array<ColorFont> texts = new Array<ColorFont>();
            for (int i = 1; i < ts.length; i++) {
                String colorText = ts[i].substring(0, 6);
                if (checkNum(colorText)) {
                    Color color = Color.valueOf(colorText + "FF");
                    ColorFont colorfont = new ColorFont(ts[i].substring(6), color, gbx, maxHeight);
                    gbx += font.getMultiLineBounds(ts[i].substring(6)).width * getFontScaleY();
                    texts.add(colorfont);
                }
            }
            if (gbx > maxWidth) {
                maxWidth = gbx;
            }
            textss.add(texts);
            maxHeight -= font.getLineHeight();
        }
        setWidth(maxWidth * getFontScaleY());
        setHeight(-maxHeight * getFontScaleY());
    }

    /**
     * 判断是否为6位数16进制
     * 
     * @param aNumber
     * @return
     */
    public boolean checkNum(String aNumber) {
        String regString = "[a-f0-9A-F]{6}";
        if (Pattern.matches(regString, aNumber)) {
            return true;
        }
        return false;
    }

    public void setText(CharSequence newText) {
        super.setText(newText);
        cpuText(newText.toString());
    }

    public void setFontScale(float s) {
        super.setFontScale(s);
        bounds = font.getMultiLineBounds(getText());
        cpuText(getText().toString());
    }

    public float getPrefWidth() {
        return getWidth();
    }

    public float getPrefHeight() {
        return getHeight();
    }

    public void draw(Batch batch, float parentAlpha) {
        float fs = font.getScaleY();
        font.setScale(getFontScaleY());
        for (int i = textss.size - 1; i > -1; i--) {
            for (ColorFont colo : textss.get(i)) {
                font.setColor(colo.color);
                font.draw(batch, colo.text, getX() + colo.x, getY() + colo.y);
            }
        }
        font.setScale(fs);
    }

    public class ColorFont {
        String text;

        Color color;

        float x, y;

        public ColorFont(String text, Color color, float x, float y) {
            this.text = text;
            this.color = color;
            this.x = x;
            this.y = y;
        }
    }
}
