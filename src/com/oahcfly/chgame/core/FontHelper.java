
package com.oahcfly.chgame.core;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * 
 * <pre>
 * 字体工具类
 * 
 * date: 2014-5-11
 * </pre>
 * @author caohao
 */
public class FontHelper {
    private FontHelper() {
    }

    private static FontHelper instance;

    public static FontHelper getInstance() {
        if (instance == null) {
            instance = new FontHelper();
        }
        return instance;
    }

    private HashMap<String, FreeTypeFontGenerator> generatorMap = new HashMap<String, FreeTypeFontGenerator>();

    public BitmapFont loadTtfFont(String ttfFilePath, int size, String chineseExtraStr) {
        FreeTypeFontGenerator generator = null;
        if (generatorMap.containsKey(ttfFilePath)) {
            generator = generatorMap.get(ttfFilePath);
        } else {
            generator = new FreeTypeFontGenerator(Gdx.files.internal(ttfFilePath));
        }
        
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + chineseExtraStr;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        generator.dispose();
        return font;
    }

    public BitmapFont loadFntFont(String fntFilePath) {
        AssetManager assetManager = CHGame.getInstance().getAssetManager();
        if (assetManager.isLoaded(fntFilePath)) {
            return assetManager.get(fntFilePath, BitmapFont.class);
        }
        assetManager.load(fntFilePath, BitmapFont.class);
        assetManager.finishLoading();
        BitmapFont bitmapFont =assetManager.get(fntFilePath);
        bitmapFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        return bitmapFont;
    }
}
