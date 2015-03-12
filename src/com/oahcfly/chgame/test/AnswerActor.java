
package com.oahcfly.chgame.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.core.mvc.CHActor;
import com.oahcfly.chgame.core.mvc.CHGame;

public class AnswerActor extends CHActor {
    private Label textLabel;

    public void setText(String text) {
        if (text == null) {
            textLabel = null;
        } else {
            textLabel = CHGame.getInstance().getInternationalGenerator().createLabel(text);
            textLabel.setWrap(true);
            textLabel.setBounds(0, 0, 300, 300);
            textLabel.setColor(Color.BLACK);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO Auto-generated method stub
        super.draw(batch, parentAlpha);
        if (textLabel != null) {
            textLabel.setPosition(getX(Align.center), getY(Align.center), Align.center);
            textLabel.draw(batch, parentAlpha);
        }
    }

    public boolean textIsEmpty() {
        return textLabel == null;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        super.reset();
        textLabel = null;
    }

    public String getText() {
        if (textLabel != null)
            return textLabel.getText().toString();
        return null;
    }
}
