
package com.oahcfly.chgame.plist.element;

public class PBoolean extends PListObject {

    /**
     * 
     */
    private static final long serialVersionUID = -5708079552890202102L;

    private boolean isTrue = true;

    public PBoolean(boolean b) {
        isTrue = b;
        if (isTrue) {
            setType(PListObjectType.TRUE);
        } else {
            setType(PListObjectType.FALSE);
        }
    }

    public boolean isTrue() {
        return isTrue;
    }
}
