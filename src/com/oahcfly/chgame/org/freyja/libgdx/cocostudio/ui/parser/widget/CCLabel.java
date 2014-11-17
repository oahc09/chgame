package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.WidgetParser;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.TTFLabel;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.TTFLabelStyle;

public class CCLabel extends WidgetParser {

	@Override
	public String getClassName() {
		return "Label";
	}

	@Override
	public Actor parse(CocoStudioUIEditor editor, CCWidget widget,
			CCOption option) {

		final TTFLabelStyle labelStyle = editor.createLabelStyle(option);

		TTFLabel label = new TTFLabel(option.getText(), labelStyle);

		// 水平
		int h = 0;
		switch (option.gethAlignment()) {
		case 0:
			h = Align.left;
			break;
		case 1:
			h = Align.center;
			break;
		default:
			h = Align.right;
			break;
		}

		// 垂直
		int v = 0;
		switch (option.getvAlignment()) {
		case 0:
			v = Align.top;
			break;
		case 1:
			v = Align.center;
			break;
		default:
			v = Align.bottom;
			break;
		}
		label.setAlignment(h, v);

		return label;
	}
}
