
package com.oahcfly.chgame.test.mvc;

import com.oahcfly.chgame.core.CHModel;
import com.oahcfly.chgame.core.CHScreen;

public class AScreen extends CHScreen {

    @Override
    public void initScreen() {
        setModel(new CHModel(this) {
        });
        AModel aModel = getModel();
        
    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

}
