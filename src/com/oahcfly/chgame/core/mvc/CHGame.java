
package com.oahcfly.chgame.core.mvc;

import java.util.HashMap;
import java.util.Locale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.FreeTypeFontGeneratorExt;
import com.oahcfly.chgame.core.International;
import com.oahcfly.chgame.core.Version;
import com.oahcfly.chgame.core.ad.CHADListener;
import com.oahcfly.chgame.core.assetmanager.CHAssets;
import com.oahcfly.chgame.core.listener.LocaleListener;
import com.oahcfly.chgame.core.manager.MusicManager;
import com.oahcfly.chgame.core.manager.SoundManager;
import com.oahcfly.chgame.core.transition.ScreenTransition;
import com.oahcfly.chgame.core.ui.CHLoading;

/**
 * 
 * <pre>
 * 【Control层】
 * 游戏核心控制类
 * 
 * date: 2014-10-24
 * </pre>
 * @author caohao
 */
public abstract class CHGame extends Game {
    private FreeTypeFontGeneratorExt generator;

    private HashMap<String, FileHandle> ttfMap = new HashMap<String, FileHandle>();

    private static CHGame instance = null;

    @SuppressWarnings("unchecked")
    public static <T extends CHGame> T getInstance() {
        return (T)instance;
    }

    private CHADListener chadListener;

    private boolean debug;

    /**资源加载管理器*/
    private AssetManager assetManager;

    public int gameWidth = 800;

    public int gameHeight = 480;

    /** 初始化 */
    public abstract void init();

    private boolean openFPS = false;

    //private SpriteBatch spriteBatch;

    private Label fpsLabel;

    private BitmapFont fontNumber, fontMenu;

    private Preferences preferences;

    private CHAssets chAssets;

    public CHGame() {

    }

    public CHGame(CHADListener adListener) {
        this(adListener, null);
    }

    public CHGame(CHADListener adListener, LocaleListener localeListener) {
        this.chadListener = adListener;
        this.localeListener = localeListener;
    }

    /**
     * 是否绘制FPS
     * 
     * @param showFPS
     */
    public void setFPS(boolean showFPS) {
        openFPS = showFPS;
    }

    private MusicManager musicManager;

    private SoundManager soundManager;

    private Array<TextureRegion> loadingKeyFrames = new Array<TextureRegion>();

    // 国际化语言
    private International international;

    private LocaleListener localeListener;

    public final static String TAG = "CHGame";

    @Override
    public void create() {
        Gdx.app.log(TAG, "version : " + Version.VERSION);

        // 初始化字体方案
        generator = new FreeTypeFontGeneratorExt(30, false);
        FileHandle baseFileHandle = Gdx.files.internal("values/strings");
        if (localeListener != null && baseFileHandle.name() != null) {
            // 读取国际化资源
            Locale locale = localeListener.getLocale();
            international = International.createBundle(baseFileHandle, locale);
            // end
        } else {
            Gdx.app.error(TAG, "not find values/strings in asset");
        }

        musicManager = new MusicManager();
        soundManager = new SoundManager();

        chAssets = new CHAssets();
        assetManager = chAssets.getAssetManager();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        instance = this;

        setPreferences(Gdx.app.getPreferences("chdata"));

        //spriteBatch = new SpriteBatch();

        try {
            fontNumber = new BitmapFont(Gdx.files.classpath("com/oahcfly/chgame/font/number.fnt"),
                    Gdx.files.classpath("com/oahcfly/chgame/font/number.png"), false, true);
        } catch (Exception e) {
            fontNumber = new BitmapFont();
            Gdx.app.error("chgame", "fontNumber load error :" + e.getMessage());
        }

        try {
            fontMenu = new BitmapFont(Gdx.files.classpath("com/oahcfly/chgame/font/menu.fnt"),
                    Gdx.files.classpath("com/oahcfly/chgame/font/menu.png"), false, true);
        } catch (Exception e) {
            fontMenu = new BitmapFont();
            Gdx.app.error("chgame", "fontMenu load error :" + e.getMessage());
        }

        fontNumber.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fontMenu.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        LabelStyle style = new LabelStyle(fontMenu, fontMenu.getColor());

        fpsLabel = new Label("FPS", style);
        fpsLabel.setY(20);
        fpsLabel.setX(10);

        for (int i = 0; i < 6; i++) {
            FileHandle fileHandle = Gdx.files.classpath(String.format("com/oahcfly/chgame/res/l_%d.png", i));
            Texture texture = new Texture(fileHandle);
            loadingKeyFrames.add(new TextureRegion(texture));
        }
        chLoading = new CHLoading();
        init();
    }

