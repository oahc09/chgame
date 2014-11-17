
package com.oahcfly.chgame.util;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class CHUtils {

    public static Action getClickAction() {
        Action mClick = Actions.sequence(Actions.alpha(0.5f, 0.1f), Actions.alpha(1f, 0.1f));
        return mClick;
    }

    public static String getTimeFormat(int time) {
        String str = "";
        if (String.valueOf(time / 60).length() == 1) {
            str += "0" + (time / 60);
        } else {
            str += (time / 60);
        }

        str += ":";

        if (String.valueOf(time % 60).length() == 1) {
            str += "0" + (time % 60);
        } else {
            str += (time % 60);
        }

        //return String.format("%02d:%02d",time/60, time%60);
        return str;
    }
}
