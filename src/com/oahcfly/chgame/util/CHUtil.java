
package com.oahcfly.chgame.util;


public class CHUtil {

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
