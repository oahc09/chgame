package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.GroupParser;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.LabelAtlas;

/**
 * 
 * 数字标签,暂时不支持.首字符设置.也就是说数字图片必须是0-9
 * 
 * @author i see
 * 
 */
public class CCLabelAtlas extends GroupParser {

	@Override
	public String getClassName() {
		return "LabelAtlas";
	}

	@Override
	public Actor parse(CocoStudioUIEditor editor, CCWidget widget,
			CCOption option) {

		if (option.getCharMapFileData() != null) {
			TextureRegion tr = editor.findTextureRegion(option, option
					.getCharMapFileData().getPath());
			if (tr != null) {
				LabelAtlas label = new LabelAtlas(tr, option.getItemWidth(),
						option.getItemHeight(), option.getStartCharMap(),
						option.getStringValue());
				return label;

			}
		}

		return new Table();
	}
}
