package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.WidgetParser;

public class CCImageView extends WidgetParser {

	@Override
	public String getClassName() {
		return "ImageView";
	}

	@Override
	public Actor parse(CocoStudioUIEditor editor, CCWidget widget,
			CCOption option) {

		Drawable tr = editor.findDrawable(option, option.getFileNameData()
				.getPath());
		if (tr == null) {
			return new Image();
		}
		Image image = new Image(tr);

		return image;
	}

}
