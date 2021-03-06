
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.oahcfly.chgame.core.assetmanager.CHAssets;
import com.oahcfly.chgame.core.async.CHAsyncTask;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.mvc.CHScreen;

/**
 * 
 * <pre>
 * 自定义Logo,408*68大小
 * 
 * date: 2015-1-2
 * </pre>
 * @author caohao
 */
public abstract class CHLoadingScreen extends CHScreen {

    private CHAssets chAssets;

    private float percent = 0f;

    private Image logoImage;

    private Image loadingBarBg;

    private Image loadingPrgressBg;

    private Label progressLabel;

    private Texture barTexture, progressTexture, screenbgTexture;

    public CHLoadingScreen() {
        super();
    }

    public CHLoadingScreen(String logoFilePath) {
        super();
        if (logoFilePath != null) {
            Texture texture = new Texture(Gdx.files.internal(logoFilePath));
            logoImage = new Image(texture);
        }
    }

    private CHAsyncTask loadAssetFileAsyncTask;

    @Override
    public void initScreen() {
        long startime = System.currentTimeMillis();

        chAssets = new CHAssets();

        loadingUI();
        Gdx.app.debug(getTAG(), "loading step -01  :" + (System.currentTimeMillis() - startime));
        startime = System.currentTimeMillis();

        loadAssetFileAsyncTask = new CHAsyncTask() {

            @Override
            public void onPreExecute() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPostExecute(String result) {
                Gdx.app.debug(getTAG(), "result:" + result);

            }

            @Override
            public String doInBackground() {
                loadAssetFile();
                return "ok";
            }
        };
        addAsyncTask(loadAssetFileAsyncTask);

        addProgressLabel();
        Gdx.app.debug(getTAG(), "loading step -02 :" + (System.currentTimeMillis() - startime));

        // 加载界面禁止点击
        //Gdx.input.setInputProcessor(null);
        // touchUp事件传递到第二个Screen里面了
    }

    /**
     * 
     * <pre>
     * 加载资源文件
     * 
     * date: 2015-1-2
     * </pre>
     * @author caohao
     * @param filePath
     */
    public abstract void loadAssetFile();

    /**
     * 
     * <pre>
     * 资源加载完毕。切换到下一个Screen
     * 
     * date: 2015-1-9
     * </pre>
     * @author caohao
     */
    public abstract void changeToNextScreen();

    public CHAssets getCHAssets() {
        return chAssets;
    }

    private float progress = 0.2f;

    @Override
    public void render(float delta) {

        super.render(delta);

        //        if (chAssets.update()) { // Load some, will return true if done loading
        //            if (Gdx.input.justTouched()) {
        //                // If the screen is touched after the game is done loading, go to the next screen
        //                // 处理down事件->切换screen>处理up事件
        //            }
        //        }

        if (loadAssetFileAsyncTask.isDone()) {
            // 文件加载完毕，才进行load，update操作
            chAssets.update();
            progress = chAssets.getProgress();
            progress = progress < 0.2f ? 0.2f : progress;
        }

        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, progress, 0.1f);
        loadingPrgressBg.setSize(20 + 420 * percent, loadingPrgressBg.getHeight());
        progressLabel.setTouchable(Touchable.disabled);
        progressLabel.setText("Loading..." + (int)(percent * 100) + "%");
        //System.out.println("Loading:" + (int)(percent * 100) + "% , " + System.currentTimeMillis());

        if (percent > 0.990f) {
            changeToNextScreen();
        }
    }

    protected void loadingUI() {
        FileHandle loadingBarFileHandle = Gdx.files.classpath("com/oahcfly/chgame/res/loadingbar.png");
        FileHandle loadingProgressFileHandle = Gdx.files.classpath("com/oahcfly/chgame/res/loadingprogress.png");
        FileHandle screenbgFileHandle = Gdx.files.classpath("com/oahcfly/chgame/res/screen-bg.png");

        screenbgTexture = new Texture(screenbgFileHandle);
        screenbgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        Image screenBg = new Image(screenbgTexture);
        screenBg.setSize(CHGame.getInstance().gameWidth, CHGame.getInstance().gameHeight);
        screenBg.setTouchable(Touchable.disabled);

        addActor(screenBg);
        barTexture = new Texture(loadingBarFileHandle);
        progressTexture = new Texture(loadingProgressFileHandle);

        barTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        progressTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        loadingBarBg = new Image(barTexture);
        loadingPrgressBg = new Image(progressTexture);

        if (logoImage == null) {
            logoImage = new Image(new Texture(Gdx.files.classpath("com/oahcfly/chgame/res/studio_logo.png")));
        }

        loadingBarBg.setTouchable(Touchable.disabled);
        logoImage.setTouchable(Touchable.disabled);
        loadingPrgressBg.setTouchable(Touchable.disabled);

        logoImage.setX((getStage().getWidth() - logoImage.getWidth()) / 2);
        logoImage.setY((getStage().getHeight()) / 2 + 100);
        logoImage.setOrigin(com.badlogic.gdx.utils.Align.center);
        Action repeatedAction = Actions.sequence(Actions.moveTo(logoImage.getX(), logoImage.getY() + 10, 0.5f),
                Actions.moveTo(logoImage.getX(), logoImage.getY(), 0.5f),
                Actions.moveTo(logoImage.getX(), logoImage.getY() - 10, 0.5f));
        logoImage.addAction(Actions.forever(repeatedAction));

        // 居中
        loadingBarBg.setX((getStage().getWidth() - loadingBarBg.getWidth()) / 2);
        loadingBarBg.setY((getStage().getHeight() - loadingBarBg.getHeight()) / 2);
        loadingPrgressBg.setX(loadingBarBg.getX());
        loadingPrgressBg.setY(loadingBarBg.getY() + 5);
        loadingPrgressBg.setSize(30, loadingPrgressBg.getHeight());

        addActor(logoImage);
        addActor(loadingPrgressBg);
        addActor(loadingBarBg);

    }

    private void addProgressLabel() {
        progressLabel = new Label("Loading... 0%", new LabelStyle(CHGame.getInstance().getDefaultBitmapFont(),
                Color.WHITE));
        GlyphLayout layout = new GlyphLayout();
        layout.setText(CHGame.getInstance().getDefaultBitmapFont(), "Loading... 0%");
        // progressLabel.getStyle().font.setScale(2f);
        progressLabel.setTouchable(Touchable.disabled);
        progressLabel.setWidth(layout.width);
        progressLabel.setPosition(getStage().getWidth() / 2, getStage().getHeight() / 2, Align.center);
        //touchTipLabel.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.1f, 0.4f), Actions.alpha(1f, 0.8f))));
        addActor(progressLabel);
    }

    @Override
    public void endScreen() {
        // TODO Auto-gene4rated method stub
        getCHAssets().dispose();
        barTexture.dispose();
        progressTexture.dispose();
        screenbgTexture.dispose();
    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

}
