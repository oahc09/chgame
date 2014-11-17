
package com.oahcfly.chgame.core;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private CocoStudioUIEditor editor;

    private Group group;

    private Stage stage;
    public CHUI(Stage stage,Map<String, FileHandle> ttfs, String jsonPath) {
        this.stage=stage;
        editor = new CocoStudioUIEditor(Gdx.files.internal(jsonPath), ttfs, null, null, null);
        group = editor.createGroup();
    }

    public void show(){
        stage.addActor(group);
    }
    
    public void dismiss(){
        group.remove();
        editor.clear();
    }
    
    public CocoStudioUIEditor getEditor() {
        return editor;
    }

    public Group getGroup() {
        return group;
    }

}
