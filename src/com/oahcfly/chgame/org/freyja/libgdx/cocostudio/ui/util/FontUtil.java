
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtil {
    static FreeTypeFontGenerator generator;

    static Map<FileHandle, FreeTypeFontGenerator> generators = new HashMap<FileHandle, FreeTypeFontGenerator>();

    public static BitmapFont createFont(FileHandle fontHandle, String text, int fontSize) {
        if (fontHandle == null) {
            return new BitmapFont();
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
            return new BitmapFont();
        }

        return font;
    }

}
