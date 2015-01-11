
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.core.assetmanager.CHAssets;
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

    public CHLoadingScreen() {
    }

    private float startX, endX;

    private float percent;

    private Image loadingBarHidden;

    private Image logoImage;

    private CHAniamtionPlayer aniamtionPlayer;

    public CHLoadingScreen(String logoFilePath) {
        if (logoFilePath != null) {
            Texture texture = new Texture(Gdx.files.internal(logoFilePath));
            logoImage = new Image(texture);
        }
    }

    private TextureAtlas atlas;

    private Image loadingBg;

    private Label touchTipLabel;

    @Override
    public void initScreen() {

        chAssets = new CHAssets();
        FileHandle packFile = Gdx.files.classpath("com/oahcfly/chgame/res/loading.pack");
        FileHandle imagesDir = Gdx.files.classpath("com/oahcfly/chgame/res/");
        atlas = new TextureAtlas(packFile, imagesDir);
        atlas.getRegions().get(0).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        // Grab the regions from the atlas and create some images

        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));
        Image loadingFrame = new Image(atlas.findRegion("loading-frame"));
        Image screenBg = new Image(atlas.findRegion("screen-bg"));
        screenBg.setBounds(0, 0, getStage().getWidth(), getStage().getHeight());

        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        // 帧动画
        aniamtionPlayer = new CHAniamtionPlayer(0.05f, atlas.findRegions("loading-bar-anim"), PlayMode.LOOP_REVERSED);
        aniamtionPlayer.play();

        if (logoImage == null) {
            logoImage = new Image(new Texture(Gdx.files.classpath("com/oahcfly/chgame/res/studio_logo.png")));
            //new Image(atlas.findRegion("libgdx-logo"));
        }

        aniamtionPlayer.setTouchable(Touchable.disabled);
        loadingFrame.setTouchable(Touchable.disabled);
        logoImage.setTouchable(Touchable.disabled);
        loadingBg.setTouchable(Touchable.disabled);
        loadingBarHidden.setTouchable(Touchable.disabled);

        screenBg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (touchTipLabel != null) {
                    changeToNextScreen();
                }
            }

        });

        addActor(screenBg);
        addActor(logoImage);
        addActor(aniamtionPlayer);
        addActor(loadingFrame);

        addActor(loadingBg);
        addActor(loadingBarHidden);

        logoImage.setX((getStage().getWidth() - logoImage.getWidth()) / 2);
        logoImage.setY((getStage().getHeight()) / 2 + 100);
        logoImage.setOrigin(Align.center);
        Action repeatedAction = Actions.sequence(Actions.moveTo(logoImage.getX(), logoImage.getY() + 10, 0.5f),
                Actions.moveTo(logoImage.getX(), logoImage.getY(), 0.5f),
                Actions.moveTo(logoImage.getX(), logoImage.getY() - 10, 0.5f));
        logoImage.addAction(Actions.forever(repeatedAction));

        // 居中
        loadingFrame.setX((getStage().getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((getStage().getHeight() - loadingFrame.getHeight()) / 2);
        aniamtionPlayer.setX(loadingFrame.getX() + 15);
        aniamtionPlayer.setY(loadingFrame.getY() + 5);
        loadingBarHidden.setX(aniamtionPlayer.getX() + 35);
        loadingBarHidden.setY(aniamtionPlayer.getY() - 3);

        loadingBg.setSize(aniamtionPlayer.getWidth() - 65, 50);
        loadingBg.setX(loadingBarHidden.getRight());
        loadingBg.setY(loadingBarHidden.getY() + 3);

        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = aniamtionPlayer.getRight() - loadingBarHidden.getWidth() + 2;

        loadAssetFile();

    }

    private void showTouchTip() {
        touchTipLabel = new Label("Touch to start ", new LabelStyle(CHGame.getInstance().getDefaultBitmapFont(),
                Color.WHITE));

        touchTipLabel.getStyle().font.setScale(2f);
        touchTipLabel.setTouchable(Touchable.disabled);
        touchTipLabel.setWidth(touchTipLabel.getTextBounds().width);
        touchTipLabel.setPosition(getStage().getWidth() / 2, getStage().getHeight() / 2, Align.center);
        touchTipLabel.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.1f, 0.4f), Actions.alpha(1f, 0.8f))));
        addActor(touchTipLabel);
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

    @Override
    public void endScreen() {
        // TODO Auto-gene4rated method stub
        getCHAssets().dispose();
        atlas.dispose();
    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

    public CHAssets getCHAssets() {
        return chAssets;
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);

        if (chAssets.update()) { // Load some, will return true if done loading
            if (Gdx.input.justTouched()) {
                // If the screen is touched after the game is done loading, go to the next screen
                // 处理down事件->切换screen>处理up事件
            }
            if (touchTipLabel == null) {
                showTouchTip();
            }
        }
        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, chAssets.getProgress(), 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + (endX - startX) * percent);
        loadingBg.setX(loadingBarHidden.getRight());
        loadingBg.setWidth((aniamtionPlayer.getWidth() - 71) * (1 - percent));
        if (percent > 0.99) {
            loadingBarHidden.setVisible(false);
        }

    }

}
