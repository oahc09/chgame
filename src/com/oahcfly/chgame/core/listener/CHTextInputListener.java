
package com.oahcfly.chgame.core.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.oahcfly.chgame.core.mvc.CHGame;

/**
 * 
 * <pre>
 * 文本输入框
 * 
 * date: 2015-1-6
 * </pre>
 * @author caohao
 */
public abstract class CHTextInputListener implements TextInputListener {

    private Label target;

    public CHTextInputListener() {
    }

    public CHTextInputListener(Label label) {
        target = label;
    }

    @Override
    public void input(String text) {
        final String input = text;
        Gdx.app.postRunnable(new Runnable() {

            @Override
            public void run() {
                CHGame.getInstance().getInternationalGenerator().appendToFont(input);
                getInput(input);
                if (target != null) {
                    target.setText(input);
                }
            }
        });

    }

    /**
     * 
     * <pre>
     * 获取输入的字符串
     * 
     * date: 2015-1-6
     * </pre>
     * @author caohao
     * @param input
     */
    public abstract void getInput(String input);

    /**
     * 
     * <pre>
     * 显示输入框
     * 
     * date: 2015-1-6
     * </pre>
     * @author caohao
     * @param title
     */
    public void show(String title) {
        Gdx.input.getTextInput(this, title, "", "");
    }

    @Override
    public void canceled() {
        // 取消

    }

}
