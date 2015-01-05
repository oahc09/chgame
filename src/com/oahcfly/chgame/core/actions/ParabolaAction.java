
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**
 * 
 * <pre>
 * 抛物线Action
 * X^2=-2*P*Y+Y0
 * 
 * date: 2015-1-5
 * </pre>
 * @author caohao
 */
public class ParabolaAction extends TemporalAction {
    private float amountX, amountY;

    private float startX, startY;

    //[p为焦准距（p>0）]
    float P;

    float y0;

    public ParabolaAction() {
    }

    public ParabolaAction(float duration, float moveXAmount, float moveYAmount) {
        setDuration(duration);
        setAmount(moveXAmount, moveYAmount);
    }

    protected void begin() {
        startX = target.getX();
        startY = target.getY();

        float endX = startX + amountX;
        float endY = startY + amountY;
        P = (float)((-0.5) * (endX * endX - startX * startX) / (endY - startY));
        y0 = startX * startX + 2 * P * startY;

    }

    @Override
    protected void update(float percent) {

        float newX = startX + percent * amountX;
        float newY = (newX * newX - y0) / ((-2) * P);

        target.setPosition(newX, newY);

    }

    public void setAmount(float x, float y) {
        amountX = x;
        amountY = y;
    }

    public float getAmountX() {
        return amountX;
    }

    public void setAmountX(float x) {
        amountX = x;
    }

    public float getAmountY() {
        return amountY;
    }

    public void setAmountY(float y) {
        amountY = y;
    }
}
