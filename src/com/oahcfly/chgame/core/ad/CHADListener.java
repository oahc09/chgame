
package com.oahcfly.chgame.core.ad;

/**
 * 
 * <pre>
 * 广告接口
 * 
 * date: 2014-12-3
 * </pre>
 * @author caohao
 */
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
     * 关闭插屏广告
     * 
     * date: 2014-12-22
     * </pre>
     * @author caohao
     */
    public void closeSpotAds();

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

    /**
     * 
     * <pre>
     * 关闭广告条
     * 
     * date: 2014-11-24
     * </pre>
     * @author caohao
     */
    public void closeBannerAds();

    /**
     * 
     * <pre>
     * 退出广告
     * 
     * date: 2015-2-8
     * </pre>
     * @author caohao
     */
    public void showQuitPopAd();
}
