
package com.oahcfly.chgame.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.test.screen.FirstScreen;

public class TestGame extends CHGame {

    @Override
    public void init() {
        setGameWidthAndHeight(800, 480);
        setFPS(true);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //CHADChannel androidAdChannel= new CHADChannel("{\"360\":false,\"baidu\":true,\"tengxu\":false,\"meizu\":false,\"anzhi\":false}");

        setScreen(new FirstScreen());

        //        String chineseExtraStr="nihao你好哈哈哈你是煞笔";
        //        char[] array = chineseExtraStr.toCharArray();
        //        StringBuffer stringBuffer = new StringBuffer();
        //        for (char c : array) {
        //            if (-1 == stringBuffer.indexOf(String.valueOf(c))) {
        //                stringBuffer.append(c);
        //            }
        //        }
        //        System.out.println(""+stringBuffer.toString());

        //        SharePreferenceUtil sharePreferenceUtil= new SharePreferenceUtil("测试", true);
        //        sharePreferenceUtil.saveSharedPreferences("ab1", false);
        //        sharePreferenceUtil.saveSharedPreferences("ab2", 2f);
        //        sharePreferenceUtil.saveSharedPreferences("ab3", 2);
        //        sharePreferenceUtil.saveSharedPreferences("ab4", 1l);
        //        sharePreferenceUtil.saveSharedPreferences("ab5", "cesfs的是多少");
        //        
        //        System.out.println(""+sharePreferenceUtil.loadStringSharedPreference("ab5", "null"));
        //        System.out.println(""+sharePreferenceUtil.loadIntSharedPreference("ab3", -1));
        //        

    }

}
