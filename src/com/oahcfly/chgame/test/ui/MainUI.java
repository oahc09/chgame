
package com.oahcfly.chgame.test.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.oahcfly.chgame.core.listener.CHClickListener;
import com.oahcfly.chgame.core.listener.CHClickListener.CLICKTYPE;
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
        //        getEditor().findActor("img_rush_bg").addListener(new DragListener() {
        //
        //            @Override
        //            public void dragStart(InputEvent event, float x, float y, int pointer) {
        //                Gdx.app.log("ccc", "dragStart");
        //                super.dragStart(event, x, y, pointer);
        //            }
        //
        //            @Override
        //            public void drag(InputEvent event, float x, float y, int pointer) {
        //                Gdx.app.log("ccc", "drag+"+super.isDragging()+","+super.getStageTouchDownX()+","+super.getStageTouchDownY());
        //                super.drag(event, x, y, pointer);
        //            }
        //
        //            @Override
        //            public void dragStop(InputEvent event, float x, float y, int pointer) {
        //                Gdx.app.log("ccc", "dragStop");
        //                super.dragStop(event, x, y, pointer);
        //            }
        //
        //    
        //
        //        });

        //        new InputListener() {
        //
        //            @Override
        //            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        //
        //                Gdx.app.log("ccc", "player jump touch down");
        //                return super.touchDown(event, x, y, pointer, button);
        //            }
        //
        //            @Override
        //            public void touchDragged(InputEvent event, float x, float y, int pointer) {
        //                Gdx.app.log("ccc", "player touchDragged");
        //                super.touchDragged(event, x, y, pointer);
        //            }
        //            
        //            
        //        }
        //        
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
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("ccc", "pause touch up");
                //CHGame.getInstance().closeLoadingUI();
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

}
