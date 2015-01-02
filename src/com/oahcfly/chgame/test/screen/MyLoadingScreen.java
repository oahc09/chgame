package com.oahcfly.chgame.test.screen;

import com.oahcfly.chgame.core.ui.CHLoadingScreen;

public class MyLoadingScreen extends CHLoadingScreen{

    public MyLoadingScreen() {
        super(new PPLScreen());
        // TODO Auto-generated constructor stub
    }

    @Override
    public void loadAssetFile() {
         getCHAssets().loadAssetFile("asset.ch");
         getCHAssets().loadAllGroup();
    }

}
