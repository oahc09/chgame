
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCExport;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCOption;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.model.CCWidget;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group.CCButton;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group.CCCheckBox;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group.CCLabelAtlas;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group.CCPanel;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.group.CCScrollView;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget.CCImageView;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget.CCLabel;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget.CCLabelBMFont;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget.CCLoadingBar;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget.CCSlider;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.parser.widget.CCTextField;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.TTFLabelStyle;

/**
 * CocoStudio ui 解析器.根据CocoStudio的ui编辑器生成的json文件,创建出一个对应Group.
 * 本解析器还处于初级阶段,部分控件与属性不支持.
 * 
 * @author i see
 * @email 121077313@qq.com
 * @wiki https://github.com/121077313/cocostudio-ui-libgdx/wiki
 * @tip https://github.com/121077313/cocostudio-ui-libgdx/wiki/疑难解答
 */
public class CocoStudioUIEditor {

    /** json文件所在目录 */
    protected String dirName;

    /** 所有纹理 */
    protected Collection<TextureAtlas> textureAtlas;

    /** 控件集合 */
    protected Map<String, Array<Actor>> actors;

    /** 字体集合 */
    protected Map<String, FileHandle> ttfs;

    /** BitmapFont集合,key:font.fnt */
    protected Map<String, BitmapFont> bitmapFonts;

    /** 导出的json结构 */
    protected CCExport export;

    protected Map<String, BaseWidgetParser> parsers;

    String tag = CocoStudioUIEditor.class.getSimpleName();

    /** 添加转换器 */
    public void addParser(BaseWidgetParser parser) {
        parsers.put(parser.getClassName(), parser);
    }

    /** 默认ttf字体文件 */
    protected FileHandle defaultFont;

    private BitmapFont defaultBitmapFont;

    /**
     * 不需要显示文字
     * 
     * @param jsonFile
     * @param textureAtlas
     *            资源文件,传入 null表示使用小文件方式加载图片
     */
    public CocoStudioUIEditor(FileHandle jsonFile, Collection<TextureAtlas> textureAtlas) {
        this(jsonFile, null, null, null, textureAtlas);
    }

    long starttime = System.currentTimeMillis();

    /**
     * 
     * @param jsonFile
     *            ui编辑成生成的json文件
     * @param textureAtlas
     *            资源文件,传入 null表示使用小文件方式加载图片.
     * 
     * @param ttfs
     *            字体文件集合
     * @param bitmapFonts
     *            自定义字体文件集合
     * @param defaultFont
     *            默认ttf字体文件
     */
    public CocoStudioUIEditor(FileHandle jsonFile, Map<String, FileHandle> ttfs, Map<String, BitmapFont> bitmapFonts,
            FileHandle defaultFont, Collection<TextureAtlas> textureAtlas) {
        this.textureAtlas = textureAtlas;
        this.ttfs = ttfs;
        this.bitmapFonts = bitmapFonts;
        this.defaultFont = defaultFont;
        parsers = new HashMap<String, BaseWidgetParser>();

        addParser(new CCButton());
        addParser(new CCCheckBox());
        addParser(new CCImageView());
        addParser(new CCLabel());
        addParser(new CCLabelBMFont());
        addParser(new CCPanel());
        addParser(new CCScrollView());
        addParser(new CCTextField());
        addParser(new CCLoadingBar());
        addParser(new CCLabelAtlas());
        addParser(new CCSlider());

        actors = new HashMap<String, Array<Actor>>();

        dirName = jsonFile.parent().toString();

        tag += "-" + jsonFile.name();

        if (!dirName.equals("")) {
            dirName += "/";
        }
        String json = jsonFile.readString("utf-8");
        Json jj = new Json();
        jj.setIgnoreUnknownFields(true);
        export = jj.fromJson(CCExport.class, json);

        defaultBitmapFont = CHGame.getInstance().getDefaultBitmapFont();
    }

