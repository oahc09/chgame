
package com.oahcfly.chgame.core.mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.oahcfly.chgame.core.actions.CHScheduleAction;
import com.oahcfly.chgame.core.async.CHAsyncTask;
import com.oahcfly.chgame.core.ui.CHUI;

/**
 * 
 * <pre>
 * 【View层】
 * 场景抽像类
 * 
 * 内部管理多个UI。UI可以自由切换，关闭和打开。
 * UI数据通过CHModel进行管理。
 * date: 2014-5-11
 * </pre>
 * @author caohao
 */
public abstract class CHScreen implements Screen, CHUIFocusListener {

    /** 初始化场景信息*/
    public abstract void initScreen();

    /**从当前场景切换到另一个场景需要前执行*/
    public abstract void endScreen();

    /**点击back键了*/
    public abstract void clickBackKey();

    private String TAG = getClass().getSimpleName();

    // 舞台宽&高
    private int stageW = 0, stageH = 0;

    // CHUI的缓存
    private HashMap<String, CHUI> chuiMap;

    private Stage stage;

    private CHModel chModel;

    // 影子
    private CHActor shadowActor;

    // chui堆栈
    private Stack<CHUI> chuiStack;

    // 是否已经处理了back键点击
    private boolean doBackClick = false;

    public CHScreen() {
        this(CHGame.getInstance().getGameWidth(), CHGame.getInstance().getGameHeight());
    }

    public CHScreen(int stageW, int stageH) {
        this.stageW = stageW;
        this.stageH = stageH;
        chuiStack = new Stack<CHUI>();
        chuiMap = new HashMap<String, CHUI>();
    }

    @Override
    public void show() {
        Gdx.app.debug(TAG, "screen-show");
        setBackKeyPressed(false);
        createStage();
        initScreen();
    }

    private void createStage() {
        // 自动拉伸舞台
        StretchViewport viewport = new StretchViewport(stageW, stageH);
        stage = new Stage(viewport, getGame().getBatch());
        stage.getRoot().setName(getClass().getSimpleName());
        // 注册触摸事件
        Gdx.input.setInputProcessor(stage);

        shadowActor = new CHActor();
        addActor(shadowActor);

        Gdx.app.debug(TAG, "screen-init-createStage");
    }

    @Override
    public void hide() {
        Gdx.app.debug(TAG, "screen-hide");
        // screen销毁
        Set<String> keys = chuiMap.keySet();
        for (String key : keys) {
            // 依次关闭CHUI
            chuiMap.get(key).dismiss();
        }
        chuiMap.clear();

        chuiStack.clear();

        endScreen();

        // 此处可能报错：java.lang.IllegalArgumentException: buffer not allocated with newUnsafeByteBuffer or already disposed
        // 具体原因不清楚。。。。
        stage.dispose();

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        Gdx.app.debug(TAG, "screen-pause");
    }

    @Override
    public void render(float delta) {
        draw();
        act(delta);

        // 点击了返回键
        boolean pressBackKey = Gdx.input.isKeyPressed(Input.Keys.BACK);
        if (doBackClick && !pressBackKey) {
            // 已经通知过返回事件了，并且事件已经被处理完了，重置标志，继续监测
            doBackClick = false;
        }
        if (getGame().isCatchBackKey() && pressBackKey && !doBackClick) {
            // 监听返回键【未处理back键事件，且当前点击了】
            doBackClick = true;
            clickBackKey();
        }

    }

    public void draw() {
        getStage().draw();
    }

    public void act(float delta) {
        getStage().act();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        Gdx.app.debug(TAG, "screen-resize");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        Gdx.app.debug(getClass().getName(), "screen-resume");
    }

    public <T extends CHGame> T getGame() {
        return CHGame.getInstance();
    }

    public Stage getStage() {
        return stage;
    }

    public void addActor(Actor actor) {
        getStage().addActor(actor);
    }

    public String getTAG() {
        return TAG;
    }

    private void setBackKeyPressed(boolean backKey) {
        this.doBackClick = backKey;
    }

    @SuppressWarnings("unchecked")
    public <T extends CHModel> T getModel() {
        return (T)chModel;
    }

