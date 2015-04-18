
package com.oahcfly.chgame.core.ui;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.oahcfly.chgame.core.annotations.CHUIAnnotation;
import com.oahcfly.chgame.core.listener.CHClickListener;
import com.oahcfly.chgame.core.mvc.CHModel;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;

/**
 * 
 * <pre>
 * UI面板 : 可以dismiss后重新show
 * 
 * date: 2014-10-24
 * </pre>
 * @author caohao
 */
public abstract class CHUI {

    private CocoStudioUIEditor editor;

    private Group group;

    private Stage stage;

    private CHScreen parentScreen;

    /**
     * 获取注解
     */
    public CHUIAnnotation getAnnotation() {
        if (this.getClass().isAnnotationPresent(CHUIAnnotation.class)) {
            //若存在就获取注解
            CHUIAnnotation annotation = (CHUIAnnotation)this.getClass().getAnnotation(CHUIAnnotation.class);
            return annotation;
        } else {
            return null;
        }
    }

    public CHUI(CHScreen screen) {
        CHUIAnnotation chAnnotation = getAnnotation();
        String jsonPath = null;
        if (chAnnotation != null) {
            jsonPath = chAnnotation.jsonPath();
        } else {
            throw new GdxRuntimeException("cannot find CHUIAnnotation in class!!!");
        }
        this.parentScreen = screen;
        this.stage = parentScreen.getStage();
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), null, null, null, null);
        group = editor.createGroup();
        initUIBeforeShow();
    }

    public CHUI(CHScreen screen, String jsonPath) {
        CHUIAnnotation chAnnotation = getAnnotation();
        if (chAnnotation != null) {
            jsonPath = chAnnotation.jsonPath();
        }
        this.parentScreen = screen;
        this.stage = parentScreen.getStage();
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), null, null, null, null);
        group = editor.createGroup();
        initUIBeforeShow();
    }

    public CHUI(Stage stage, Map<String, FileHandle> ttfs, String jsonPath) {
        CHUIAnnotation chAnnotation = getAnnotation();
        if (chAnnotation != null) {
            jsonPath = chAnnotation.jsonPath();
        }
        this.stage = stage;
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), ttfs, null, null, null);
        group = editor.createGroup();
        initUIBeforeShow();
    }

    public CHUI(CHScreen screen, Map<String, FileHandle> ttfs, String jsonPath) {
        CHUIAnnotation chAnnotation = getAnnotation();
        if (chAnnotation != null) {
            jsonPath = chAnnotation.jsonPath();
        }
        this.parentScreen = screen;
        this.stage = parentScreen.getStage();
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), ttfs, null, null, null);
        group = editor.createGroup();
        initUIBeforeShow();
    }

    /**
     * 
     * <pre>
     * 显示CHUI
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     */
    public void show() {
        stage.addActor(group);
        refreshUIAfterShow();
        if (parentScreen != null) {
            parentScreen.notifyUIFocus(this);
        }
    }

    /**
     * 
     * <pre>
     * 隐藏CHUI
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     */
    public void dismiss() {
        group.remove();
        editor.clear();
        if (parentScreen != null) {
            parentScreen.notifyUIUnFocus(this);
        }
        resetAfterDismiss();
    }

    public CocoStudioUIEditor getEditor() {
        return editor;
    }

    public Group getGroup() {
        return group;
    }

    public Stage getParentStage() {
        return stage;
    }

    public <T extends Actor> T findActor(String name) {
        return getGroup().findActor(name);
    }

    public void addActor(Actor actor) {
        group.addActor(actor);
    }

    @SuppressWarnings("unchecked")
    public <T extends CHScreen> T getParentScreen() {
        return (T)parentScreen;
    }

    public <T extends CHModel> T getParentModel() {
        return parentScreen.getModel();
    }

    /**
     * 
     * <pre>
     * 是否在显示
     * 
     * date: 2015-1-12
     * </pre>
     * @author caohao
     * @return
     */
    public boolean isShowing() {
        if (getParentScreen().getTopCHUI() == null)
            return false;
        return getParentScreen().getTopCHUI().getGroup().getName().equals(group.getName());
    }

    /**
     * 
     * <pre>
     * 加入到舞台前初始化UI变量[在CHUI构造方法内调用]
     * 
     * date: 2015-1-10
     * </pre>
     * @author caohao
     */
    public abstract void initUIBeforeShow();

    /**
     * 
     * <pre>
     * 加入到舞台后，刷新UI数据
     * 
     * date: 2015-1-10
     * </pre>
     * @author caohao
     */
    public abstract void refreshUIAfterShow();

    /**
     * 
     * <pre>
     * 用于关闭UI后重置一些变量信息
     * 
     * date: 2015-1-30
     * </pre>
     * @author caohao
     */
    public abstract void resetAfterDismiss();

    /**
     * 
     * <pre>
     * 注册监听器
     * 
     * date: 2015-2-4
     * </pre>
     * @author caohao
     * @param actorName 组件名称
     * @param chClickListener 监听器
     */
    public void registerClickListener(String actorName, CHClickListener chClickListener) {
        Actor actor = findActor(actorName);
        if (actor == null) {
            throw new GdxRuntimeException("cannot find actor :" + actorName);
        }
        actor.addListener(chClickListener);
    }
}
