
package com.oahcfly.chgame.test.screen;

import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.ui.CHLoadingScreen;

public class MyLoadingScreen extends CHLoadingScreen {

    @Override
    public void loadAssetFile() {
        getCHAssets().loadAssetFile("asset.ch");
        getCHAssets().loadAllGroup();
    }

    @Override
    public void changeToNextScreen() {
        CHGame.getInstance().setScreen(new PPLScreen());
    }

}
