
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.WidgetParser;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.ProgressBar;

public class CCLoadingBar extends WidgetParser {

    @Override
    public String getClassName() {
        return "LoadingBar";
    }

    @Override
    public Actor parse(CocoStudioUIEditor editor, CCWidget widget, CCOption option) {

        if (option.getTextureData() == null) {
            return new Image();
        }
//        Drawable tr = editor.findDrawable(option, option.getTextureData().getPath());
//        if (tr == null) {
//            return new Image();
//        }
        //		Image image = new Image(tr);
        //		return image;

        if (option.getTextureData() == null) {
            return new Image();
        }
        Drawable tr = editor.findDrawable(option, option.getTextureData().getPath());
        if (tr == null) {
            return new Image();
        }

        Pixmap pixmap = new Pixmap((int)tr.getMinWidth(), (int) tr.getMinHeight(), Format.RGB888);
 
        Texture texture= editor.getTexture(pixmap);
        Drawable drawable= new TextureRegionDrawable(new TextureRegion(texture));//new Texture("Dialog/loadingbar.png")));
        ProgressBar.ProgressBarStyle mBarStyle = new ProgressBar.ProgressBarStyle(drawable,tr);

        ProgressBar mProgressBar = new ProgressBar(1, 100, 1, false, mBarStyle);

        mProgressBar.setSize(option.getWidth() * option.getScaleX(), option.getHeight() * option.getScaleY());
        mProgressBar.setValue(option.getPercent());
        return mProgressBar;

    }
}
