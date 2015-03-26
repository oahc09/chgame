
package com.oahcfly.chgame.core.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;

/**
 * 
 * <pre>
 * 封装各类Action：
 * 旋转+掉落并发效果，滑动回弹效果，中心缩放效果。上下跳动，左右跳动。
 * date: 2015-1-4
 * </pre>
 * @author caohao
 */
public class CHActions {

    /**
     * 
     * <pre>
     * 上下跳动
     * 
     * date: 2015-1-19
     * </pre>
     * @author caohao
     * @param distance 跳的高度
     * @param duration 花费时长
     * @param repeatCount 重复次数
     * @return
     */
    public static Action createUpAndDownAction(int distance, float duration, int repeatCount) {
        Action actionUp = Actions.moveBy(0, distance, duration);
        Action actionDown = Actions.moveBy(0, -distance, duration);
        return Actions.repeat(repeatCount, Actions.sequence(actionUp, actionDown));
    }

    /**
     * 
     * <pre>
     * 左右晃动
     * 
     * date: 2015-1-19
     * </pre>
     * @author caohao
     * @param distance 晃动的宽度
     * @param duration 花费时长
     * @param repeatCount 重复次数
     * @return
     */
    public static Action createLeftAndRightAction(int distance, float duration, int repeatCount) {
        Action actionLeft = Actions.moveBy(-distance, 0, duration);
        Action actionRight = Actions.moveBy(distance, 0, duration);
        return Actions.repeat(repeatCount, Actions.sequence(actionLeft, actionRight));
    }

    /**
     * 
     * <pre>
     * 旋转并按照抛物线移动某段距离
     * 【起始点为抛物线的顶点】
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
        CHParabolaAction parabolaAction = new CHParabolaAction();
        parabolaAction.setAmount(moveXAmount, moveYAmount);
        parabolaAction.setDuration(duration);
        ParallelAction parallelAction = new ParallelAction(rotateAction, parabolaAction);
        return parallelAction;
    }

    /**
     * 
     * <pre>
     * 自动缩放
     * 
     * date: 2015-1-7
     * </pre>
     * @author caohao
     * @param targetScale 目标缩放倍数 如：缩小=0.8f,放大=1.2f
     * @param singleScaleDuration 缩放到目标倍数耗时（单位:s）
     * @param repeatCount -1=无限次执行，1=执行1次
     * @return
     */
    public static Action createAutoScaleToCenter(float targetScale, float singleScaleDuration, int repeatCount) {
        Action repeatedAction = Actions.sequence(Actions.scaleTo(targetScale, targetScale, singleScaleDuration),
                Actions.scaleTo(1f, 1f, singleScaleDuration));
        Action action = Actions.repeat(repeatCount, repeatedAction);
        return action;
    }

    /**
     * 
     * <pre>
     * 回弹效果【仅限横向】
     * 
     * date: 2015-1-7
     * </pre>
     * @author caohao
     * @param targetX 目标X
     * @param targetY 目标Y
     * @param duration 时长
     * @param backDistance 回弹距离
     * @return
     */
    public static Action createMoveBackAction(float targetX, float targetY, float duration, int backDistance) {
        Action passAction = Actions.moveTo(targetX + backDistance, targetY, duration - 0.05f);
        Action backAction = Actions.moveTo(targetX - backDistance, targetY, 0.1f);
        Action action = Actions.sequence(passAction, backAction, Actions.moveTo(targetX, targetY, 0.1f));
        return action;
    }

    /**
     * 
     * <pre>
     * 闪烁效果
     * 
     * date: 2015-1-7
     * </pre>
     * @author caohao
     * @param duration 单次闪烁时长[不可见->可见]
     * @param repeatCount 闪烁次数
     * @return
     */
    public static Action createAutoTwinkleAction(float duration, int repeatCount) {
        Action action = Actions.repeat(repeatCount,
                Actions.sequence(Actions.fadeOut(duration / 2), Actions.fadeIn(duration / 2)));
        return action;
    }
}
