
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.GroupParser;

/**
 * @tip libgdx的CheckBox只有选中和未选中两个状态的图片显示
 * @author i see
 * 
 */
public class CCCheckBox extends GroupParser {

    @Override
    public String getClassName() {
        return "CheckBox";
    }

    @Override
    public Actor parse(CocoStudioUIEditor editor, CCWidget widget, CCOption option) {
        CheckBoxStyle style = new CheckBoxStyle(null, null, CHGame.getInstance().getDefaultBitmapFont(), Color.BLACK);

        if (option.getBackGroundBoxData() != null) {// 选中图片

            style.checkboxOff = editor.findDrawable(option, option.getBackGroundBoxData().getPath());
        }
        if (option.getFrontCrossData() != null) {// 没选中图片
            style.checkboxOn = editor.findDrawable(option, option.getFrontCrossData().getPath());
        }
        CheckBox checkBox = new CheckBox("", style);
        checkBox.setChecked(option.isSelectedState());
        return checkBox;
    }
}
