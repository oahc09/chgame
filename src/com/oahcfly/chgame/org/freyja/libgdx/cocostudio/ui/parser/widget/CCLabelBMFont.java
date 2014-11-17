
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.WidgetParser;

public class CCLabelBMFont extends WidgetParser {

    @Override
    public String getClassName() {
        return "LabelBMFont";
    }

    @Override
    public Actor parse(CocoStudioUIEditor editor, CCWidget widget, CCOption option) {
        BitmapFont font = null;
        if (editor.getBitmapFonts() != null) {
            font = editor.getBitmapFonts().get(option.getFileNameData().getPath());
        } else {//备用创建字体方式
            font = new BitmapFont(Gdx.files.internal(editor.getDirName() + option.getFileNameData().getPath()));
        }

        if (font == null) {
            font = new BitmapFont();
        }
        
        Color textColor = new Color(option.getColorR() / 255.0f, option.getColorG() / 255.0f,
                option.getColorB() / 255.0f, option.getOpacity() / 255.0f);
        LabelStyle style = new LabelStyle(font, textColor);
        Label label = new Label(option.getText(), style);
        return label;
    }

}
