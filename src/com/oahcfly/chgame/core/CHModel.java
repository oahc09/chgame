
package com.oahcfly.chgame.core;

/**
 * 
 * <pre>
 * 【Model层】
 * 
 * date: 2014-12-3
 * </pre>
 * @author caohao
 */
public abstract class CHModel {

    public CHScreen screen;

    public CHModel() {
    }

    public CHModel(CHScreen chScreen) {
        screen = chScreen;
    }

}
