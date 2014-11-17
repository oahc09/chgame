
package com.oahcfly.chgame.plist.element;

import java.util.Stack;

public class PList {

    public final String TAG = "PList";

    Stack<PListObject> stack;

    public PList() {
        stack = new Stack<PListObject>();
    }

    public void addDict(PDict dict) {
          stack.add(dict);
    }

    public Stack<PListObject> getStack() {
        return stack;
    }
    
 
}
