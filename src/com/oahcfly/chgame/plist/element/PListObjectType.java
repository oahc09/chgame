
package com.oahcfly.chgame.plist.element;

public enum PListObjectType {
    ARRAY(0), DATA(1), DATE(2), DICT(3), REAL(4), INTEGER(5), STRING(6), TRUE(7), FALSE(8);

    private int type;

    private PListObjectType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
