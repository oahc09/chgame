
package com.oahcfly.chgame.core.listener;

/**
 * 
 * <pre>
 * 调用Android原生组件
 * 
 * date: 2015-2-3
 * </pre>
 * @author caohao
 */
public interface CHAndroidViewListener {
    public static final int LENGTH_SHORT = 0;

    public static final int LENGTH_LONG = 1;

    public void showToast(String content, int lengthType);

    public void showExitDialog(String content, String okBtnString, String cancelBtnString);

    public void showTipDialog(String content);
}
