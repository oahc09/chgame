
package com.oahcfly.chgame.plist.element;

public class PInteger extends PListObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4021780717015931063L;

    private int value;

    public PInteger(int v) {
        setValue(v);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
