
package com.oahcfly.chgame.test.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.core.ui.CHUI;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.LabelAtlas;

public class TestUI extends CHUI {
    public TestUI(CHScreen chScreen) {
        super(chScreen, null, "screen/PausePanel_1.json");
        initUI();
    }

    public TestUI(Stage stage) {
        super(stage, null, "screen/PausePanel_1.json");
        initUI();
    }

    public void initUI() {
        Button button = getGroup().findActor("btn_resume");
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                TestUI.this.getParentScreen().getUI("MainUI").show();
                super.clicked(event, x, y);
            }

        });

        LabelAtlas labelAtlas = new LabelAtlas(new TextureRegion(CHGame.getInstance().getTexture(
                "screen/score_numlist.png")), 23, 27, "0123456789");
        labelAtlas.setColor(Color.RED);
        labelAtlas.setText("" + 11);
        labelAtlas.setX(100);
        labelAtlas.setY(100);
        labelAtlas.setOrigin(Align.center);
        addActor(labelAtlas);
    }

}