    /**
     * 根据json文件创建并返回Group
     * 
     * @return
     */
    public Group createGroup() {
        Actor actor = parseWidget(null, export.getWidgetTree());

        //parseAction();

        Group group = (Group)actor;
        group.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });

        Gdx.app.debug(tag, " ui loadtime :" + (System.currentTimeMillis() - starttime));
        return group;
    }

    protected TextureRegion findRegion(String name) {
        for (TextureAtlas ta : textureAtlas) {
            TextureRegion tr = ta.findRegion(name);
            if (tr != null) {
                return tr;
            }
        }
        return null;
    }

    protected TextureRegion findRegion(String name, int index) {
        for (TextureAtlas ta : textureAtlas) {
            TextureRegion tr = ta.findRegion(name, index);
            if (tr != null) {
                return tr;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String[] arr = "".split("\\/");
        System.out.println(arr.length);
    }

    /**
     * 获取材质
     * 
     * @param option
     * @param name
     * @return
     */
    public TextureRegion findTextureRegion(CCOption option, String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        TextureRegion tr = null;
        if (textureAtlas == null || textureAtlas.size() == 0) {// 不使用合并纹理
            tr = new TextureRegion(getTexture(dirName + name));
        } else {

            try {
                String[] arr = name.split("\\/");
                if (arr.length == 1) {
                    // support same folder with json file
                    // add by @xiaozc

                    name = name.substring(0, name.length() - 4);
                } else {
                    name = name.substring(arr[0].length() + 1, name.length() - 4);
                }
            } catch (Exception e) {
                error(option, "资源名称不符合约定,无法解析.请查看github项目wiki第十条");
            }

            // 考虑index下标

            if (name.indexOf("_") == -1) {
                tr = findRegion(name);
            } else {
                try {
                    int length = name.lastIndexOf("_");

                    Integer index = Integer.parseInt(name.substring(length + 1, name.length()));
                    // 这里可能报错,属于正常,因为会出现 xx_xx名字的资源而不是xx_2这种

                    name = name.substring(0, length);

                    tr = findRegion(name, index);

                } catch (Exception e) {
                    tr = findRegion(name);
                }
            }
        }
        if (tr == null) {
            debug(option, "找不到纹理");
        }

        if (option.isFlipX() || option.isFlipY()) {

            if (textureAtlas == null) {
                tr.flip(option.isFlipX(), option.isFlipY());
            } else {
                tr = new TextureRegion(tr);
                tr.flip(option.isFlipX(), option.isFlipY());
            }
        }

        return tr;
    }

    /**
     * .9文件生成
     * 
     * @param option
     * @param name
     * @author wujj
     * @return
     */
    public NinePatch findNinePatch(CCOption option, String name) {
        if (name == null || name.equals("")) {
            return null;
        }

        NinePatch tr = null;
        if (textureAtlas == null || textureAtlas.size() == 0) {// 不使用合并纹理
            tr = new NinePatch(getTexture((dirName + name)), option.getCapInsetsX(), option.getCapInsetsX()
                    + option.getCapInsetsWidth(), option.getCapInsetsY(), option.getCapInsetsY()
                    + option.getCapInsetsHeight());
        } else {
            name = name.substring(0, name.indexOf("."));
            // 考虑index下标

            if (name.indexOf("_") == -1) {
                for (TextureAtlas atlas : textureAtlas) {
                    tr = atlas.createPatch(name);
                    if (tr != null) {
                        break;
                    }
                }
            } else {
                try {
                    // 不支持同名索引查找
                    int length = name.lastIndexOf("_");

                    name = name.substring(0, length);
                    for (TextureAtlas atlas : textureAtlas) {
                        tr = atlas.createPatch(name);
                        if (tr != null) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    for (TextureAtlas atlas : textureAtlas) {
                        tr = atlas.createPatch(name);
                        if (tr != null) {
                            break;
                        }
                    }
                }
            }
        }
        if (tr == null) {
            debug(option, "找不到纹理");
        }
        // 不支持翻转和镜像
        return tr;
    }

    public Texture findTexture(String name) {
        return getTexture(dirName + name);
    }

    public Drawable findDrawable(CCOption option, String name) {

        if (option.isScale9Enable()) {// 九宫格支持
            NinePatch np = findNinePatch(option, name);
            if (np == null) {
                return null;
            }
            return new NinePatchDrawable(np);
        }

        TextureRegion tr = findTextureRegion(option, name);
        if (tr == null) {
            return null;
        }

        return new TextureRegionDrawable(tr);
    }

    public void debug(String message) {
        Gdx.app.debug(tag, message);
    }

    public void debug(CCOption option, String message) {
        Gdx.app.debug(tag, "控件: " + option.getName() + " " + message);
    }

    public void error(String message) {
        Gdx.app.error(tag, message);
    }

    public void error(CCOption option, String message) {
        Gdx.app.error(tag, "控件: " + option.getName() + " " + message);
    }

    /***
     * 解析节点,创建控件
     * 
     * @param node
     * @return
     */
    public Actor parseWidget(Group parent, CCWidget widget) {

        long starttime = System.currentTimeMillis();

        CCOption option = widget.getOptions();
        String className = option.getClassname();
        BaseWidgetParser parser = parsers.get(className);

        if (parser == null) {
            debug(option, "not support Widget:" + className);
            return null;
        }

        Actor actor = parser.parse(this, widget, option);

        Gdx.app.debug(tag, " parse widget step_1 " + parser.getClassName() + ":"
                + (System.currentTimeMillis() - starttime));
        starttime = System.currentTimeMillis();

        actor = parser.commonParse(this, widget, option, parent, actor);

        Gdx.app.debug(tag, " parse widget step_2 " + actor.getName() + ":" + (System.currentTimeMillis() - starttime));

        return actor;
    }

    /**
     * 创建LabelStyle
     * 
     * @param option
     * @return
     */
    public TTFLabelStyle createLabelStyle(CCOption option) {

        FileHandle fontFile = null;
        if (ttfs != null) {
            fontFile = ttfs.get(option.getFontName());
        }

        if (fontFile == null) {// 使用默认字体文件
            fontFile = defaultFont;
        }

        Color textColor = null;

        if (option.getTextColorB() == 0 & option.getTextColorG() == 0 && option.getTextColorR() == 0) {

            textColor = new Color(option.getColorR() / 255.0f, option.getColorG() / 255.0f,
                    option.getColorB() / 255.0f, option.getOpacity() / 255.0f);

        } else {

            textColor = new Color(option.getTextColorR() / 255.0f, option.getTextColorG() / 255.0f,
                    option.getTextColorB() / 255.0f, option.getOpacity() / 255.0f);

        }

        if (fontFile == null) {
            debug(option, "ttf字体:" + option.getFontName() + " 不存在,使用系统默认字体");
        }

        BitmapFont font = defaultBitmapFont;//FontUtil.createFont(fontFile, option.getText(), option.getFontSize());

        return new TTFLabelStyle(new LabelStyle(font, textColor), fontFile, option.getFontSize());
    }

    public Map<String, Array<Actor>> getActors() {
        return actors;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public Map<String, FileHandle> getTtfs() {
        return ttfs;
    }

    public void setTtfs(Map<String, FileHandle> ttfs) {
        this.ttfs = ttfs;
    }

    public Map<String, BitmapFont> getBitmapFonts() {
        return bitmapFonts;
    }

    public void setBitmapFonts(Map<String, BitmapFont> bitmapFonts) {
        this.bitmapFonts = bitmapFonts;
    }

    public void setActors(Map<String, Array<Actor>> actors) {
        this.actors = actors;
    }

    private Texture getTexture(String path) {
        Gdx.app.debug("Game", "getTexture:" + path);
        return CHGame.getInstance().getTexture(path);
    }

    public Texture getTexture(Pixmap pixmap) {
        Texture texture = CHGame.getInstance().getTexture(pixmap);
        // 抗锯齿
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        return texture;
    }

    public void clear() {
        this.actors = null;
        this.bitmapFonts = null;
        this.parsers = null;
        this.textureAtlas = null;
        this.ttfs = null;

    }
}
