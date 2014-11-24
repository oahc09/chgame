
package com.oahcfly.chgame.core;

public interface CHADListener {

    /**
     * 
     * <pre>
     * 显示插屏广告
     * 
     * date: 2014-11-24
     * </pre>
     * @author caohao
     */
    public void showSpotAds();

    /**
     * 
     * <pre>
     * 显示广告条
     * 
     * date: 2014-11-24
     * </pre>
     * @author caohao
     */
    public void showBannerAds();

    /**
     * 
     * <pre>
     * 广告是否开启
     * 
     * date: 2014-11-24
     * </pre>
     * @author caohao
     * @return
     */
    public boolean isOpenAD();
}
