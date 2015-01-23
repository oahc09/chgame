
package com.oahcfly.chgame.core.helper;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.util.StringUtil;

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

    public BitmapFont loadTtfFont(FileHandle fontHandle, int fontSize, String text) {
        return createFont(fontHandle, text, fontSize);
    }

    /**
     * 
     * <pre>
     * 加载TTF字体
     * 
     * date: 2014-12-3
     * </pre>
     * @author caohao
     * @param ttfFilePath
     * @param size
     * @param chineseExtraStr
     * @return
     */
    public BitmapFont loadTtfFont(String ttfFilePath, int size, String chineseExtraStr) {
        FreeTypeFontGenerator generator = null;
        if (generatorMap.containsKey(ttfFilePath)) {
            generator = generatorMap.get(ttfFilePath);
        } else {
            generator = new FreeTypeFontGenerator(Gdx.files.internal(ttfFilePath));
        }
        // 字符去重
        char[] array = chineseExtraStr.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : array) {
            if (-1 == stringBuffer.indexOf(String.valueOf(c))) {
                stringBuffer.append(c);
            }
        }

        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;

        String newText = StringUtil.removeRepeatedChar(FreeTypeFontGenerator.DEFAULT_CHARS + stringBuffer.toString());
        parameter.characters = newText;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        generator.dispose();
        return font;
    }

    /**
     * 
     * <pre>
     * 加载FNT字体
     * 
     * date: 2014-12-3
     * </pre>
     * @author caohao
     * @param fntFilePath
     * @return
     */
    public BitmapFont loadFntFont(String fntFilePath) {
        AssetManager assetManager = CHGame.getInstance().getAssetManager();
        if (assetManager.isLoaded(fntFilePath)) {
            return assetManager.get(fntFilePath, BitmapFont.class);
        }
        assetManager.load(fntFilePath, BitmapFont.class);
        assetManager.finishLoading();
        BitmapFont bitmapFont = assetManager.get(fntFilePath);
        bitmapFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        return bitmapFont;
    }

    private static FreeTypeFontGenerator generator;

    private static Map<FileHandle, FreeTypeFontGenerator> generators = new HashMap<FileHandle, FreeTypeFontGenerator>();

    public static BitmapFont createFont(FileHandle fontHandle, String text, int fontSize) {
        if (fontHandle == null) {
            return CHGame.getInstance().getDefaultBitmapFont();
        }

        BitmapFont font = null;
        try {
            generator = (FreeTypeFontGenerator)generators.get(fontHandle);
            if (generator == null) {
                generator = new FreeTypeFontGenerator(fontHandle);
                generators.put(fontHandle, generator);
            }

            String newText = StringUtil.removeRepeatedChar(FreeTypeFontGenerator.DEFAULT_CHARS + text);

            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = fontSize;
            parameter.characters = newText;
            font = generator.generateFont(parameter);
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        } catch (Exception e) {
            Gdx.app.error("FontUtil", e.getMessage());
        }

        if (font == null) {
            return CHGame.getInstance().getDefaultBitmapFont();
        }

        return font;
    }
}
