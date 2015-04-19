
package com.oahcfly.chgame.core.mvc;

import java.util.HashMap;
import java.util.Locale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.International;
import com.oahcfly.chgame.core.Version;
import com.oahcfly.chgame.core.ad.CHADListener;
import com.oahcfly.chgame.core.async.CHAsyncManager;
import com.oahcfly.chgame.core.helper.CHFontHelper;
import com.oahcfly.chgame.core.listener.CHGameInfoListener;
import com.oahcfly.chgame.core.listener.CHSocialListener;
import com.oahcfly.chgame.core.listener.LocaleListener;
import com.oahcfly.chgame.core.manager.MusicManager;
import com.oahcfly.chgame.core.manager.SoundManager;
import com.oahcfly.chgame.core.transition.IScreenTransition;
import com.oahcfly.chgame.core.ui.CHWaiting;

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
    // 场景切换
    private FrameBuffer curFbo;

    private FrameBuffer nextFbo;

    private float transitonDeltaTime;

    private IScreenTransition screenTransition;

    private boolean initTransition;

    private CHScreen curScreen;

    private CHScreen nextScreen;

    // 场景切换

    private HashMap<String, FileHandle> ttfMap = new HashMap<String, FileHandle>();

    private CHADListener chadListener;

    private CHSocialListener socialListener;

    private CHGameInfoListener gameInfoListener;

    private boolean debug;

    /**资源加载管理器*/
    private AssetManager assetManager;

    public int gameWidth = 800;

    public int gameHeight = 480;

    /** 初始化 */
    public abstract void init();

    private boolean openFPS = false;

    private Label fpsLabel;

    private static CHGame instance = null;

    // 异步任务管理者
    private CHAsyncManager chAsyncManager;

    private HashMap<String, CHScreen> chscreenMap;

    @SuppressWarnings("unchecked")
    public static <T extends CHGame> T getInstance() {
        return (T)instance;
    }

    public CHGame() {
    }

    public CHGame(CHADListener adListener) {
        this(adListener, null);
    }

    public CHGame(CHADListener adListener, LocaleListener localeListener) {
        this.chadListener = adListener;
        this.localeListener = localeListener;
    }

    /**音乐管理*/
    private MusicManager musicManager;

    /**音效管理*/
    private SoundManager soundManager;

    private Array<TextureRegion> loadingKeyFrames = new Array<TextureRegion>();

    // 国际化语言
    private International international;

    private LocaleListener localeListener;

    public final static String TAG = "CHGame";

    private CHWaiting chLoading;

    private SpriteBatch batch;

    // Gdx是否处理back键
    private boolean catchBackKey = true;

    @Override
    public void create() {
        // batch = new SpriteBatch();

        Gdx.app.log(TAG, "version : " + Version.VERSION);

        chscreenMap = new HashMap<String, CHScreen>();
        musicManager = new MusicManager();
        soundManager = new SoundManager();

        assetManager = new AssetManager();

        chAsyncManager = new CHAsyncManager();

        // 代表返回，菜单事件，会被Gdx拦截掉，不会下发到Android处理了。
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        instance = this;

        init();

    }

    public void addScreen(CHScreen chScreen) {
        chscreenMap.put(chScreen.getClass().getName(), chScreen);
    }

    /**
     * 
     * <pre>
     * 读取多语言
     * 
     * date: 2015-1-2
     * </pre>
     * @author caohao
     */
    private void loadInternationalValues() {
        FileHandle baseFileHandle = Gdx.files.internal("values/strings");
        if (baseFileHandle.name() != null) {
            // 读取国际化资源
            Locale locale = new Locale("chgame");
            if (localeListener != null) {
                locale = localeListener.getLocale();
            }
            international = International.createBundle(baseFileHandle, locale);
            // 创建国际化方案里的当前语言的所有字符纹理  太耗时了。。。。
            //  generator.appendToFont(international.getVaulesString());
            // end
        } else {
            Gdx.app.error(TAG, "not find values/strings in asset");
        }
    }

    /**
     * 是否绘制FPS
     * 
     * @param showFPS
     */
    public void setFPS(boolean showFPS) {
        openFPS = showFPS;
    }

    @Override
    public void dispose() {
        if (curScreen != null)
            curScreen.hide();
        if (nextScreen != null)
            nextScreen.hide();
        if (initTransition) {
            curFbo.dispose();
            curScreen = null;
            nextFbo.dispose();
            nextScreen = null;
            batch.dispose();
            initTransition = false;
        }

        Gdx.app.debug(TAG, "dispose");

        for (TextureRegion textureRegion : this.loadingKeyFrames) {
            textureRegion.getTexture().dispose();
        }

        musicManager.dispose();
        soundManager.dispose();
        assetManager.dispose();
        if (bitmapFont != null) {
            bitmapFont.dispose();
        }
        chAsyncManager.dispose();

        CHFontHelper.getInstance().dispose();
    }

    @Override
    public CHScreen getScreen() {
        // TODO Auto-generated method stub
        return curScreen;
    }

    @Override
    public void pause() {
        savaDataBeforeExit();
        if (curScreen != null)
            curScreen.pause();
    }

    @Override
    public void render() {
        //   清屏
        // Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
        if (nextScreen == null) {
            // no ongoing transition
            if (curScreen != null)
                // 正常绘制
                curScreen.render(deltaTime);
        } else {
            // ongoing transition
            float duration = 0;
            if (screenTransition != null)
                duration = screenTransition.getDuration();
            // update progress of ongoing transition
            transitonDeltaTime = Math.min(transitonDeltaTime + deltaTime, duration);
            if (screenTransition == null || transitonDeltaTime >= duration) {
                //no transition effect set or transition has just finished
                if (curScreen != null)
                    curScreen.hide();
                nextScreen.resume();
                // enable input for next screen
                Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
                // switch screens
                curScreen = nextScreen;
                nextScreen = null;
                screenTransition = null;
            } else {
                // render screens to FBOs
                curFbo.begin();
                if (curScreen != null)
                    curScreen.render(deltaTime);
                curFbo.end();

                nextFbo.begin();
                nextScreen.render(deltaTime);
                nextFbo.end();
                // render transition effect to screen
                float alpha = transitonDeltaTime / duration;
                screenTransition.render(batch, curFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(), alpha);
            }
        }

        //
        if (chAsyncManager != null) {
            // 异步任务处理
            chAsyncManager.update();
        }

        if (openFPS) {
            if (fpsLabel == null) {
                // fps
                BitmapFont fontMenu = getDefaultBitmapFont();
                LabelStyle style = new LabelStyle(fontMenu, fontMenu.getColor());
                fpsLabel = new Label("FPS", style);
                fpsLabel.setColor(Color.RED);
                fpsLabel.setY(20);
                fpsLabel.setX(10);
                // fps
            }

            if (getScreen() != null) {
                Batch fpsSpriteBatch = getScreen().getStage().getBatch();
                // 初始要有begin起始
                fpsSpriteBatch.begin();
                // 显示文字到屏幕指定位置
                fpsLabel.setText("FPS :" + Gdx.graphics.getFramesPerSecond() + ",M:"
                        + (Gdx.app.getJavaHeap() / (1024 * 1024)) + ",TS:" + Texture.getNumManagedTextures());
                fpsLabel.draw(fpsSpriteBatch, 1);
                // 结束要有end结尾
                fpsSpriteBatch.end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if (curScreen != null)
            curScreen.resize(width, height);
        if (nextScreen != null)
            nextScreen.resize(width, height);
    }

    @Override
    public void resume() {
        if (curScreen != null)
            curScreen.resume();
    }

    /**
     * 
     * <pre>
     * 切换Screen
     * 
     * date: 2015-4-19
     * </pre>
     * @author caohao
     * @param screenName 完整的类名 com.a.b.Screen
     * @param screenTransition
     */
    public void setScreen(String screenName, IScreenTransition screenTransition) {
        for (CHScreen chScreen : chscreenMap.values()) {
            if (chScreen.getClass().getSimpleName().equals(screenName)) {
                setScreen(chScreen, screenTransition);
                return;
            }
        }

        try {
            Class<?> c = Class.forName(screenName);
            Object yourObj = c.newInstance();
            setScreen((CHScreen)yourObj, screenTransition);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            Gdx.app.debug(TAG, e.getMessage());
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            Gdx.app.debug(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            Gdx.app.debug(TAG, e.getMessage());
        }
    }

    @Override
    public void setScreen(Screen screen) {
        setScreen((CHScreen)screen, null);
    }

    public void setScreen(CHScreen nxtScreen, IScreenTransition screenTransition) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        if (!initTransition) {
            curFbo = new FrameBuffer(Format.RGB888, w, h, false);
            nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
            batch = new SpriteBatch();
            initTransition = true;
        }
        // start new transition
        nextScreen = nxtScreen;
        // activate next screen
        nextScreen.show();
        nextScreen.resize(w, h);
        // let screen update() once [下个场景立马绘制，获取其纹理]
        nextScreen.render(0);
        if (curScreen != null)
            curScreen.pause();
        nextScreen.pause();
        // disable input
        Gdx.input.setInputProcessor(null);
        this.screenTransition = screenTransition;
        transitonDeltaTime = 0;
    }

    /**
     * 
     * <pre> 
     * 同过getAssetManager管理的资源，不要手动释放资源
     * 请使用：AssetManager.unload()
     * 
     * AssetManager.dispose() 将清理掉自身，调用这个方法后，就不能再使用AssetManager了。
     * date: 2015-1-5
     * </pre>
     * @author caohao
     * @return
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    public CHADListener getADListener() {
        return this.chadListener;
    }

    public Drawable getDrawable(String fileName) {
        Texture texture = getTexture(fileName);
        return new TextureRegionDrawable(new TextureRegion(texture));
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
            try {
                assetManager.load(fileName, Texture.class);
                assetManager.finishLoading();
                texture = assetManager.get(fileName, Texture.class);
            } catch (Exception e) {
                // 加载出错，直接读取
                Gdx.app.error(TAG, e.getMessage());
                texture = new Texture(Gdx.files.internal(fileName));
            }
        } else {
            // 此处不知为何报错，特殊处理一下
            try {
                texture = assetManager.get(fileName, Texture.class);
            } catch (Exception e) {
                // 加载出错，直接读取
                Gdx.app.error(TAG, e.getMessage());
                texture = new Texture(Gdx.files.internal(fileName));
            }
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
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public Array<TextureRegion> getLoadingKeyFrames() {
        if (loadingKeyFrames.size == 0) {
            for (int i = 0; i < 6; i++) {
                FileHandle fileHandle = Gdx.files.classpath(String.format("com/oahcfly/chgame/res/l_%d.png", i));
                Texture texture = new Texture(fileHandle);
                texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                loadingKeyFrames.add(new TextureRegion(texture));
            }
        }
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
    public void showWaitingUI() {
        if (chLoading == null) {
            chLoading = new CHWaiting();
        }
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
    public void closeWaitingUI() {
        if (chLoading == null) {
            chLoading = new CHWaiting();
        }
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
        if (international == null) {
            loadInternationalValues();
        }
        return international;
    }

    public LocaleListener getLocaleListener() {
        return localeListener;
    }

    private BitmapFont bitmapFont;

    /**
     * 
     * <pre>
     * Gdx自带英文字体
     * 【禁止自行释放】
     * date: 2015-1-5
     * </pre>
     * @author caohao
     * @return
     */
    public BitmapFont getDefaultBitmapFont() {
        if (bitmapFont == null) {
            bitmapFont = new BitmapFont();
            bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        return bitmapFont;
    }

    public void setADListener(CHADListener chadListener) {
        this.chadListener = chadListener;
    }

    public void setLocaleListener(LocaleListener localeListener) {
        this.localeListener = localeListener;
    }

    public CHAsyncManager getAsyncManager() {
        return chAsyncManager;
    }

    public Batch getBatch() {
        return batch;
    }

    public CHSocialListener getSocialListener() {
        if (socialListener == null) {
            socialListener = new CHSocialListener() {

                @Override
                public void showShare(String title, String subject, String text) {
                    // do nothing
                    Gdx.app.error(TAG, "socialListener is null,showShare");
                }

                @Override
                public void showFeedBack() {
                    //  do nothing
                    Gdx.app.error(TAG, "socialListener is null,showFeedBack");
                }
            };
        }
        return socialListener;
    }

    public void setSocialListener(CHSocialListener socialListener) {
        this.socialListener = socialListener;
    }

    /**
     * 
     * <pre>
     * 退出游戏：自动调用退出广告
     * 
     * date: 2015-1-26
     * </pre>
     * @author caohao
     */
    public void exit() {
        if (getScreen() != null) {
            getScreen().pause();
        }
        savaDataBeforeExit();
        if (chadListener != null) {
            chadListener.showQuitPopAd();
        } else {
            Gdx.app.exit();
        }
    }

    /**
     * 
     * <pre>
     * 在游戏退出前保存数据
     * 
     * date: 2015-1-26
     * </pre>
     * @author caohao
     */
    public abstract void savaDataBeforeExit();

    /**
     * 
     * <pre>
     * Gdx里面是否处理返回按键【默认：true】
     * 
     * date: 2015-2-8
     * </pre>
     * @author caohao
     * @return
     */
    public boolean isCatchBackKey() {
        return catchBackKey;
    }

    public void setCatchBackKey(boolean catchBackKey) {
        this.catchBackKey = catchBackKey;
    }

    public CHGameInfoListener getGameInfoListener() {
        return gameInfoListener;
    }

    public void setGameInfoListener(CHGameInfoListener gameInfoListener) {
        this.gameInfoListener = gameInfoListener;
    }

    public TextureAtlas getTextureAtlas(String packFileName) {
        TextureAtlas textureAtlas;
        if (!assetManager.isLoaded(packFileName)) {
            try {
                assetManager.load(packFileName, TextureAtlas.class);
                assetManager.finishLoading();
                textureAtlas = assetManager.get(packFileName, TextureAtlas.class);
            } catch (Exception e) {
                // 加载出错，直接读取
                Gdx.app.error(TAG, e.getMessage());
                textureAtlas = new TextureAtlas(Gdx.files.internal(packFileName));
            }
        } else {
            // 此处不知为何报错，特殊处理一下
            try {
                textureAtlas = assetManager.get(packFileName, TextureAtlas.class);
            } catch (Exception e) {
                // 加载出错，直接读取
                Gdx.app.error(TAG, e.getMessage());
                textureAtlas = new TextureAtlas(Gdx.files.internal(packFileName));
            }
        }
        return textureAtlas;
    }

}
