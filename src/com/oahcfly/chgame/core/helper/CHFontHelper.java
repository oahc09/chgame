
package com.oahcfly.chgame.core.helper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.util.StringUtil;

/**
 * 
 * <pre>
 * 字体工具类
 * 注：每次createFont，一定要把上次create的字体dispose。
 * date: 2014-5-11
 * </pre>
 * @author caohao
 */
public class CHFontHelper {
    private CHFontHelper() {
    }

    private static CHFontHelper instance;

    public static CHFontHelper getInstance() {
        if (instance == null) {
            instance = new CHFontHelper();
        }
        return instance;
    }

    private HashMap<String, FreeTypeFontGenerator> generatorMap = new HashMap<String, FreeTypeFontGenerator>();

    /**
     * 
     * <pre>
     *  读取系统字体
     * 
     * date: 2015-4-19
     * </pre>
     * @author caohao
     * @param fontSize
     * @param text
     * @return
     */
    public BitmapFont loadSysFont(int fontSize, String text) {
        FileHandle fontHandle = readSysTtfConfig();
        if (fontHandle == null) {
            throw new GdxRuntimeException("can not find system TTF font file !!!");
        }
        return loadTtfFont(fontHandle, fontSize, text);
    }

    /**
     * 
     * <pre>
     *  加载TTF字体
     * 
     * date: 2015-4-19
     * </pre>
     * @author caohao
     * @param fontHandle
     * @param fontSize
     * @param text
     * @return
     */
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

    private Map<FileHandle, FreeTypeFontGenerator> generators = new HashMap<FileHandle, FreeTypeFontGenerator>();

    private BitmapFont createFont(FileHandle fontHandle, String text, int fontSize) {
        if (fontHandle == null) {
            return CHGame.getInstance().getDefaultBitmapFont();
        }

        BitmapFont font = null;
        try {
            FreeTypeFontGenerator generator = (FreeTypeFontGenerator)generators.get(fontHandle);
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

    public void dispose() {
        for (FreeTypeFontGenerator generator : generatorMap.values()) {
            if (generator != null) {
                generator.dispose();
            }
        }
        for (FreeTypeFontGenerator generator : generators.values()) {
            if (generator != null) {
                generator.dispose();
            }
        }
        this.generatorMap.clear();
        generators.clear();

    }

    /**
    * @author hx 读取安卓系统的ttf配置文件
    * @return TTFfamily
    * */
    private FileHandle readSysTtfConfig() {

        Properties props = System.getProperties(); // 获得系统属性集
        String osName = props.getProperty("os.name"); // 操作系统名称
        if (osName.toLowerCase().contains("mac")) {
            FileHandle fh = Gdx.files.absolute("/System/Library/Fonts/DroidSansFallback.ttf");
            if (fh.exists()) {
                return fh;
            } else {
                throw new GdxRuntimeException("mac下我没有实现，需要的话自己实现，或者把DroidSansFallback.ttf放到 /System/Library/Fonts下");
            }
        } else if (osName.toLowerCase().contains("windows")) {
            FileHandle fh = Gdx.files.absolute("C:\\Windows\\Fonts\\DroidSansFallback.ttf");
            if (fh.exists()) {
                return fh;
            } else {
                throw new GdxRuntimeException("window下我没有实现，需要的话自己实现，或者把DroidSansFallback.ttf放到 windows/fonts下");
            }
        } else {
            try {
                FileHandle f = Gdx.files.absolute("/system/etc/fallback_fonts.xml");

                if (f.exists()) {
                    return new PullTtfPaser().parse(f.read());
                } else {

                    // System.out.println("不存在");
                    // Plugin.i("fallback_fonts.xml 不存在..");

                    FileHandle ff = Gdx.files.absolute("/system/fonts/");
                    FileHandle[] ffx = ff.list();
                    if (ffx.length <= 0) {
                        // Plugin.i("直接读取/system/fonts/下的文件..");
                        String[] defultFonts = {
                                "/system/fonts/DroidSansFallback.ttf", "/system/fonts/DroidSans-Bold.ttf",
                                "/system/fonts/DroidSans.ttf", "/system/fonts/DroidSansMono.ttf",
                                "/system/fonts/DroidSerif-Bold.ttf", "/system/fonts/DroidSerif-BoldItalic.ttf",
                                "/system/fonts/DroidSerif-Italic.ttf", "system/fonts/DroidSerif-Regular.ttf"

                        };
                        for (String path : defultFonts) {
                            FileHandle fh = Gdx.files.absolute(path);
                            if (fh.exists()) {
                                return fh;
                            }
                        }
                    } else {
                        // Plugin.i("file list[]读取/system/fonts/下的文件..");
                        for (FileHandle fhd : ffx) {
                            if (fhd != null) {
                                String name = fhd.name();
                                String ext = name.substring(name.lastIndexOf(".") + 1);
                                if (fhd.exists() && ext != null && "ttf".equalsIgnoreCase(ext.toLowerCase())) {
                                    return fhd;
                                }
                            }
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public class PullTtfPaser {
        String abspath = "/system/fonts/";

        public FileHandle parse(InputStream is) throws Exception {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            Element rootElement = doc.getDocumentElement();
            NodeList items = rootElement.getElementsByTagName("file");
            for (int i = 0; i < items.getLength(); i++) {

                Node item = items.item(i);
                String filename = item.getFirstChild().getTextContent();
                if (filename == null || "".equals(filename.trim())) {
                    continue;
                } else {
                    FileHandle fh = Gdx.files.absolute(abspath + filename);
                    if (fh.exists()) {
                        return fh;
                    }

                }
            }
            return null;
        }
    }
}
