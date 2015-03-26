
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**
 * 
 * <pre>
 * 抛物线Action
 * 公式：y=a*(x-x0)^2+y0
 * 起始点为顶点坐标
 * date: 2015-1-5
 * </pre>
 * @author caohao
 */
public class CHParabolaAction extends TemporalAction {
    private float amountX, amountY;

    private float startX, startY;

    float a;

    public CHParabolaAction() {
    }

    public CHParabolaAction(float duration, float moveXAmount, float moveYAmount) {
        setDuration(duration);
        setAmount(moveXAmount, moveYAmount);
    }

    protected void begin() {
        startX = actor.getX();
        startY = actor.getY();

        float endX = startX + amountX;
        float endY = startY + amountY;

        //y=a*(x-x0)^2+y0
        a = (float)((endY - startY) / (Math.pow((endX - startX), 2)));
    }

    @Override
    protected void update(float percent) {

        float newX = startX + percent * amountX;
        //y=a*(x-x0)^2+y0
        float newY = a * (newX - startX) * (newX - startX) + startY;

        actor.setPosition(newX, newY);

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
