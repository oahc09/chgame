
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.GroupParser;

/**
 * @tip 还未支持单色背景属性,背景图片在Cocostudio里面并不是铺满,而是居中
 * @author i see
 * 
 */
public class CCPanel extends GroupParser {

    @Override
    public String getClassName() {
        return "Panel";
    }

    @Override
    public Actor parse(CocoStudioUIEditor editor, CCWidget widget, CCOption option) {
        Table table = new Table();

        if (option.getColorType() == 0) {// 无颜色

        } else if (option.getColorType() == 1) {// 单色

            Pixmap pixmap = new Pixmap((int)option.getWidth(), (int)option.getHeight(), Format.RGBA8888);
            pixmap.setColor(option.getBgColorR() / 255f, option.getBgColorG() / 255f, option.getBgColorB() / 255f,
                    option.getBgColorOpacity());
            pixmap.fill();

            table.setBackground(new TextureRegionDrawable(new TextureRegion(editor.getTexture(pixmap))));
            pixmap.dispose();
        } else {// 渐变色

        }

        if (option.getBackGroundImageData() != null) {// Panel的图片并不是拉伸平铺的!!.但是这里修改为填充
            Drawable tr = editor.findDrawable(option, option.getBackGroundImageData().getPath());
            if (tr != null) {
                Image bg = new Image(tr);
                bg.setPosition((option.getWidth() - bg.getWidth()) / 2, (option.getHeight() - bg.getHeight()) / 2);
                // bg.setFillParent(true);
                bg.setTouchable(Touchable.disabled);

                bg.setColor(option.getColorR() / 255f, option.getColorG() / 255f, option.getColorB() / 255f,
                        option.getOpacity());
                table.addActor(bg);
            }
        }

        table.setClip(option.isClipAble());

        return table;
    }
}
