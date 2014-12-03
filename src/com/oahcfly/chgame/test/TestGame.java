
package com.oahcfly.chgame.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.oahcfly.chgame.core.ad.CHADChannel;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.test.screen.FirstScreen;

public class TestGame extends CHGame {

    @Override
    public void init() {
        // TODO Auto-generated method stub
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        
        CHADChannel androidAdChannel= new CHADChannel("{\"360\":false,\"baidu\":true,\"tengxu\":false,\"meizu\":false,\"anzhi\":false}");
        
        setScreen(new FirstScreen());
        
        String chineseExtraStr="nihao你好哈哈哈你是煞笔";
        char[] array = chineseExtraStr.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : array) {
            if (-1 == stringBuffer.indexOf(String.valueOf(c))) {
                stringBuffer.append(c);
            }
        }
        System.out.println(""+stringBuffer.toString());
    }

}
