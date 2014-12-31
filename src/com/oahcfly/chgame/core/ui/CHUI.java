
package com.oahcfly.chgame.core.ui;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oahcfly.chgame.core.mvc.CHModel;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.CocoStudioUIEditor;

/**
 * 
 * <pre>
 * UI面板
 * 
 * date: 2014-10-24
 * </pre>
 * @author caohao
 */
public abstract class CHUI {

    private boolean isShowing;

    private CocoStudioUIEditor editor;

    private Group group;

    private Stage stage;

    private CHScreen parentScreen;

    public CHUI(Stage stage, Map<String, FileHandle> ttfs, String jsonPath) {
        this.stage = stage;
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), ttfs, null, null, null);
        group = editor.createGroup();
    }

    public CHUI(CHScreen screen, Map<String, FileHandle> ttfs, String jsonPath) {
        this.parentScreen = screen;
        this.stage = parentScreen.getStage();
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), ttfs, null, null, null);
        group = editor.createGroup();
    }

    public void show() {
        isShowing = true;
        stage.addActor(group);
    }

    public void dismiss() {
        group.remove();
        editor.clear();
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

    public boolean isShowing() {
        return isShowing;
    }

}