    public void setModel(CHModel chModel) {
        this.chModel = chModel;
        if (chModel.getScreen() == null)
            chModel.setScreen(this);
    }

    public void addUI(CHUI chui) {
        chuiMap.put(chui.getClass().getSimpleName(), chui);
    }

    public void showUI(String uiname) {
        getUI(uiname).show();
    }

    /**
     * 
     * <pre>
     *  chui会关闭显示
     * 
     * date: 2015-4-11
     * </pre>
     * @author caohao
     * @param chui
     * @param newUIName
     */
    public void changeUI(CHUI chui, String newUIName) {
        chui.dismiss();
        getUI(newUIName).show();
    }

    /**
     * 
     * <pre>
     * 根据名称得到CHUI
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends CHUI> T getUI(String name) {
        return (T)chuiMap.get(name);
    }

    public void dispose() {
    }

    @Override
    public void notifyUIUnFocus(CHUI chui) {
        // chui关闭了。从栈里面移除。
        chuiStack.remove(chui);
    }

    @Override
    public void notifyUIFocus(CHUI chui) {
        // chui显示。加入栈内，处在顶部
        chuiStack.push(chui);
    }

    /**
     * 
     * <pre>
     * 当前正在展示的CHUI
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     * @return
     */
    public CHUI getTopCHUI() {
        if (chuiStack.isEmpty())
            return null;
        return chuiStack.peek();
    }

    /**
     * 
     * <pre>
     * 加入异步任务处理
     * 
     * date: 2015-1-17
     * </pre>
     * @author caohao
     * @param asyncTask
     */
    public void addAsyncTask(CHAsyncTask asyncTask) {
        CHGame.getInstance().getAsyncManager().loadTask(asyncTask);
    }

    /**
     * 
     * <pre>
     * 时间调度[同步，在绘制线程中执行，方法内不能有耗时处理]
     * 【每间隔duration时间，调用methodName的方法】
     * date: 2015-1-22
     * </pre>
     * @author caohao
     * @param methodName 方法名
     * @param duration 间隔时间
     */
    public void addSyncSchedule(String methodName, float duration) {
        try {
            final Class<?> screenClass = this.getClass();
            final Method method = screenClass.getDeclaredMethod(methodName);

            Action runnableAction = Actions.run(new Runnable() {

                @Override
                public void run() {
                    try {
                        method.invoke(CHScreen.this);
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        Gdx.app.error(TAG, "addSyncSchedule :" + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        Gdx.app.error(TAG, "addSyncSchedule :" + e.getMessage());
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        Gdx.app.error(TAG, "addSyncSchedule :" + e.getMessage());
                    }
                }
            });
            for (Action action : shadowActor.getActions()) {
                if (action instanceof CHScheduleAction && ((CHScheduleAction)action).getName().equals(methodName)) {
                    // 发现已有action,重复利用，修改间隔时间即可
                    ((CHScheduleAction)action).setDuration(duration);
                    action.restart();
                    return;
                }
            }

            shadowActor.addAction(new CHScheduleAction(methodName, duration, -1, runnableAction));
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            Gdx.app.error(TAG, "addSyncSchedule :" + e.getMessage());
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            Gdx.app.error(TAG, "addSyncSchedule :" + e.getMessage());
        }
    }

    /**
     * 
     * <pre>
     * 取消时间调度[同步]
     * 
     * date: 2015-1-22
     * </pre>
     * @author caohao
     * @param methodName 方法名
     */
    public void unSyncSchedule(String methodName) {
        for (Action action : shadowActor.getActions()) {
            if (action instanceof CHScheduleAction) {
                // 直接完成
                ((CHScheduleAction)action).finish();
            }
        }
    }

    public boolean isBackKey() {
        return doBackClick;
    }
}

interface CHUIFocusListener {
    /**
     * 
     * <pre>
     * chui不显示，失去焦点
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     * @param chui
     */
    public void notifyUIUnFocus(CHUI chui);

    /**
     * 
     * <pre>
     * chui显示，获得焦点
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     * @param chui
     */
    public void notifyUIFocus(CHUI chui);

}
