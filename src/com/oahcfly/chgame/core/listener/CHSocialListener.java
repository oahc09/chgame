
package com.oahcfly.chgame.core.listener;

public interface CHSocialListener {

    /**
     * 
     * <pre>
     * 反馈
     * 
     * date: 2015-1-17
     * </pre>
     * @author caohao
     */
    public void showFeedBack();

    /**
     * 
     * <pre>
     * 分享
     * 
     * date: 2015-1-17
     * </pre>
     * @author caohao
     * @param title 标题
     * @param subject 文本提示
     * @param text 文本内容
     */
    public void showShare(String title, String subject, String text);

}
