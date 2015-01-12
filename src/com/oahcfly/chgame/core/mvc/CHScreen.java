
package com.oahcfly.chgame.core.mvc;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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

    private CHGame game = CHGame.getInstance();

    // 舞台宽&高
    private int stageW = 0, stageH = 0;

    // CHUI的缓存
    private HashMap<String, CHUI> chuiMap = new HashMap<String, CHUI>();

    private Stage stage;

    private CHModel chModel;

    /**当前正在展示的CHUI*/
    private CHUI topCHUI;

    public CHScreen() {
        this.stageW = game.getGameWidth();
        this.stageH = game.getGameHeight();
    }

    public CHScreen(int stageW, int stageH) {
        this.stageW = stageW;
        this.stageH = stageH;
    }

    @Override
    public void show() {
        setBackKeyPressed(false);
        // 自动拉伸舞台
        StretchViewport viewport = new StretchViewport(stageW, stageH);
        stage = new Stage(viewport);
        // 注册触摸事件
        Gdx.input.setInputProcessor(stage);
        initScreen();
    }

    @Override
    public void hide() {
        // screen销毁
        Set<String> keys = chuiMap.keySet();
        for (String key : keys) {
            // 依次关闭CHUI
            chuiMap.get(key).dismiss();
        }
        chuiMap.clear();

        endScreen();

        // 此处可能报错：java.lang.IllegalArgumentException: buffer not allocated with newUnsafeByteBuffer or already disposed
        // 具体原因不清楚。。。。
        stage.dispose();

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        Gdx.app.log(TAG, "screen-pause");
    }

    private boolean backKey = false;

    @Override
    public void render(float delta) {
        draw();
        act(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) && !backKey) {
            // 监听返回键
            backKey = true;
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
        Gdx.app.log(TAG, "screen-resize");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        Gdx.app.log(getClass().getName(), "screen-resume");
    }

    public CHGame getGame() {
        return game;
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

    public void setBackKeyPressed(boolean backKey) {
        this.backKey = backKey;
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

    public void changeUI(String oldUIName, String newUIName) {
        getUI(oldUIName).dismiss();
        getUI(newUIName).show();
    }

    @SuppressWarnings("unchecked")
    public <T extends CHUI> T getUI(String name) {
        return (T)chuiMap.get(name);
    }

    public void dispose() {

    }

    @Override
    public void notifyUIUnFocus(CHUI chui) {

    }

    @Override
    public void notifyUIFocus(CHUI chui) {
        topCHUI = chui;

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
        return topCHUI;
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
