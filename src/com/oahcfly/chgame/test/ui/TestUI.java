
package com.oahcfly.chgame.test.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oahcfly.chgame.core.ui.CHUI;

public class TestUI extends CHUI {

    public TestUI(Stage stage) {
        super(stage, null, "screen/PausePanel_1.json");
        initUI();
    }

    public void initUI() {
        Button button = getGroup().findActor("btn_resume");
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                dismiss();
                super.clicked(event, x, y);
            }

        });

    }

}