    private CHLoading chLoading;

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
        //spriteBatch.dispose();
        fontMenu.dispose();
        fontNumber.dispose();
        musicManager.dispose();
        soundManager.dispose();
        chAssets.dispose();
    }

    @Override
    public CHScreen getScreen() {
        // TODO Auto-generated method stub
        return (CHScreen)super.getScreen();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        super.pause();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        if (openFPS) {
            Batch spriteBatch = getScreen().getStage().getBatch();
            // 初始要有begin起始
            spriteBatch.begin();
            // 显示文字到屏幕指定位置
            fpsLabel.setText("FPS :" + Gdx.graphics.getFramesPerSecond() + ",M:"
                    + (Gdx.app.getJavaHeap() / (1024 * 1024)) + ",TS:" + Texture.getNumManagedTextures());
            fpsLabel.draw(spriteBatch, 1);

            //            String text = "FPS:" + Math.min(Gdx.graphics.getFramesPerSecond(), 60) + "\nJHeap:" + Gdx.app.getJavaHeap()
            //                    / 1024 / 1204 + "M" + "\nNHeap:" + Gdx.app.getNativeHeap() / 1024 / 1024 + "M";
            //
            //            //don not follow the main camera . 
            //            getMenuFont().drawMultiLine(spriteBatch, text, 0,   200);
            // 结束要有end结尾
            spriteBatch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        super.resize(width, height);
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        super.resume();
    }

    @Override
    public void setScreen(Screen screen) {
        Gdx.input.setInputProcessor(null);
        super.setScreen(screen);
    }

    /**
     * 
     * <pre>
     * 切换Screen
     * 
     * date: 2014-10-24
     * </pre>
     * @author caohao
     * @param screen
     * @param transition
     */
    public void setScreen(CHScreen toScreen, ScreenTransition transition) {
        Gdx.input.setInputProcessor(null);
        transition.transition((CHScreen)this.getScreen(), toScreen);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * 
     * <pre>
     *   
     * 
     * date: 2014-10-24
     * </pre>
     * @author caohao
     * @return 数字字体
     */
    public BitmapFont getNumberFont() {
        return fontNumber;
    }

    /**
     * 
     * <pre>
     * 
     * date: 2014-10-24
     * </pre>
     * @author caohao
     * @return 适用于菜单的字体
     */
    public BitmapFont getMenuFont() {
        return fontMenu;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    private void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public CHADListener getADListener() {
        return this.chadListener;
    }

    /**
     * 
     * <pre>
     * 读取纹理，自动缓存到AssetManager
     * 
     * date: 2014-12-23
     * </pre>
     * @author caohao
     * @param fileName
     * @return
     */
    public Texture getTexture(String fileName) {
        Texture texture = null;

        if (!assetManager.isLoaded(fileName)) {
            assetManager.load(fileName, Texture.class);
            assetManager.finishLoading();
            texture = assetManager.get(fileName, Texture.class);
        } else {
            texture = assetManager.get(fileName, Texture.class);
        }
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        return texture;
    }

    /**
     * 
     * <pre>
     * 根据pixmap生成纹理
     * 
     * date: 2014-12-23
     * </pre>
     * @author caohao
     * @param pixmap
     * @return
     */
    public Texture getTexture(Pixmap pixmap) {
        Texture texture = new Texture(pixmap);
        // 抗锯齿
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        return texture;
    }

    /**
     * 
     * <pre>
     * 生成Image对象
     * 
     * date: 2014-12-23
     * </pre>
     * @author caohao
     * @param path
     * @return
     */
    public Image getImage(String path) {
        return new Image(getTexture(path));
    }

    public void setGameWidthAndHeight(int width, int height) {
        gameWidth = width;
        gameHeight = height;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public <T> void loadAsset(String fileName, Class<T> type) {
        assetManager.load(fileName, type);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void showSpotADs() {
        if (this.chadListener != null) {
            this.chadListener.showSpotAds();
        }
    }

    public void showBannerADs() {
        if (this.chadListener != null) {
            this.chadListener.showBannerAds();
        }
    }

    public void closeBannerADs() {
        if (this.chadListener != null) {
            this.chadListener.closeBannerAds();
        }
    }

    public void closeShopADs() {
        if (this.chadListener != null) {
            this.chadListener.closeSpotAds();
        }
    }

    public CHAssets getCHAssets() {
        return chAssets;
    }

    public <T extends CHModel> T getModel() {
        return getScreen().getModel();
    }

    /**
     * 
     * <pre>
     * key = x.ttf
     * value=filehandler
     * date: 2014-12-20
     * </pre>
     * @author caohao
     * @return
     */
    public HashMap<String, FileHandle> getTTFMap() {
        return ttfMap;
    }

    public void addTTF(String filePath) {
        int begin = filePath.lastIndexOf("/");
        ttfMap.put(filePath.substring(begin), Gdx.files.internal(filePath));
        System.out.println("ttf:" + ttfMap.toString());
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public Array<TextureRegion> getLoadingKeyFrames() {
        return loadingKeyFrames;
    }

    /**
     * 
     * <pre>
     * 显示等待界面
     * 
     * date: 2014-12-22
     * </pre>
     * @author caohao
     */
    public void showLoadingUI() {
        chLoading.show();
    }

    /**
     * 
     * <pre>
     * 关闭等待界面
     * 
     * date: 2014-12-22
     * </pre>
     * @author caohao
     */
    public void closeLoadingUI() {
        chLoading.dismiss();
    }

    /**
     * 
     * <pre>
     * 多国语言
     * 
     * date: 2014-12-26
     * </pre>
     * @author caohao
     * @return
     */
    public International getInternational() {
        return international;
    }

    /**
     * 
     * <pre>
     * 系统默认字体生成器
     * 
     * date: 2014-12-26
     * </pre>
     * @author caohao
     * @return
     */
    public FreeTypeFontGeneratorExt getInternationalGenerator() {
        return generator;
    }

}
