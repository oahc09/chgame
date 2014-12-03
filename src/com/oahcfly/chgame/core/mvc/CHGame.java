
package com.oahcfly.chgame.core.mvc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.oahcfly.chgame.core.Version;
import com.oahcfly.chgame.core.ad.CHADListener;
import com.oahcfly.chgame.core.assetmanager.CHAssets;
import com.oahcfly.chgame.core.transition.ScreenTransition;

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

    /**
     * 是否绘制FPS
     * 
     * @param showFPS
     */
    public void setFPS(boolean showFPS) {
        openFPS = showFPS;
    }

    @Override
    public void create() {
        Gdx.app.log("chgame", "version : " + Version.VERSION);

        chAssets = new CHAssets();
        assetManager = chAssets.getAssetManager();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        instance = this;

        setPreferences(Gdx.app.getPreferences("chdata"));

        //spriteBatch = new SpriteBatch();

        fontNumber = new BitmapFont(Gdx.files.classpath("com/oahcfly/chgame/font/number.fnt"),
                Gdx.files.classpath("com/oahcfly/chgame/font/number.png"), false, true);

        fontMenu = new BitmapFont(Gdx.files.classpath("com/oahcfly/chgame/font/menu.fnt"),
                Gdx.files.classpath("com/oahcfly/chgame/font/menu.png"), false, true);

        fontNumber.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fontMenu.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        LabelStyle style = new LabelStyle(fontMenu, fontMenu.getColor());

        fpsLabel = new Label("FPS", style);
        fpsLabel.setY(20);
        fpsLabel.setX(10);

        init();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
        //spriteBatch.dispose();
        fontMenu.dispose();
        fontNumber.dispose();
        assetManager.dispose();
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

    public CHGame(CHADListener adListener) {
        this.chadListener = adListener;
    }

    public CHADListener getADListener() {
        return this.chadListener;
    }

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

        return texture;
    }

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

    public CHAssets getCHAssets() {
        return chAssets;
    }

    public <T extends CHModel> T getModel() {
        return getScreen().getModel();
    }

}
