package com.oahcfly.chgame.util;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ActionUtil {

    public static Action getClickAction() {
        Action mClick = Actions.sequence(Actions.alpha(0.5f, 0.1f), Actions.alpha(1f, 0.1f));
        return mClick;
    }

}
