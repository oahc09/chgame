
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.oahcfly.chgame.core.FontHelper;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.WidgetParser;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.TTFLabelStyle;

/**
 * 
 * @author i see
 * 
 */
public class CCTextField extends WidgetParser {

    @Override
    public String getClassName() {
        return "TextField";
    }

    /** 默认文字,为了支持基本字符输入 */
    final String defaultText = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*";

    @Override
    public Actor parse(CocoStudioUIEditor editor, CCWidget widget, CCOption option) {

        final TTFLabelStyle labelStyle = editor.createLabelStyle(option);

        TextFieldStyle style = new TextFieldStyle(labelStyle.font, labelStyle.fontColor, null, null, null);

        // style.font = FontUtil.createFont(
        // labelStyle.getFontFileHandle(), "0123456789",
        // labelStyle.getFontSize());

        //option.getText()
        TextField textField = new TextField("TextField", style) {

            @Override
            public void setText(String text) {
                String sumText = text + getMessageText() + defaultText;

                getStyle().font = FontHelper.createFont(labelStyle.getFontFileHandle(), sumText,
                        labelStyle.getFontSize());

                super.setText(text);
            };

            @Override
            public void setMessageText(String messageText) {

                String sumText = messageText + getText() + defaultText;

                getStyle().font = FontHelper.createFont(labelStyle.getFontFileHandle(), sumText,
                        labelStyle.getFontSize());
                super.setMessageText(messageText);
            };
        };

        textField.setMaxLength(option.getMaxLength());
        textField.setMessageText(option.getPlaceHolder());
        textField.setPasswordMode(option.isPasswordEnable());
        textField.setPasswordCharacter(option.getPasswordStyleText());
        return textField;
    }
}
