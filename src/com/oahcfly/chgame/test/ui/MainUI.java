
package com.oahcfly.chgame.test.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.oahcfly.chgame.core.CHUI;

public class MainUI extends CHUI {

    public MainUI(Stage stage) {
        super(stage, null, "screen/MainScreen_1.json");
        getEditor().findActor("img_rush_bg").addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("ccc", "player jump touch down");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Image imgPause = (Image)getEditor().findActor("img_pause");
        imgPause.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("ccc", "pause touch down");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("ccc", "pause touch up");
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

}
