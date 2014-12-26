
package com.oahcfly.chgame.core;

public class Version {

    /**
     * 0.0.1  初始版本
     * 0.0.2 增加CHUI，ColorLabel
     * 0.0.3 增加Plist解析，还需完善 [使用Sprite类完美解决旋转问题，参考TextureAtlas相关代码实现]
     * 0.0.4 增加A星寻路算法
     * 0.0.5 新增加密，随机相关工具类
     * 0.0.6 新增CHADListener广告接口&封装粒子类
     * 0.0.7 字体加上抗锯齿&封装动画播放器CHAniamtionPlayer & 带缩放的监听器
     * 0.0.8 封装广告渠道 & mvc框架 & 资源加载封装
     * 0.0.9 调用android相关UI组件接口
     * 0.1.0 常用工具类整合
     * 0.1.1  封装UI更新事件相关类，用于网络异步请求的UI处理. 新增manager. 编辑器字体抗锯齿效果。
     * 0.1.2 存储新增加密属性,manager优化
     * 0.1.3 label性能优化【注意：label最好不要使用太多】首次创建TTFLabelStyle不去创建字体减少耗时。只有settext时才重新创建。
     * 0.1.4 新增toast和loading相关UI
     * 0.1.5 集成SmartFont，对中文支持不是很好。labelaltes 居中问题。
     *  */
    public static final String VERSION = "0.1.4";
}
