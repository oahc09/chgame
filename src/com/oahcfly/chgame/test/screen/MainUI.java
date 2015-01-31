
package com.oahcfly.chgame.test.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.oahcfly.chgame.core.listener.CHClickListener;
import com.oahcfly.chgame.core.listener.CHClickListener.CLICKTYPE;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.core.ui.CHToast;
import com.oahcfly.chgame.core.ui.CHUI;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.LabelAtlas;

public class MainUI extends CHUI {

    public MainUI(CHScreen chScreen) {
        super(chScreen, null, "screen/MainScreen_1.json");
        initUI();
    }

    public MainUI(Stage stage) {
        super(stage, null, "screen/MainScreen_1.json");
        initUI();
    }

    public void initUI() {
        findActor("img_rush_bg").addListener(new CHClickListener(CLICKTYPE.FADE));

        Image imgPause = findActor("img_pause");
        imgPause.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("ccc", "pause touch down");
                dismiss();
                new TestUI(getParentStage()).show();
                CHToast chToast = new CHToast("的间谍记得", Color.RED);
                chToast.setDelayDuration(11);
                chToast.show();
                CHGame.getInstance().showWaitingUI();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("ccc", "pause touch up");

                super.touchUp(event, x, y, pointer, button);
            }
        });

        LabelAtlas labelAtlas = getGroup().findActor("LabelAtlas_coin");

        labelAtlas.setText("110");
        labelAtlas.setX(0);
        labelAtlas.setY(10);
    }

    @Override
    public void refreshUIAfterShow() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initUIBeforeShow() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetAfterDismiss() {
        // TODO Auto-generated method stub
        
    }

}
