
package com.oahcfly.chgame.core.mvc;

import java.util.HashMap;
import java.util.Locale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.FreeTypeFontGeneratorExt;
import com.oahcfly.chgame.core.International;
import com.oahcfly.chgame.core.Version;
import com.oahcfly.chgame.core.ad.CHADListener;
import com.oahcfly.chgame.core.async.CHAsyncManager;
import com.oahcfly.chgame.core.helper.FontHelper;
import com.oahcfly.chgame.core.listener.CHSocialListener;
import com.oahcfly.chgame.core.listener.LocaleListener;
import com.oahcfly.chgame.core.manager.MusicManager;
import com.oahcfly.chgame.core.manager.SoundManager;
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
    private FreeTypeFontGeneratorExt generator;

    private HashMap<String, FileHandle> ttfMap = new HashMap<String, FileHandle>();

    private CHADListener chadListener;

    private CHSocialListener socialListener;

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

    private static CHGame instance = null;

    // 异步任务管理者
    private CHAsyncManager chAsyncManager;

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

    private MusicManager musicManager;

    private SoundManager soundManager;

    private Array<TextureRegion> loadingKeyFrames = new Array<TextureRegion>();

    // 国际化语言
    private International international;

    private LocaleListener localeListener;

    public final static String TAG = "CHGame";

    private CHWaiting chLoading;

    private Batch batch;

    // Gdx是否处理back键
    private boolean catchBackKey = true;

    @Override
    public void create() {
        batch = new SpriteBatch();

        Gdx.app.log(TAG, "version : " + Version.VERSION);

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

    /**
     * 
     * <pre>
     * 初始化字体方案
     * 
     * date: 2015-1-2
     * </pre>
     * @author caohao
     */
    private void loadSystemTTF() {
        generator = new FreeTypeFontGeneratorExt(30, false);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();

        Gdx.app.debug(TAG, "dispose");
        batch.dispose();

        if (generator != null) {
            generator.dispose();
        }

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

        FontHelper.getInstance().dispose();
    }

    @Override
    public CHScreen getScreen() {
        // TODO Auto-generated method stub
        return (CHScreen)super.getScreen();
    }

    @Override
    public void pause() {
       savaDataBeforeExit();
        super.pause();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (chAsyncManager != null) {
            // 异步任务处理
            chAsyncManager.update();
        }

        super.render();

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
        if (getScreen() != null) {
            // 不可点击
            getScreen().getStage().getRoot().setTouchable(Touchable.disabled);
        }
        Gdx.input.setInputProcessor(null);
        super.setScreen(screen);
        if (getScreen() != null && getScreen().isBackKey()) {
            // 通过back键切换screen。防止第二个screen再次接收到back处理
            ((CHScreen)screen).setBackKeyPressed(true);
        }
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
        if (generator == null) {
            loadSystemTTF();
        }
        return generator;
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

    public void setChADListener(CHADListener chadListener) {
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
}
