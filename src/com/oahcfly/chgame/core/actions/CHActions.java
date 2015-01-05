
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;

/**
 * 
 * <pre>
 * 封装各类Action：
 * 旋转+掉落并发效果，滑动回弹效果，中心缩放效果。
 * date: 2015-1-4
 * </pre>
 * @author caohao
 */
public class CHActions {
    /**
     * 
     * <pre>
     * 旋转并按照抛物线移动某段距离
     * 
     * date: 2015-1-5
     * </pre>
     * @author caohao
     * @param duration        时长
     * @param rotateAmount 旋转次数
     * @param moveXAmount X方向移动距离
     * @param moveYAmount Y方向移动距离
     * @return
     */
    public static Action createRotateAndMoveAction(float duration, int rotateAmount, int moveXAmount, int moveYAmount) {
        Action rotateAction = Actions.rotateTo(360 * rotateAmount, duration);
        ParabolaAction parabolaAction = new ParabolaAction();
        parabolaAction.setAmount(moveXAmount, moveYAmount);
        parabolaAction.setDuration(duration);
        ParallelAction parallelAction = new ParallelAction(rotateAction, parabolaAction);
        return parallelAction;
    }

}
