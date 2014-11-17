package com.oahcfly.chgame.plist.element;

public class PString extends PListObject{

    /**
     * 
     */
    private static final long serialVersionUID = -877824542076661889L;

    private String text;
    
    public PString(String text){
        setType(PListObjectType.STRING);
        setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
