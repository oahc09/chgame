
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 * 
 * <pre>
 * Label专用的文字挨个显示
 * 
 * date: 2014-12-29
 * </pre>
 * @author caohao
 */
public class LabelTickerAction extends TemporalAction {

    private CharSequence completeText;

    private StringBuilder currentDisplay = new StringBuilder();

    private Label label;

    private int currentPos;

    public LabelTickerAction() {
    }

    public LabelTickerAction(float duration) {
        super(duration);
    }

    public LabelTickerAction(float duration, CharSequence text) {
        this(duration, text, null);
    }

    public LabelTickerAction(float duration, CharSequence text, Interpolation interpolation) {
        super(duration, interpolation);
        completeText = text;
        currentDisplay.setLength(text.length());
    }

    /**
     * Resets Ticker.
     */
    @Override
    public void restart() {
        super.restart();
        completeText = null;
        currentDisplay.delete(0, currentDisplay.length());
        label = null;
        currentPos = -1;
    }

    /**
     * Sets Label to be target of Ticker. Performs check if actor is instance of Label by instanceof operator.
     * @see #setActor(Actor);
     */
    @Override
    public void setTarget(Actor actor) {
        if (!(actor instanceof Label))
            if (actor == null)
                super.setActor(actor);
            else
                throw new IllegalArgumentException("Ticker uses only Labels");

        super.setActor(actor);
        label = (Label)super.actor;
    }

    /**
     * Sets text to be ticked.
     * @param text
     */
    public void setText(CharSequence text) {
        completeText = text;
        currentDisplay.setLength(text.length());
    }

    @Override
    protected void begin() {
        currentPos = 0;
        currentDisplay.append(completeText.charAt(0));
    }

    @Override
    protected void update(float percent) {
        currentPos = (int)(completeText.length() * percent);
        currentDisplay.delete(0, currentDisplay.length());
        for (int i = 0; i < currentPos; i++) {
            currentDisplay.append(completeText.charAt(i));
        }

        label.setText(currentDisplay);
    }

    /**
     * Static factory method for LabelTickerAction with default duration of 2f. Pools instance.
     * @param text
     * @return
     */
    public static LabelTickerAction obtain(CharSequence text) {
        return LabelTickerAction.obtain(text, 2f);
    }

    /**
     * Static factory method for LabelTickerAction. Pools instance.
     * @param text
     * @param duration
     * @return
     */
    public static LabelTickerAction obtain(CharSequence text, float duration) {
        Pool<LabelTickerAction> pool = Pools.get(LabelTickerAction.class);
        LabelTickerAction t = pool.obtain();
        t.setPool(pool);
        t.setDuration(duration);
        t.completeText = text;
        t.currentDisplay.setLength(text.length());
        return t;

    }
}
