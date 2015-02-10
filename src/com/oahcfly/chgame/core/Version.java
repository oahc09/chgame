
package com.oahcfly.chgame.core;

public class Version {

    /**
     * 0.0.0
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
     * 0.1.6 TTFLabel优化，未设置TTF路径，则使用系统自带字体。
     * 0.1.7 封装各种action，【倒计时，文字挨个显示，跳动《左右，上下》，缩放，旋转】
     * 0.1.8 CHScreen内部管理一系列CHUI。CHUI切换特效。构造函数优化。数字标签颜色处理。
     * 0.1.9 PixmapHelper封装常见图形绘制。 BmobListener移除。
     * 0.2.0 修改chgame初始加载字体方案提供加载速度，改完在createLabel时加载文字纹理。
     * 0.2.1 新增CHLoadingScreen。CHActor使用缓存池。CHEvent优化。
     * 0.2.2 Action特辑：旋转+掉落并发效果，滑动回弹效果，中心缩放效果。TTFLabel新增setText方法。新增文本输入框抽象类。
     * 0.2.3 插屏广告比例控制。优化LabelAltas减少new Image的次数。
     * 0.2.4 CocoStudioUIEditor优化去除无用代码。CHUI新增两个抽象方法，规范UI刷新。目前CHUI可以自由关闭打开，通过CHScreen控制。
     * 0.2.5 修复颜色设置无效问题，优化CHLoadingScreen，labelatlas新增设置num方法。优化chtoast
     * 0.2.6 优化CHUI,优化chgame.chscreen。chloadingscreen加载速度提升，逻辑简化。
     * 0.2.7 新增各种粒子特效。
     * 0.2.8 封装异步任务。负责加载xml，json等耗时操作避免影响主线程。新增chsocialListenr.
     * 0.2.9 对于异步任务优化，update依托于CHGame. CHActions封装跳动，晃动的action。
     * 0.3.0 统一Batch。代码重构
     * 0.3.1 CHScreen新增时间调度schudule【同步】。整合sqlite。
     * 0.3.2 CHGame新增exit方法，退出前保存数据。新增Gif解析。
     * 0.3.3 CHClickListener优化。
     * 0.3.4 LabelAtlas优化支持缩放，颜色设置。 CHUI新增resetAfterDismiss()方法。修复LabelBMFont缩放。CHActor的reset加上setVisible。
     * 0.3.5 CHUI优化topCHUI机制。字体纹理释放问题。
     * 0.3.6 CHScreen切换触摸事件监听问题(PC版有问题，android没问题。)。
     * 0.3.7 封装CHLine线条绘制。添加接口CHAndroidViewListener。CHUI，CHClickListener优化。
     * 0.3.8 CHUI使用注解来获取JsonPath。
     * 0.3.9 back键处理优化.新增退出广告显示接口。
     * 0.4.0 新增文字描边和底部渐变效果。
     * 0.4.1
     * 0.4.2
     * 0.4.3
     * 0.4.4
     * 0.4.5
     * 0.4.6
     * 0.4.7
     * 
     *  */
    public static final String VERSION = "0.3.9";

    /**
     * 小贴士：
     *  
     *  1.使用Application.postRunnable()将其他线程的数据传递到渲染线程。在ApplicationListener.render()调用之前，会在渲染线程的Runnable中运行代码。
     * 
     * 
     */

    //  第一个Screen处理down事件->切换screen，screen注册了事件接收器，进行绘制处理->此时已经是第二个Screen在显示，就会处理up事件了。
    //    public void a() {
    //        Action[] sAction = new Action[20];
    //        // 使用action实现定时器
    //        for (int i = 0; i < sAction.length; i++) {
    //            Action delayedAction = Actions.run(new Runnable() {
    //
    //                @Override
    //                public void run() {
    //                    System.out.println("time:" + (System.currentTimeMillis() / 1000) + ",执行something");
    //                }
    //            });
    //            // 延迟1s后执行delayedAction
    //            Action action = Actions.delay(1f, delayedAction);
    //            sAction[i] = action;
    //        }
    //        getStage().addAction(Actions.sequence(sAction));
    //    }
}
