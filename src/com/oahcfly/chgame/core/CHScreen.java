
package com.oahcfly.chgame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * 
 * <pre>
 * 场景抽像类
 * 
 * date: 2014-5-11
 * </pre>
 * @author caohao
 */
public abstract class CHScreen implements Screen {
    private String TAG = getClass().getSimpleName();

    private CHGame game = CHGame.getInstance();

    private int stageW = 0, stageH = 0;

    /** 初始化场景信息*/
    public abstract void initScreen();

    /**从当前场景切换到另一个场景需要前执行*/
    public abstract void endScreen();

    /**点击back键了*/
    public abstract void clickBackKey();

    public CHScreen() {
        this.stageW = game.getGameWidth();
        this.stageH = game.getGameHeight();
    }

    public CHScreen(int stageW, int stageH) {
        this.stageW = stageW;
        this.stageH = stageH;
    }

    public void dispose() {

    }

    private Stage stage;

    @Override
    public void show() {
        setBackKeyPressed(false);
        StretchViewport viewport = new StretchViewport(stageW, stageH);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        initScreen();
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        endScreen();
        stage.dispose();
        dispose();
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

}
