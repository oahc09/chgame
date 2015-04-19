
package com.oahcfly.chgame.core.transition;


public abstract class AbstractScreenTransition implements IScreenTransition {

    // 切换时长
    private float sduration;

    public AbstractScreenTransition(float duration) {
        this.sduration = duration;
    }

    public float getDuration() {
        return sduration;
    }

}
