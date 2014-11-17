
package com.oahcfly.chgame.plist.element;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class PDict extends PListObject {

    /**
     * 
     */
    private static final long serialVersionUID = 39300499039163778L;

    private HashMap<String, PListObject> map;

    public PDict() {
        setType(PListObjectType.DICT);
        map = new HashMap<String, PListObject>();
    }

    public void addChild(String key, PListObject pListObject) {
        map.put(key, pListObject);
    }

    public PListObject findValue(String keyName) {
        return map.get(keyName);
    }

    public void print() {
        Gdx.app.log("PDict", map.keySet().toString());
    }
}
